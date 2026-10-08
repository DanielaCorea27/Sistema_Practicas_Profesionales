package sv.edu.itca.practicas.dao;
/**
 *
 * @author danie
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import sv.edu.itca.practicas.model.AsignacionEvaluable;
import sv.edu.itca.practicas.model.EvaluacionRegistrada;
import sv.edu.itca.practicas.model.OpcionEvaluacion;
import sv.edu.itca.practicas.model.PreguntaEvaluacion;
import sv.edu.itca.practicas.model.RespuestaEvaluada;
import sv.edu.itca.practicas.util.Conexion;
import sv.edu.itca.practicas.util.ReglaNegocioException;


public class EvaluacionDAO {

    // ------------------------------------------------------------------
    //  Preguntas
    // ------------------------------------------------------------------

    /** Preguntas ACTIVAS con sus opciones (las mismas para empresa y tutor). */
    public List<PreguntaEvaluacion> listarPreguntasActivas() {

        Map<Integer, PreguntaEvaluacion> mapa = new LinkedHashMap<>();

        try (Connection con = Conexion.obtenerConexion()) {

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT id_pregunta, pregunta FROM preguntas_evaluacion "
                    + "WHERE estado = 1 ORDER BY id_pregunta");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    PreguntaEvaluacion p = new PreguntaEvaluacion();
                    p.setId(rs.getInt("id_pregunta"));
                    p.setTexto(rs.getString("pregunta"));

                    mapa.put(p.getId(), p);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT o.id_opcion, o.id_pregunta, o.opcion "
                    + "FROM opciones_evaluacion o "
                    + "JOIN preguntas_evaluacion p "
                    + "  ON p.id_pregunta = o.id_pregunta "
                    + "WHERE p.estado = 1 "
                    + "ORDER BY o.id_pregunta, o.id_opcion");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    PreguntaEvaluacion p = mapa.get(rs.getInt("id_pregunta"));

                    if (p != null) {
                        p.getOpciones().add(new OpcionEvaluacion(
                                rs.getInt("id_opcion"),
                                rs.getString("opcion")));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(mapa.values());
    }

    // ------------------------------------------------------------------
    //  Consultas
    // ------------------------------------------------------------------

    /** Asignaciones ACTIVAS o FINALIZADAS de la empresa (pendientes primero). */
    public List<AsignacionEvaluable> listarEvaluables(int empresaId) {

        List<AsignacionEvaluable> lista = new ArrayList<>();

        String sql = "SELECT ap.id_asignacion, ap.estado, ap.horas_planificadas, "
                + "u.nombre, u.apellido, a.carnet, c.nombre AS carrera, "
                + "mu.nombre AS m_nombre, mu.apellido AS m_apellido, "
                + "COALESCE((SELECT SUM(x.horas) FROM actividades x "
                + "  WHERE x.id_asignacion = ap.id_asignacion "
                + "    AND x.estado = 'APROBADO'), 0) AS h_aprobadas, "
                + "(SELECT ev.calificacion FROM evaluaciones ev "
                + "  WHERE ev.id_asignacion = ap.id_asignacion "
                + "    AND ev.tipo_evaluador = 'EMPRESA') AS cal_empresa, "
                + "(SELECT COUNT(*) FROM evaluaciones ev "
                + "  WHERE ev.id_asignacion = ap.id_asignacion "
                + "    AND ev.tipo_evaluador = 'MAESTRO') AS eval_tutor "
                + "FROM asignaciones_pasantia ap "
                + "JOIN pasantias pa ON pa.id_pasantia = ap.id_pasantia "
                + "JOIN alumnos a ON a.id_alumno = pa.id_alumno "
                + "JOIN usuarios u ON u.id_usuario = a.id_usuario "
                + "JOIN carreras c ON c.id_carrera = a.id_carrera "
                + "LEFT JOIN maestros m ON m.id_maestro = pa.id_maestro "
                + "LEFT JOIN usuarios mu ON mu.id_usuario = m.id_usuario "
                + "WHERE ap.id_empresa = ? "
                + "AND ap.estado IN ('ACTIVA','FINALIZADA') "
                + "ORDER BY ap.id_asignacion DESC";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empresaId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    AsignacionEvaluable e = new AsignacionEvaluable();

                    e.setAsignacionId(rs.getInt("id_asignacion"));
                    e.setAlumnoNombre(
                            rs.getString("nombre") + " " + rs.getString("apellido"));
                    e.setCarnet(rs.getString("carnet"));
                    e.setCarrera(rs.getString("carrera"));

                    String mn = rs.getString("m_nombre");
                    e.setMaestroNombre(mn == null
                            ? null : mn + " " + rs.getString("m_apellido"));

                    e.setEstado(rs.getString("estado"));
                    e.setHorasPlanificadas(rs.getInt("horas_planificadas"));
                    e.setHorasAprobadas(rs.getDouble("h_aprobadas"));

                    BigDecimal cal = rs.getBigDecimal("cal_empresa");
                    e.setCalificacionEmpresa(cal == null ? null : cal.doubleValue());

                    e.setEvaluadoPorTutor(rs.getInt("eval_tutor") > 0);

                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Pendientes de evaluar primero (el orden del resto se mantiene).
        lista.sort(Comparator.comparingInt(
                (AsignacionEvaluable e) -> e.isEvaluadoPorEmpresa() ? 1 : 0));

        return lista;
    }

    public EvaluacionRegistrada buscarEvaluacion(int asignacionId, String tipo) {

        String sql = "SELECT ev.id_evaluacion, ev.calificacion, "
                + "ev.observaciones, ev.fecha, u.nombre, u.apellido "
                + "FROM evaluaciones ev "
                + "JOIN usuarios u ON u.id_usuario = ev.id_evaluador "
                + "WHERE ev.id_asignacion = ? AND ev.tipo_evaluador = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, asignacionId);
            ps.setString(2, tipo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    EvaluacionRegistrada ev = new EvaluacionRegistrada();

                    ev.setId(rs.getInt("id_evaluacion"));
                    ev.setCalificacion(rs.getBigDecimal("calificacion").doubleValue());
                    ev.setObservaciones(rs.getString("observaciones"));
                    ev.setFecha(rs.getDate("fecha").toLocalDate());
                    ev.setEvaluador(
                            rs.getString("nombre") + " " + rs.getString("apellido"));

                    return ev;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** Si el evaluador del tipo indicado ya registro su evaluacion. */
    public boolean existeEvaluacion(int asignacionId, String tipo) {

        String sql = "SELECT COUNT(*) FROM evaluaciones "
                + "WHERE id_asignacion = ? AND tipo_evaluador = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, asignacionId);
            ps.setString(2, tipo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Respuestas tal como se contestaron (pregunta y opcion en texto). */
    public List<RespuestaEvaluada> listarRespuestas(int evaluacionId) {

        List<RespuestaEvaluada> lista = new ArrayList<>();

        String sql = "SELECT p.pregunta, o.opcion "
                + "FROM respuestas_evaluacion r "
                + "JOIN preguntas_evaluacion p ON p.id_pregunta = r.id_pregunta "
                + "JOIN opciones_evaluacion o ON o.id_opcion = r.id_opcion "
                + "WHERE r.id_evaluacion = ? ORDER BY p.id_pregunta";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, evaluacionId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(new RespuestaEvaluada(
                            rs.getString("pregunta"), rs.getString("opcion")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ------------------------------------------------------------------
    //  Guardar
    // ------------------------------------------------------------------

    /**
     * Guarda la evaluacion de la EMPRESA en una sola transaccion:
     * evaluacion + respuestas + revision de cierre de la pasantia.
     *
     * @return FinalizacionPasantia.SOLO_GUARDADA / ASIGNACION_FINALIZADA /
     *         PASANTIA_FINALIZADA
     */
    public int guardarComoEmpresa(
            int asignacionId,
            int empresaId,
            int usuarioId,
            BigDecimal calificacion,
            String observaciones,
            Map<Integer, Integer> respuestas)
            throws ReglaNegocioException {

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try {

                // 1. Bloquear la asignacion y comprobar que es de ESTA empresa.
                String estado = null;

                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT estado FROM asignaciones_pasantia "
                        + "WHERE id_asignacion = ? AND id_empresa = ? "
                        + "FOR UPDATE")) {

                    ps.setInt(1, asignacionId);
                    ps.setInt(2, empresaId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {
                            estado = rs.getString(1);
                        }
                    }
                }

                if (estado == null) {
                    throw new ReglaNegocioException(
                            "No se encontró la asignación.");
                }

                if ("CANCELADA".equals(estado)) {
                    throw new ReglaNegocioException(
                            "No se puede evaluar una asignación cancelada.");
                }

                // 2. Una sola evaluacion de la empresa por asignacion.
                if (existeEvaluacion(con, asignacionId, "EMPRESA")) {
                    throw new ReglaNegocioException(
                            "Ya registraste la evaluación de este estudiante.");
                }

                // 3. Insertar y revisar cierre.
                registrar(con, asignacionId, usuarioId, "EMPRESA",
                        calificacion, observaciones, respuestas);

                int resultado = FinalizacionPasantia.revisar(con, asignacionId);

                con.commit();

                return resultado;

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

    /**
     * Inserta una evaluacion y sus respuestas con la conexion recibida
     * (no hace commit). Reutilizable por el modulo del TUTOR con
     * tipo = "MAESTRO".
     */
    public void registrar(
            Connection con,
            int asignacionId,
            int usuarioId,
            String tipo,
            BigDecimal calificacion,
            String observaciones,
            Map<Integer, Integer> respuestas)
            throws SQLException {

        int evaluacionId;

        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO evaluaciones (id_asignacion, id_evaluador, "
                + "tipo_evaluador, calificacion, observaciones, fecha) "
                + "VALUES (?,?,?,?,?, CURDATE())",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, asignacionId);
            ps.setInt(2, usuarioId);
            ps.setString(3, tipo);
            ps.setBigDecimal(4, calificacion);
            ps.setString(5, observaciones);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                keys.next();
                evaluacionId = keys.getInt(1);
            }
        }

        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO respuestas_evaluacion "
                + "(id_evaluacion, id_pregunta, id_opcion) VALUES (?,?,?)")) {

            for (Map.Entry<Integer, Integer> r : respuestas.entrySet()) {

                ps.setInt(1, evaluacionId);
                ps.setInt(2, r.getKey());
                ps.setInt(3, r.getValue());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    private boolean existeEvaluacion(Connection con, int asignacionId, String tipo)
            throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM evaluaciones "
                + "WHERE id_asignacion = ? AND tipo_evaluador = ?")) {

            ps.setInt(1, asignacionId);
            ps.setString(2, tipo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
