package sv.edu.itca.practicas.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import sv.edu.itca.practicas.dao.EvaluacionDAO;
import sv.edu.itca.practicas.dao.SeguimientoDAO;
import sv.edu.itca.practicas.model.AsignacionEvaluable;
import sv.edu.itca.practicas.model.AsignacionResumen;
import sv.edu.itca.practicas.model.EvaluacionRegistrada;
import sv.edu.itca.practicas.model.PreguntaEvaluacion;
import sv.edu.itca.practicas.model.RespuestaEvaluada;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Evaluacion final que hace la EMPRESA sobre el estudiante.
 */
public class EvaluacionService {

    public static final int MAX_OBSERVACIONES = 1000;

    private static final BigDecimal MINIMO = new BigDecimal("1");
    private static final BigDecimal MAXIMO = new BigDecimal("10");

    private final EvaluacionDAO dao = new EvaluacionDAO();
    private final SeguimientoDAO seguimientoDAO = new SeguimientoDAO();

    public List<AsignacionEvaluable> listar(int empresaId) {
        return dao.listarEvaluables(empresaId);
    }

    /** null si no existe o es de otra empresa. */
    public AsignacionResumen obtenerAsignacion(int asignacionId, int empresaId) {
        return seguimientoDAO.buscarAsignacion(asignacionId, empresaId);
    }

    public List<PreguntaEvaluacion> preguntasActivas() {
        return dao.listarPreguntasActivas();
    }

    public EvaluacionRegistrada evaluacionDeEmpresa(int asignacionId) {
        return dao.buscarEvaluacion(asignacionId, "EMPRESA");
    }

    public boolean tutorYaEvaluo(int asignacionId) {
        return dao.existeEvaluacion(asignacionId, "MAESTRO");
    }

    public List<RespuestaEvaluada> respuestas(int evaluacionId) {
        return dao.listarRespuestas(evaluacionId);
    }

    /**
     * Valida y guarda la evaluacion.
     *
     * @param respuestas pregunta -> opcion elegida
     * @return codigo de FinalizacionPasantia
     */
    public int guardar(
            int asignacionId,
            int empresaId,
            int usuarioId,
            String calificacionTexto,
            String observaciones,
            Map<Integer, Integer> respuestas)
            throws ReglaNegocioException {

        // Calificacion 1 a 10.
        BigDecimal calificacion;

        try {

            calificacion = new BigDecimal(
                    calificacionTexto.trim().replace(',', '.'));

        } catch (Exception e) {
            throw new ReglaNegocioException(
                    "La calificación debe ser un número entre 1 y 10.");
        }

        calificacion = calificacion.setScale(2, RoundingMode.HALF_UP);

        if (calificacion.compareTo(MINIMO) < 0
                || calificacion.compareTo(MAXIMO) > 0) {
            throw new ReglaNegocioException(
                    "La calificación debe estar entre 1 y 10.");
        }

        // Observaciones (opcionales).
        String obs = observaciones == null ? "" : observaciones.trim();

        if (obs.length() > MAX_OBSERVACIONES) {
            throw new ReglaNegocioException(
                    "Las observaciones no pueden superar "
                    + MAX_OBSERVACIONES + " caracteres.");
        }

        // Todas las preguntas activas contestadas, con una opcion valida.
        Map<Integer, Integer> limpias = new LinkedHashMap<>();

        for (PreguntaEvaluacion p : dao.listarPreguntasActivas()) {

            Integer opcion = respuestas.get(p.getId());

            if (opcion == null) {
                throw new ReglaNegocioException(
                        "Responde todas las preguntas. Falta: \""
                        + p.getTexto() + "\".");
            }

            if (!p.tieneOpcion(opcion)) {
                throw new ReglaNegocioException(
                        "Respuesta no válida en: \"" + p.getTexto() + "\".");
            }

            limpias.put(p.getId(), opcion);
        }

        return dao.guardarComoEmpresa(
                asignacionId, empresaId, usuarioId,
                calificacion, obs.isEmpty() ? null : obs, limpias);
    }
}
