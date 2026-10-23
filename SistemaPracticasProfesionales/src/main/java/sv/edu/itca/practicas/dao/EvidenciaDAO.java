package sv.edu.itca.practicas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.model.EvidenciaResumen;
import sv.edu.itca.practicas.util.Conexion;

/**
 * Evidencias del alumno (solo texto, sin subir archivos todavia).
 *
 * La BD no tiene tabla de evidencias: solo la columna actividades.evidencia.
 * Por eso una evidencia se guarda como una fila de ACTIVIDADES con
 * horas = 0 (no suma al avance) y evidencia = tipo (INFORME, CAPTURA...).
 * RegistroHoraDAO ignora esas filas (usa horas > 0).
 */
public class EvidenciaDAO {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final String SELECT =
            "SELECT x.id_actividad, pa.id_alumno, "
            + "CONCAT(u.nombre, ' ', u.apellido) AS alumno, "
            + "x.titulo, x.descripcion, x.evidencia, x.fecha, x.estado "
            + "FROM actividades x "
            + "JOIN asignaciones_pasantia ap ON ap.id_asignacion = x.id_asignacion "
            + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia "
            + "JOIN alumnos a ON a.id_alumno = pa.id_alumno "
            + "JOIN usuarios u ON u.id_usuario = a.id_usuario "
            + "WHERE x.evidencia IS NOT NULL AND x.horas = 0 ";

    private static final String ORDEN =
            "ORDER BY x.fecha DESC, x.id_actividad DESC";

    private final AlumnoPortalDAO portalDAO = new AlumnoPortalDAO();

    private EvidenciaResumen mapear(ResultSet rs) throws SQLException {

        return new EvidenciaResumen(
                rs.getInt("id_actividad"),
                rs.getInt("id_alumno"),
                rs.getString("alumno"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("evidencia"),
                rs.getDate("fecha").toLocalDate().format(FORMATO),
                estadoVista(rs.getString("estado"))
        );
    }

    /** La BD usa APROBADO/RECHAZADO; las vistas esperan APROBADA/RECHAZADA. */
    private String estadoVista(String estado) {

        if ("APROBADO".equals(estado)) {
            return "APROBADA";
        }

        if ("RECHAZADO".equals(estado)) {
            return "RECHAZADA";
        }

        return "PENDIENTE";
    }

    private List<EvidenciaResumen> consultar(String sql, Integer parametro) {

        List<EvidenciaResumen> lista = new ArrayList<>();

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

    public List<EvidenciaResumen> listarTodos() {
        return consultar(SELECT + ORDEN, null);
    }

    public List<EvidenciaResumen> listarPorAlumno(int alumnoId) {

        if (alumnoId <= 0) {
            return new ArrayList<>();
        }

        return consultar(SELECT + "AND pa.id_alumno = ? " + ORDEN, alumnoId);
    }

    public EvidenciaResumen buscarPorId(int id) {

        List<EvidenciaResumen> lista = consultar(
                SELECT + "AND x.id_actividad = ? " + ORDEN, id);

        return lista.isEmpty() ? null : lista.get(0);
    }

    /** false si el alumno no tiene una asignacion ACTIVA o falla la BD. */
    public boolean insertar(EvidenciaResumen evidencia) {

        int asignacionId = portalDAO.buscarAsignacionActiva(
                evidencia.getAlumnoId());

        if (asignacionId == 0) {
            return false;
        }

        String sql = "INSERT INTO actividades (id_asignacion, fecha, titulo, "
                + "descripcion, hora_inicio, hora_fin, horas, evidencia, estado) "
                + "VALUES (?, CURDATE(), ?, ?, '00:00:00', '00:00:00', 0, ?, 'PENDIENTE')";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, asignacionId);
            ps.setString(2, recortar(evidencia.getTitulo(), 150));
            ps.setString(3, evidencia.getDescripcion());
            ps.setString(4, recortar(evidencia.getTipo(), 255));

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    evidencia.setId(keys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Elimina una evidencia del propio alumno (las APROBADAS no se eliminan). */
    public boolean eliminar(int id, int alumnoId) {

        String sql = "DELETE x FROM actividades x "
                + "JOIN asignaciones_pasantia ap ON ap.id_asignacion = x.id_asignacion "
                + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia "
                + "WHERE x.id_actividad = ? AND pa.id_alumno = ? "
                + "AND x.evidencia IS NOT NULL AND x.horas = 0 "
                + "AND x.estado <> 'APROBADO'";

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

    public boolean aprobar(int id) {
        return cambiarEstado(id, "APROBADO");
    }

    public boolean rechazar(int id) {
        return cambiarEstado(id, "RECHAZADO");
    }

    private boolean cambiarEstado(int id, String estado) {

        String sql = "UPDATE actividades SET estado = ? "
                + "WHERE id_actividad = ? AND evidencia IS NOT NULL AND horas = 0";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarPendientes() {
        return contar("PENDIENTE");
    }

    public int contarAprobadas() {
        return contar("APROBADO");
    }

    private int contar(String estado) {

        String sql = "SELECT COUNT(*) FROM actividades "
                + "WHERE evidencia IS NOT NULL AND horas = 0 AND estado = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String recortar(String s, int max) {

        String t = s == null ? "" : s.trim();

        return t.length() > max ? t.substring(0, max) : t;
    }
}
