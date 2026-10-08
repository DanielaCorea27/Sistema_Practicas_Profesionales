package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.Postulante;
import sv.edu.itca.practicas.util.Conexion;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Postulaciones vistas desde la empresa.
 * Todo se filtra por empresaId: una empresa solo toca lo suyo.
 */
public class PostulacionDAO {

    private static final String SELECT_POSTULANTES =
            "SELECT p.id_postulacion, p.fecha_postulacion, "
            + "p.estado AS p_estado, "
            + "o.id_oportunidad, o.titulo, o.horas_ofrecidas, "
            + "o.estado AS o_estado, "
            + "a.id_alumno, a.carnet, a.telefono, "
            + "u.nombre, u.apellido, u.correo, "
            + "c.nombre AS carrera, t.nombre AS tipo, t.horas_requeridas, "
            + "(SELECT COALESCE(SUM(ap.horas_planificadas), 0) "
            + "   FROM asignaciones_pasantia ap "
            + "   JOIN pasantias ps ON ps.id_pasantia = ap.id_pasantia "
            + "  WHERE ps.id_alumno = a.id_alumno "
            + "    AND ps.estado IN ('PENDIENTE','ACTIVA') "
            + "    AND ap.estado <> 'CANCELADA') AS horas_comprometidas "
            + "FROM postulaciones p "
            + "JOIN oportunidades o ON o.id_oportunidad = p.id_oportunidad "
            + "JOIN alumnos a ON a.id_alumno = p.id_alumno "
            + "JOIN usuarios u ON u.id_usuario = a.id_usuario "
            + "JOIN carreras c ON c.id_carrera = a.id_carrera "
            + "JOIN tipo_carrera t ON t.id_tipo_carrera = c.id_tipo_carrera "
            + "WHERE o.id_empresa = ? ";

    /**
     * @param estado PENDIENTE, ACEPTADA, RECHAZADA o null para todos.
     */
    public List<Postulante> listarPorEmpresa(int empresaId, String estado) {

        List<Postulante> lista = new ArrayList<>();

        String sql = SELECT_POSTULANTES
                + (estado != null ? "AND p.estado = ? " : "")
                + "ORDER BY (p.estado = 'PENDIENTE') DESC, "
                + "p.fecha_postulacion DESC, p.id_postulacion DESC";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empresaId);

