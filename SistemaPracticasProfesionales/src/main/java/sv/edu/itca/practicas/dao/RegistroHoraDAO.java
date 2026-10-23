package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.RegistroHora;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Registro de horas del alumno, guardado en la tabla ACTIVIDADES.
 *
 * Como el formulario solo envia fecha, horas y actividad, el resto de las
 * columnas obligatorias se rellenan asi:
 *   - id_asignacion : asignacion ACTIVA mas reciente del alumno
 *   - titulo        : primeros 150 caracteres de la actividad
 *   - descripcion   : texto completo de la actividad
 *   - hora_inicio   : 00:00:00 (relleno)
 *   - hora_fin      : hora_inicio + horas (relleno, maximo 23:59:59)
 *
 * Las filas con horas = 0 son EVIDENCIAS (ver EvidenciaDAO) y se ignoran aqui.
 */
public class RegistroHoraDAO {

    private static final String FROM =
            "FROM actividades x "
            + "JOIN asignaciones_pasantia ap ON ap.id_asignacion = x.id_asignacion "
            + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia ";

    private static final String SELECT =
            "SELECT x.id_actividad, pa.id_alumno, x.fecha, x.horas, "
            + "x.titulo, x.descripcion, x.estado, x.observacion "
            + FROM
            + "WHERE x.horas > 0 ";

    private static final String ORDEN =
            "ORDER BY x.fecha DESC, x.id_actividad DESC";

    private static final String SUMA = "SELECT COALESCE(SUM(x.horas), 0) ";
    private static final String CUENTA = "SELECT COUNT(*) ";

    private final AlumnoPortalDAO portalDAO = new AlumnoPortalDAO();

    // ------------------------------------------------------------------
    //  Mapeo
    // ------------------------------------------------------------------

    private RegistroHora mapear(ResultSet rs) throws SQLException {

        String descripcion = rs.getString("descripcion");
        String observacion = rs.getString("observacion");

        String actividad = descripcion == null || descripcion.trim().isEmpty()
                ? rs.getString("titulo")
                : descripcion;

        return new RegistroHora(
                rs.getInt("id_actividad"),
                rs.getInt("id_alumno"),
                rs.getDate("fecha").toLocalDate(),
                rs.getDouble("horas"),
                actividad,
                rs.getString("estado"),
                observacion == null ? "" : observacion
        );
    }

