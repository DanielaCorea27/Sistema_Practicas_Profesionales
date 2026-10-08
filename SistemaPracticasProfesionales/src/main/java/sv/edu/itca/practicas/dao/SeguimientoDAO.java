package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.ActividadAsignacion;
import sv.edu.itca.practicas.model.AsignacionResumen;
import sv.edu.itca.practicas.model.HorarioAsignacion;
import sv.edu.itca.practicas.model.ObservacionAsignacion;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Consultas de la empresa sobre sus estudiantes en practica:
 * asignaciones, actividades, horarios y observaciones.
 *
 * Toda consulta recibe empresaId: una empresa solo ve lo suyo.
 * Las horas "realizadas" son SOLO las de actividades APROBADAS.
 */
public class SeguimientoDAO {

    private static final String SELECT_ASIGNACION =
            "SELECT ap.id_asignacion, ap.id_pasantia, ap.horas_planificadas, "
            + "ap.fecha_inicio, ap.fecha_fin, ap.modalidad, "
            + "ap.estado AS a_estado, pa.horas_requeridas, "
            + "a.id_alumno, a.carnet, a.telefono, "
            + "u.nombre, u.apellido, u.correo, "
            + "c.nombre AS carrera, t.nombre AS tipo, "
            + "mu.nombre AS m_nombre, mu.apellido AS m_apellido, "
            + "o.titulo AS oportunidad, "
            + "COALESCE((SELECT SUM(x.horas) FROM actividades x "
            + "  WHERE x.id_asignacion = ap.id_asignacion "
            + "    AND x.estado = 'APROBADO'), 0) AS h_aprobadas, "
            + "COALESCE((SELECT SUM(x.horas) FROM actividades x "
            + "  WHERE x.id_asignacion = ap.id_asignacion "
            + "    AND x.estado = 'PENDIENTE'), 0) AS h_pendientes, "
            + "COALESCE((SELECT SUM(x.horas) FROM actividades x "
            + "  JOIN asignaciones_pasantia ap2 "
            + "    ON ap2.id_asignacion = x.id_asignacion "
            + "  WHERE ap2.id_pasantia = ap.id_pasantia "
            + "    AND x.estado = 'APROBADO'), 0) AS h_pasantia "
            + "FROM asignaciones_pasantia ap "
            + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia "
            + "JOIN alumnos a ON a.id_alumno = pa.id_alumno "
            + "JOIN usuarios u ON u.id_usuario = a.id_usuario "
            + "JOIN carreras c ON c.id_carrera = a.id_carrera "
            + "JOIN tipo_carrera t ON t.id_tipo_carrera = c.id_tipo_carrera "
            + "LEFT JOIN maestros m ON m.id_maestro = pa.id_maestro "
            + "LEFT JOIN usuarios mu ON mu.id_usuario = m.id_usuario "
            + "LEFT JOIN oportunidades o ON o.id_oportunidad = ap.id_oportunidad "
            + "WHERE ap.id_empresa = ? ";

    private AsignacionResumen mapear(ResultSet rs) throws SQLException {

        AsignacionResumen r = new AsignacionResumen();

        r.setId(rs.getInt("id_asignacion"));
        r.setPasantiaId(rs.getInt("id_pasantia"));
        r.setAlumnoId(rs.getInt("id_alumno"));
        r.setAlumnoNombre(rs.getString("nombre") + " " + rs.getString("apellido"));
        r.setCarnet(rs.getString("carnet"));
        r.setCorreo(rs.getString("correo"));
        r.setTelefono(rs.getString("telefono"));
        r.setCarrera(rs.getString("carrera"));
        r.setTipoCarrera(rs.getString("tipo"));

        String mNombre = rs.getString("m_nombre");

        r.setMaestroNombre(mNombre == null
                ? null
                : mNombre + " " + rs.getString("m_apellido"));

        r.setOportunidadTitulo(rs.getString("oportunidad"));
        r.setHorasRequeridas(rs.getInt("horas_requeridas"));
        r.setHorasPlanificadas(rs.getInt("horas_planificadas"));
        r.setHorasAprobadas(rs.getDouble("h_aprobadas"));
        r.setHorasPendientes(rs.getDouble("h_pendientes"));
        r.setHorasTotalesPasantia(rs.getDouble("h_pasantia"));

        Date ini = rs.getDate("fecha_inicio");
        Date fin = rs.getDate("fecha_fin");

        r.setFechaInicio(ini == null ? null : ini.toLocalDate());
        r.setFechaFin(fin == null ? null : fin.toLocalDate());
        r.setModalidad(rs.getString("modalidad"));
        r.setEstado(rs.getString("a_estado"));

        return r;
    }