            if (estado != null) {
                ps.setString(2, estado);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Postulante p = new Postulante();

                    p.setPostulacionId(rs.getInt("id_postulacion"));
                    p.setFechaPostulacion(leerFecha(rs, "fecha_postulacion"));
                    p.setEstado(rs.getString("p_estado"));
                    p.setOportunidadId(rs.getInt("id_oportunidad"));
                    p.setOportunidadTitulo(rs.getString("titulo"));
                    p.setHorasOfrecidas(rs.getInt("horas_ofrecidas"));
                    p.setOportunidadEstado(rs.getString("o_estado"));
                    p.setAlumnoId(rs.getInt("id_alumno"));
                    p.setAlumnoNombre(
                            rs.getString("nombre") + " " + rs.getString("apellido"));
                    p.setCorreo(rs.getString("correo"));
                    p.setCarnet(rs.getString("carnet"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setCarrera(rs.getString("carrera"));
                    p.setTipoCarrera(rs.getString("tipo"));
                    p.setHorasRequeridas(rs.getInt("horas_requeridas"));
                    p.setHorasComprometidas(rs.getInt("horas_comprometidas"));

                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /** Rechaza una postulacion PENDIENTE de la empresa. */
    public boolean rechazar(int postulacionId, int empresaId) {

        String sql = "UPDATE postulaciones p "
                + "JOIN oportunidades o ON o.id_oportunidad = p.id_oportunidad "
                + "SET p.estado = 'RECHAZADA' "
                + "WHERE p.id_postulacion = ? AND o.id_empresa = ? "
                + "AND p.estado = 'PENDIENTE'";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, postulacionId);
            ps.setInt(2, empresaId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ACEPTA una postulacion en UNA SOLA TRANSACCION:
     *   1. marca la postulacion como ACEPTADA
     *   2. crea la pasantia del estudiante (o reutiliza la que ya tiene)
     *   3. crea la asignacion (pasantia + empresa) con las horas planificadas
     * Si algo falla, no se guarda nada.
     *
     * @return horas planificadas que quedaron en la asignacion
     */
    public int aceptar(int postulacionId, int empresaId, int representanteId)
            throws ReglaNegocioException {

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try {

                int horas = aceptarEnTransaccion(
                        con, postulacionId, empresaId, representanteId);

                con.commit();

                return horas;

            } catch (ReglaNegocioException | SQLException e) {

                con.rollback();

                throw e;

            } finally {

                con.setAutoCommit(true);
            }

        } catch (SQLException e) {

            e.printStackTrace();

            throw new ReglaNegocioException(
                    "Ocurrió un error en la base de datos. Intenta de nuevo.");
        }
    }

    private int aceptarEnTransaccion(
            Connection con,
            int postulacionId,
            int empresaId,
            int representanteId)
            throws SQLException, ReglaNegocioException {

        // 1. Bloquear la postulacion y su oportunidad (solo de ESTA empresa).
        int alumnoId;
        int oportunidadId;
        int horasOfrecidas;
        String estadoPostulacion;
        String estadoOportunidad;
        String modalidad;
        LocalDate fechaIni;
        LocalDate fechaFin;

        String sql1 = "SELECT p.id_alumno, p.estado AS p_estado, "
                + "o.id_oportunidad, o.estado AS o_estado, "
                + "o.horas_ofrecidas, o.fecha_inicio, o.fecha_fin, o.modalidad "
                + "FROM postulaciones p "
                + "JOIN oportunidades o ON o.id_oportunidad = p.id_oportunidad "
                + "WHERE p.id_postulacion = ? AND o.id_empresa = ? FOR UPDATE";

        try (PreparedStatement ps = con.prepareStatement(sql1)) {

            ps.setInt(1, postulacionId);
            ps.setInt(2, empresaId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    throw new ReglaNegocioException(
                            "No se encontró la postulación.");
                }

                alumnoId = rs.getInt("id_alumno");
                estadoPostulacion = rs.getString("p_estado");
                oportunidadId = rs.getInt("id_oportunidad");
                estadoOportunidad = rs.getString("o_estado");
                horasOfrecidas = rs.getInt("horas_ofrecidas");
                modalidad = rs.getString("modalidad");
                fechaIni = leerFecha(rs, "fecha_inicio");
                fechaFin = leerFecha(rs, "fecha_fin");
            }
        }

        if (!"PENDIENTE".equals(estadoPostulacion)) {
            throw new ReglaNegocioException(
                    "Esta postulación ya fue respondida.");
        }

        if (!"APROBADA".equals(estadoOportunidad)
                && !"CERRADA".equals(estadoOportunidad)) {
            throw new ReglaNegocioException(
                    "La oportunidad debe estar aprobada por el tutor "
                    + "antes de aceptar estudiantes.");
        }

        // 2. Bloquear al estudiante (evita que dos empresas lo acepten a la
        //    vez) y leer las horas que exige su carrera (320 o 640).
        int horasCarrera;

        String sql2 = "SELECT t.horas_requeridas FROM alumnos a "
                + "JOIN carreras c ON c.id_carrera = a.id_carrera "
                + "JOIN tipo_carrera t ON t.id_tipo_carrera = c.id_tipo_carrera "
                + "WHERE a.id_alumno = ? FOR UPDATE";

        try (PreparedStatement ps = con.prepareStatement(sql2)) {

            ps.setInt(1, alumnoId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    throw new ReglaNegocioException(
                            "No se encontró al estudiante.");
                }

                horasCarrera = rs.getInt(1);
            }
        }

        // 3. Pasantia vigente del estudiante (si ya tiene una).
        int pasantiaId = 0;
        int horasRequeridas = horasCarrera;
        LocalDate pasIni = null;
        LocalDate pasFin = null;
        boolean pasantiaExistente = false;

        String sql3 = "SELECT id_pasantia, horas_requeridas, "
                + "fecha_inicio, fecha_fin FROM pasantias "
                + "WHERE id_alumno = ? AND estado IN ('PENDIENTE','ACTIVA') "
                + "ORDER BY id_pasantia DESC LIMIT 1 FOR UPDATE";

        try (PreparedStatement ps = con.prepareStatement(sql3)) {

            ps.setInt(1, alumnoId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    pasantiaExistente = true;
                    pasantiaId = rs.getInt("id_pasantia");
                    horasRequeridas = rs.getInt("horas_requeridas");
                    pasIni = leerFecha(rs, "fecha_inicio");
                    pasFin = leerFecha(rs, "fecha_fin");
                }
            }
        }

        if (!pasantiaExistente) {

            if (contar(con,
                    "SELECT COUNT(*) FROM pasantias "
                    + "WHERE id_alumno = ? AND estado = 'FINALIZADA'",
                    alumnoId) > 0) {

                throw new ReglaNegocioException(
                        "El estudiante ya finalizó su pasantía.");
            }

            String ins = "INSERT INTO pasantias (id_alumno, horas_requeridas, "
                    + "fecha_inicio, fecha_fin, estado) "
                    + "VALUES (?, ?, ?, ?, 'ACTIVA')";

            try (PreparedStatement ps = con.prepareStatement(
                    ins, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, alumnoId);
                ps.setInt(2, horasRequeridas);
                asignarFecha(ps, 3, fechaIni);
                asignarFecha(ps, 4, fechaFin);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {

                    keys.next();
                    pasantiaId = keys.getInt(1);
                }
            }
        }

        // 4. Reglas de horas: maximo 2 empresas y no pasar de las
        //    horas requeridas (ej. 640 = 300 + 340).
        int horasPlanificadas = 0;
        int asignaciones = 0;
        int mismaEmpresa = 0;

        String sql4 = "SELECT COALESCE(SUM(horas_planificadas), 0), COUNT(*), "
                + "COALESCE(SUM(id_empresa = ?), 0) "
                + "FROM asignaciones_pasantia "
                + "WHERE id_pasantia = ? AND estado <> 'CANCELADA'";

        try (PreparedStatement ps = con.prepareStatement(sql4)) {

            ps.setInt(1, empresaId);
            ps.setInt(2, pasantiaId);

            try (ResultSet rs = ps.executeQuery()) {

                rs.next();
                horasPlanificadas = rs.getInt(1);
                asignaciones = rs.getInt(2);
                mismaEmpresa = rs.getInt(3);
            }
        }

        if (mismaEmpresa > 0) {
            throw new ReglaNegocioException(
                    "El estudiante ya está asignado a esta empresa.");
        }

        if (asignaciones >= 2) {
            throw new ReglaNegocioException(
                    "El estudiante ya tiene dos empresas asignadas.");
        }

        int restantes = horasRequeridas - horasPlanificadas;

        if (restantes <= 0) {
            throw new ReglaNegocioException(
                    "El estudiante ya tiene planificadas todas sus horas ("
                    + horasRequeridas + ").");
        }

        int horasAsignadas = Math.min(horasOfrecidas, restantes);

        // 5. Crear la asignacion pasantia <-> empresa.
        String sql5 = "INSERT INTO asignaciones_pasantia (id_pasantia, "
                + "id_empresa, id_representante, id_oportunidad, "
                + "horas_planificadas, fecha_inicio, fecha_fin, modalidad, "
                + "estado) VALUES (?,?,?,?,?,?,?,?, 'ACTIVA')";

        try (PreparedStatement ps = con.prepareStatement(sql5)) {

            ps.setInt(1, pasantiaId);
            ps.setInt(2, empresaId);
            ps.setInt(3, representanteId);
            ps.setInt(4, oportunidadId);
            ps.setInt(5, horasAsignadas);
            asignarFecha(ps, 6, fechaIni);
            asignarFecha(ps, 7, fechaFin);
            ps.setString(8, modalidad);
            ps.executeUpdate();
        }

        // 6. Marcar la postulacion como aceptada.
        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE postulaciones SET estado = 'ACEPTADA' "
                + "WHERE id_postulacion = ?")) {

            ps.setInt(1, postulacionId);
            ps.executeUpdate();
        }

        // 7. Si la pasantia ya existia, ampliar su rango de fechas.
        if (pasantiaExistente) {

            LocalDate nuevaIni = menor(pasIni, fechaIni);
            LocalDate nuevaFin = mayor(pasFin, fechaFin);

            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE pasantias SET fecha_inicio = ?, fecha_fin = ?, "
                    + "estado = 'ACTIVA' WHERE id_pasantia = ?")) {

                asignarFecha(ps, 1, nuevaIni);
                asignarFecha(ps, 2, nuevaFin);
                ps.setInt(3, pasantiaId);
                ps.executeUpdate();
            }
        }

        return horasAsignadas;
    }

    // ------------------------------------------------------------------
    //  Utilidades
    // ------------------------------------------------------------------

    private int contar(Connection con, String sql, int parametro)
            throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, parametro);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    private LocalDate leerFecha(ResultSet rs, String columna)
            throws SQLException {

        Date d = rs.getDate(columna);

        return d == null ? null : d.toLocalDate();
    }

    private void asignarFecha(PreparedStatement ps, int indice, LocalDate f)
            throws SQLException {

        if (f == null) {
            ps.setNull(indice, Types.DATE);
        } else {
            ps.setDate(indice, Date.valueOf(f));
        }
    }

    private LocalDate menor(LocalDate a, LocalDate b) {

        if (a == null) return b;
        if (b == null) return a;

        return a.isBefore(b) ? a : b;
    }

    private LocalDate mayor(LocalDate a, LocalDate b) {

        if (a == null) return b;
        if (b == null) return a;

        return a.isAfter(b) ? a : b;
    }
}
