package sv.edu.itca.practicas.service;

import java.util.Arrays;
import java.util.List;
import sv.edu.itca.practicas.dao.SeguimientoDAO;
import sv.edu.itca.practicas.model.ActividadAsignacion;
import sv.edu.itca.practicas.model.AsignacionResumen;
import sv.edu.itca.practicas.model.HorarioAsignacion;
import sv.edu.itca.practicas.model.ObservacionAsignacion;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Seguimiento de los estudiantes que estan haciendo practica en una empresa.
 */
public class SeguimientoService {

    private static final List<String> ESTADOS =
            Arrays.asList("ACTIVA", "FINALIZADA", "CANCELADA");

    public static final int MAX_OBSERVACION = 1000;

    private final SeguimientoDAO dao = new SeguimientoDAO();

    /** Si el estado no es valido se listan todas. */
    public List<AsignacionResumen> listar(int empresaId, String estado) {

        String filtro = estado != null && ESTADOS.contains(estado)
                ? estado
                : null;

        return dao.listarPorEmpresa(empresaId, filtro);
    }

    /** null si no existe o pertenece a otra empresa. */
    public AsignacionResumen obtener(int asignacionId, int empresaId) {
        return dao.buscarAsignacion(asignacionId, empresaId);
    }

    public List<ActividadAsignacion> actividades(int asignacionId) {
        return dao.listarActividades(asignacionId);
    }

    public List<HorarioAsignacion> horarios(int asignacionId) {
        return dao.listarHorarios(asignacionId);
    }

    public List<ObservacionAsignacion> observaciones(int asignacionId) {
        return dao.listarObservaciones(asignacionId);
    }

    /**
     * Agrega una observacion de la empresa a una de SUS asignaciones.
     */
    public void agregarObservacion(
            int asignacionId, int empresaId, int usuarioId, String texto)
            throws ReglaNegocioException {

        if (texto == null || texto.trim().isEmpty()) {
            throw new ReglaNegocioException("Escribe la observación.");
        }

        texto = texto.trim();

        if (texto.length() > MAX_OBSERVACION) {
            throw new ReglaNegocioException(
                    "La observación no puede superar " + MAX_OBSERVACION
                    + " caracteres.");
        }

        // Garantiza que la asignacion sea de esta empresa.
        if (dao.buscarAsignacion(asignacionId, empresaId) == null) {
            throw new ReglaNegocioException(
                    "No se encontró la asignación.");
        }

        if (!dao.agregarObservacion(asignacionId, usuarioId, texto)) {
            throw new ReglaNegocioException(
                    "No se pudo guardar la observación.");
        }
    }
}
