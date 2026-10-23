package sv.edu.itca.practicas.service;

import java.util.Arrays;
import java.util.List;

import sv.edu.itca.practicas.dao.EvidenciaDAO;
import sv.edu.itca.practicas.model.EvidenciaResumen;

public class EvidenciaService {

    private static final List<String> TIPOS =
            Arrays.asList("INFORME", "CAPTURA", "DOCUMENTO", "OTRO");

    private final EvidenciaDAO evidenciaDAO;

    public EvidenciaService() {
        evidenciaDAO = new EvidenciaDAO();
    }

    public List<EvidenciaResumen> listarTodos() {
        return evidenciaDAO.listarTodos();
    }

    public List<EvidenciaResumen> listarPorAlumno(int alumnoId) {
        return evidenciaDAO.listarPorAlumno(alumnoId);
    }

    public EvidenciaResumen buscarPorId(int id) {
        return evidenciaDAO.buscarPorId(id);
    }

    public boolean guardar(EvidenciaResumen evidencia) {

        if (evidencia == null) {
            return false;
        }

        if (evidencia.getAlumnoId() <= 0) {
            return false;
        }

        if (evidencia.getTitulo() == null
                || evidencia.getTitulo().trim().isEmpty()) {
            return false;
        }

        if (evidencia.getDescripcion() == null
                || evidencia.getDescripcion().trim().isEmpty()) {
            return false;
        }

        evidencia.setTitulo(
                evidencia.getTitulo().trim()
        );

        evidencia.setDescripcion(
                evidencia.getDescripcion().trim()
        );

        // El tipo se guarda en la columna actividades.evidencia.
        String tipo = evidencia.getTipo() == null
                ? ""
                : evidencia.getTipo().trim().toUpperCase();

        evidencia.setTipo(TIPOS.contains(tipo) ? tipo : "OTRO");

        if (evidencia.getEstado() == null
                || evidencia.getEstado().trim().isEmpty()) {

            evidencia.setEstado("PENDIENTE");
        }

        return evidenciaDAO.insertar(evidencia);
    }

    /** Elimina una evidencia SOLO si es del alumno y no esta aprobada. */
    public boolean eliminar(int id, int alumnoId) {

        if (id <= 0 || alumnoId <= 0) {
            return false;
        }

        return evidenciaDAO.eliminar(id, alumnoId);
    }

    public boolean aprobar(int id) {
        return evidenciaDAO.aprobar(id);
    }

    public boolean rechazar(int id) {
        return evidenciaDAO.rechazar(id);
    }

    public int contarPendientes() {
        return evidenciaDAO.contarPendientes();
    }

    public int contarAprobadas() {
        return evidenciaDAO.contarAprobadas();
    }
}
