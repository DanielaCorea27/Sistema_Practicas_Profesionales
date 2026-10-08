package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.MaestroAdminDAO;
import sv.edu.itca.practicas.model.MaestroAdmin;

public class MaestroAdminService {

    private final MaestroAdminDAO maestroDAO;

    public MaestroAdminService() {
        maestroDAO = new MaestroAdminDAO();
    }

    public List<MaestroAdmin> listarTodos() {

        return maestroDAO.listarTodos();
    }

    public MaestroAdmin buscarPorId(int id) {

        return maestroDAO.buscarPorId(id);
    }

    public boolean guardar(MaestroAdmin maestro) {

        if (maestro == null) {
            return false;
        }

        if (maestro.getNombre() == null
                || maestro.getNombre().trim().isEmpty()) {
            return false;
        }

        if (maestro.getUsuario() == null
                || maestro.getUsuario().trim().isEmpty()) {
            return false;
        }

        if (maestro.getEspecialidad() == null
                || maestro.getEspecialidad().trim().isEmpty()) {
            return false;
        }

        maestro.setNombre(
                maestro.getNombre().trim()
        );

        maestro.setUsuario(
                maestro.getUsuario().trim()
        );

        maestro.setEspecialidad(
                maestro.getEspecialidad().trim()
        );

        maestro.setEstado("ACTIVO");

        return maestroDAO.insertar(maestro);
    }

    public boolean cambiarEstado(int id) {

        return maestroDAO.cambiarEstado(id);
    }

    public int contarTodos() {

        return maestroDAO.contarTodos();
    }

    public int contarActivos() {

        return maestroDAO.contarActivos();
    }
}