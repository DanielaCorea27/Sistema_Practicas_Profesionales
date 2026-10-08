package sv.edu.itca.practicas.service;

import java.util.Arrays;
import java.util.List;
import sv.edu.itca.practicas.dao.PostulacionDAO;
import sv.edu.itca.practicas.model.Postulante;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Reglas de postulaciones desde la empresa.
 */
public class PostulacionService {

    private static final List<String> ESTADOS =
            Arrays.asList("PENDIENTE", "ACEPTADA", "RECHAZADA");

    private final PostulacionDAO dao = new PostulacionDAO();

    /**
     * @param estado filtro; si no es un estado valido se listan todas.
     */
    public List<Postulante> listar(int empresaId, String estado) {

        String filtro = estado != null && ESTADOS.contains(estado)
                ? estado
                : null;

        return dao.listarPorEmpresa(empresaId, filtro);
    }

    /** @return horas planificadas asignadas al estudiante en esta empresa. */
    public int aceptar(int postulacionId, int empresaId, int representanteId)
            throws ReglaNegocioException {

        return dao.aceptar(postulacionId, empresaId, representanteId);
    }

    public void rechazar(int postulacionId, int empresaId)
            throws ReglaNegocioException {

        if (!dao.rechazar(postulacionId, empresaId)) {
            throw new ReglaNegocioException(
                    "No se pudo rechazar: la postulación no existe "
                    + "o ya fue respondida.");
        }
    }
}
