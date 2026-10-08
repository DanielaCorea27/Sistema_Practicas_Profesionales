package sv.edu.itca.practicas.service;
/**
 *
 * @author danie
 */
import sv.edu.itca.practicas.dao.EmpresaDAO;
import sv.edu.itca.practicas.dao.EmpresaDashboardDAO;
import sv.edu.itca.practicas.dao.RepresentanteDAO;
import sv.edu.itca.practicas.model.Empresa;
import sv.edu.itca.practicas.model.Representante;

public class EmpresaPanelService {

    private final EmpresaDashboardDAO dashboardDAO = new EmpresaDashboardDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final RepresentanteDAO representanteDAO = new RepresentanteDAO();

    // ----- Dashboard -----

    public int totalOportunidades(int empresaId) {
        return dashboardDAO.contarOportunidades(empresaId);
    }

    public int postulacionesPendientes(int empresaId) {
        return dashboardDAO.contarPostulacionesPendientes(empresaId);
    }

    public int estudiantesActivos(int empresaId) {
        return dashboardDAO.contarEstudiantesActivos(empresaId);
    }

    public int evaluacionesPendientes(int empresaId) {
        return dashboardDAO.contarEvaluacionesPendientes(empresaId);
    }

    // ----- Perfil -----

    public Empresa obtenerEmpresa(int empresaId) {
        return empresaDAO.buscarPorId(empresaId);
    }

    public Representante obtenerRepresentantePorUsuario(int usuarioId) {
        return representanteDAO.buscarPorUsuario(usuarioId);
    }

    /**
     * Actualiza los datos de la empresa y el cargo del representante.
     * Devuelve un mensaje de error o null si todo salio bien.
     */
    public String guardarPerfil(Empresa datos, Representante rep, String cargo) {

        if (datos.getNombre() == null || datos.getNombre().trim().isEmpty()) {
            return "El nombre de la empresa es obligatorio.";
        }

        Empresa actual = empresaDAO.buscarPorId(rep.getEmpresaId());

        if (actual == null) {
            return "No se encontró la empresa.";
        }

        actual.setNombre(datos.getNombre().trim());
        actual.setDescripcion(datos.getDescripcion());
        actual.setDireccion(datos.getDireccion());
        actual.setTelefono(datos.getTelefono());
        actual.setCorreo(datos.getCorreo());
        actual.setSitioWeb(datos.getSitioWeb());
        actual.setContacto(datos.getContacto());

        if (!empresaDAO.actualizar(actual)) {
            return "No se pudo guardar la información de la empresa.";
        }

        representanteDAO.actualizarCargo(rep.getId(), cargo);

        return null;
    }
}