    private List<RegistroHora> consultar(String sql, Integer parametro) {

        List<RegistroHora> lista = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setInt(1, parametro);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ------------------------------------------------------------------
    //  Consultas
    // ------------------------------------------------------------------

    public List<RegistroHora> listarPorAlumno(int alumnoId) {

        if (alumnoId <= 0) {
            return new ArrayList<>();
        }

        return consultar(
                SELECT + "AND pa.id_alumno = ? " + ORDEN, alumnoId);
    }

    public List<RegistroHora> listarTodos() {

        return consultar(SELECT + ORDEN, null);
    }

    public RegistroHora buscarPorId(int id) {

        List<RegistroHora> lista = consultar(
                SELECT + "AND x.id_actividad = ? " + ORDEN, id);

        return lista.isEmpty() ? null : lista.get(0);
    }

    // ------------------------------------------------------------------
    //  Insertar / actualizar / eliminar
    // ------------------------------------------------------------------

    /** false si el alumno no tiene una asignacion ACTIVA o falla la BD. */
    public boolean insertar(RegistroHora registro) {

        if (registro == null) {
            return false;
        }

        int asignacionId = portalDAO.buscarAsignacionActiva(
                registro.getAlumnoId());

        if (asignacionId == 0) {
            return false;
        }

        String sql = "INSERT INTO actividades (id_asignacion, fecha, titulo, "
                + "descripcion, hora_inicio, hora_fin, horas, estado, observacion) "
                + "VALUES (?,?,?,?, '00:00:00', ?, ?, 'PENDIENTE', NULL)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, asignacionId);
            ps.setDate(2, Date.valueOf(registro.getFecha()));
            ps.setString(3, titulo(registro.getActividad()));
            ps.setString(4, registro.getActividad());
            ps.setString(5, horaFin(registro.getHoras()));
            ps.setDouble(6, registro.getHoras());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    registro.setId(keys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(RegistroHora r) {

        if (r == null || r.getFecha() == null) {
            return false;
        }

        String sql = "UPDATE actividades SET fecha = ?, titulo = ?, "
                + "descripcion = ?, hora_fin = ?, horas = ?, estado = ?, "
                + "observacion = ? WHERE id_actividad = ? AND horas > 0";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(r.getFecha()));
            ps.setString(2, titulo(r.getActividad()));
            ps.setString(3, r.getActividad());
            ps.setString(4, horaFin(r.getHoras()));
            ps.setDouble(5, r.getHoras());
            ps.setString(6, r.getEstado());
            ps.setString(7, vacioANull(r.getObservacion()));
            ps.setInt(8, r.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un registro del propio alumno. Los APROBADOS no se pueden
     * eliminar (igual que oculta el boton la vista).
     */
    public boolean eliminar(int id, int alumnoId) {

        String sql = "DELETE x FROM actividades x "
                + "JOIN asignaciones_pasantia ap ON ap.id_asignacion = x.id_asignacion "
                + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia "
                + "WHERE x.id_actividad = ? AND pa.id_alumno = ? "
                + "AND x.horas > 0 AND x.estado <> 'APROBADO'";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, alumnoId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------------------
    //  Aprobar / rechazar (maestro)
    // ------------------------------------------------------------------

    public boolean aprobar(int id, String observacion) {

        return cambiarEstado(id, "APROBADO",
                vacioANull(observacion == null ? null : observacion.trim()));
    }

    public boolean rechazar(int id, String observacion) {

        String texto = observacion == null || observacion.trim().isEmpty()
                ? "Registro rechazado por el maestro."
                : observacion.trim();

        return cambiarEstado(id, "RECHAZADO", texto);
    }

    private boolean cambiarEstado(int id, String estado, String observacion) {

        String sql = "UPDATE actividades SET estado = ?, observacion = ? "
                + "WHERE id_actividad = ? AND estado = 'PENDIENTE' AND horas > 0";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setString(2, observacion);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------------------------------------------------
    //  Horas de un alumno
    // ------------------------------------------------------------------

    public double obtenerTotalHoras(int alumnoId) {

        return alumnoId <= 0 ? 0 : escalar(SUMA, null, alumnoId);
    }

    public double obtenerHorasAprobadas(int alumnoId) {

        return alumnoId <= 0 ? 0 : escalar(SUMA, "APROBADO", alumnoId);
    }

    // ------------------------------------------------------------------
    //  Estadisticas del sistema (maestro / reportes)
    // ------------------------------------------------------------------

    public int contarRegistros() {
        return (int) escalar(CUENTA, null, null);
    }

    public int contarPendientes() {
        return (int) escalar(CUENTA, "PENDIENTE", null);
    }

    public int contarAprobados() {
        return (int) escalar(CUENTA, "APROBADO", null);
    }

    public int contarRechazados() {
        return (int) escalar(CUENTA, "RECHAZADO", null);
    }

    public double obtenerHorasTotalesSistema() {
        return escalar(SUMA, null, null);
    }

    public double obtenerHorasAprobadasSistema() {
        return escalar(SUMA, "APROBADO", null);
    }

    public double obtenerHorasPendientesSistema() {
        return escalar(SUMA, "PENDIENTE", null);
    }

    public int obtenerRegistrosPendientes() {
        return contarPendientes();
    }

    // ------------------------------------------------------------------
    //  Utilidades
    // ------------------------------------------------------------------

    /**
     * @param select SUMA o CUENTA
     * @param estado null = cualquier estado
     * @param alumnoId null = todo el sistema
     */
    private double escalar(String select, String estado, Integer alumnoId) {

        String sql = select + FROM + "WHERE x.horas > 0 "
                + (estado != null ? "AND x.estado = ? " : "")
                + (alumnoId != null ? "AND pa.id_alumno = ? " : "");

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;

            if (estado != null) {
                ps.setString(i++, estado);
            }

            if (alumnoId != null) {
                ps.setInt(i, alumnoId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String titulo(String actividad) {

        String t = actividad == null
                ? ""
                : actividad.trim().replaceAll("\\s+", " ");

        return t.length() > 150 ? t.substring(0, 150) : t;
    }

    /** hora_inicio es 00:00:00, asi que hora_fin = horas (tope 23:59:59). */
    private String horaFin(double horas) {

        int seg = (int) Math.min(Math.round(horas * 3600), 86399);

        return String.format("%02d:%02d:%02d",
                seg / 3600, (seg % 3600) / 60, seg % 60);
    }

    private String vacioANull(String s) {
        return s == null || s.isEmpty() ? null : s;
    }
}