    /**
     * @param estado ACTIVA, FINALIZADA, CANCELADA o null para todas.
     */
    public List<AsignacionResumen> listarPorEmpresa(int empresaId, String estado) {

        List<AsignacionResumen> lista = new ArrayList<>();

        String sql = SELECT_ASIGNACION
                + (estado != null ? "AND ap.estado = ? " : "")
                + "ORDER BY (ap.estado = 'ACTIVA') DESC, ap.id_asignacion DESC";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empresaId);

            if (estado != null) {
                ps.setString(2, estado);
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

    public AsignacionResumen buscarAsignacion(int asignacionId, int empresaId) {

        String sql = SELECT_ASIGNACION + "AND ap.id_asignacion = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empresaId);
            ps.setInt(2, asignacionId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ActividadAsignacion> listarActividades(int asignacionId) {

        List<ActividadAsignacion> lista = new ArrayList<>();

        String sql = "SELECT id_actividad, fecha, titulo, descripcion, "
                + "hora_inicio, hora_fin, horas, evidencia, estado, observacion "
                + "FROM actividades WHERE id_asignacion = ? "
                + "ORDER BY fecha DESC, id_actividad DESC";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, asignacionId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ActividadAsignacion a = new ActividadAsignacion();

                    a.setId(rs.getInt("id_actividad"));
                    a.setFecha(rs.getDate("fecha").toLocalDate());
                    a.setTitulo(rs.getString("titulo"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setHoraInicio(hora(rs.getTime("hora_inicio")));
                    a.setHoraFin(hora(rs.getTime("hora_fin")));
                    a.setHoras(rs.getDouble("horas"));
                    a.setEvidencia(rs.getString("evidencia"));
                    a.setEstado(rs.getString("estado"));
                    a.setObservacion(rs.getString("observacion"));

                    lista.add(a);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<HorarioAsignacion> listarHorarios(int asignacionId) {

        List<HorarioAsignacion> lista = new ArrayList<>();

        String sql = "SELECT id_horario, dia_semana, hora_inicio, hora_fin "
                + "FROM horarios WHERE id_asignacion = ? "
                + "ORDER BY dia_semana, hora_inicio";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, asignacionId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    HorarioAsignacion h = new HorarioAsignacion();

                    h.setId(rs.getInt("id_horario"));
                    h.setDiaSemana(rs.getInt("dia_semana"));
                    h.setHoraInicio(hora(rs.getTime("hora_inicio")));
                    h.setHoraFin(hora(rs.getTime("hora_fin")));

                    lista.add(h);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<ObservacionAsignacion> listarObservaciones(int asignacionId) {

        List<ObservacionAsignacion> lista = new ArrayList<>();

        String sql = "SELECT ob.id_observacion, ob.texto, ob.fecha_hora, "
                + "u.nombre, u.apellido, r.nombre AS rol "
                + "FROM observaciones_asignacion ob "
                + "JOIN usuarios u ON u.id_usuario = ob.id_usuario "
                + "JOIN roles r ON r.id_rol = u.id_rol "
                + "WHERE ob.id_asignacion = ? "
                + "ORDER BY ob.id_observacion DESC";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, asignacionId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ObservacionAsignacion o = new ObservacionAsignacion();

                    Timestamp ts = rs.getTimestamp("fecha_hora");

                    o.setId(rs.getInt("id_observacion"));
                    o.setTexto(rs.getString("texto"));
                    o.setFechaHora(ts == null ? null : ts.toLocalDateTime());
                    o.setAutor(rs.getString("nombre") + " " + rs.getString("apellido"));
                    o.setRol(rs.getString("rol"));

                    lista.add(o);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean agregarObservacion(int asignacionId, int usuarioId, String texto) {

        String sql = "INSERT INTO observaciones_asignacion "
                + "(id_asignacion, id_usuario, texto) VALUES (?,?,?)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, asignacionId);
            ps.setInt(2, usuarioId);
            ps.setString(3, texto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hora(Time t) {

        if (t == null) {
            return "";
        }

        String s = t.toLocalTime().toString();   // "08:00" o "08:00:30"

        return s.length() >= 5 ? s.substring(0, 5) : s;
    }
}
