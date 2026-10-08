package sv.edu.itca.practicas.service;

import java.util.Arrays;
import java.util.List;
import sv.edu.itca.practicas.dao.OportunidadDAO;
import sv.edu.itca.practicas.model.Oportunidad;

/**
 * Validaciones y reglas de las oportunidades de la empresa.
 */
public class OportunidadService {

    public static final List<String> MODALIDADES =
            Arrays.asList("PRESENCIAL", "REMOTA", "HIBRIDA");

    /** Maximo de horas que puede cubrir una oportunidad (Ingenieria). */
    public static final int HORAS_MAXIMAS = 640;

    private final OportunidadDAO dao = new OportunidadDAO();

    public List<Oportunidad> listarPorEmpresa(int empresaId) {
        return dao.listarPorEmpresa(empresaId);
    }

    public Oportunidad buscar(int id, int empresaId) {
        return dao.buscarPorIdYEmpresa(id, empresaId);
    }

    /**
     * Valida los datos. Devuelve el mensaje de error o null si es valido.
     */
    public String validar(Oportunidad o) {

        if (esVacio(o.getTitulo())) {
            return "El título es obligatorio.";
        }

        if (esVacio(o.getDescripcion())) {
            return "La descripción es obligatoria.";
        }

        if (o.getModalidad() == null
                || !MODALIDADES.contains(o.getModalidad())) {
            return "Selecciona una modalidad válida.";
        }

        if (o.getHorasOfrecidas() <= 0
                || o.getHorasOfrecidas() > HORAS_MAXIMAS) {
            return "Las horas ofrecidas deben estar entre 1 y "
                    + HORAS_MAXIMAS + ".";
        }

        if (o.getFechaInicio() != null && o.getFechaFin() != null
                && o.getFechaFin().isBefore(o.getFechaInicio())) {
            return "La fecha de fin no puede ser anterior a la de inicio.";
        }

        return null;
    }

    public String crear(Oportunidad o) {

        String error = validar(o);

        if (error != null) {
            return error;
        }

        return dao.insertar(o) ? null : "No se pudo guardar la oportunidad.";
    }

    /**
     * Edita una oportunidad. Al editarla vuelve a PENDIENTE para que el
     * maestro la revise otra vez.
     */
    public String editar(Oportunidad o) {

        String error = validar(o);

        if (error != null) {
            return error;
        }

        return dao.actualizar(o)
                ? null
                : "No se pudo actualizar (puede estar cerrada).";
    }

    public boolean cerrar(int id, int empresaId) {
        return dao.cerrar(id, empresaId);
    }

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
