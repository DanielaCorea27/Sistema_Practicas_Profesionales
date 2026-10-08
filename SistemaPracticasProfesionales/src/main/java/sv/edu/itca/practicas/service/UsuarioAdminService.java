package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.UsuarioAdminDAO;
import sv.edu.itca.practicas.model.UsuarioAdmin;

public class UsuarioAdminService {

    private final UsuarioAdminDAO usuarioDAO;

    public UsuarioAdminService() {
        usuarioDAO = new UsuarioAdminDAO();
    }

    public List<UsuarioAdmin> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public UsuarioAdmin buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public boolean guardar(UsuarioAdmin usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getNombre() == null
                || usuario.getNombre().trim().isEmpty()) {
            return false;
        }

        if (usuario.getUsuario() == null
                || usuario.getUsuario().trim().isEmpty()) {
            return false;
        }

        if (usuario.getRol() == null
                || usuario.getRol().trim().isEmpty()) {
            return false;
        }

        usuario.setNombre(
                usuario.getNombre().trim()
        );

        usuario.setUsuario(
                usuario.getUsuario().trim()
        );

        usuario.setEstado("ACTIVO");

        return usuarioDAO.insertar(usuario);
    }

    public boolean cambiarEstado(int id) {
        return usuarioDAO.cambiarEstado(id);
    }

    public int contarTodos() {
        return usuarioDAO.contarTodos();
    }

    public int contarActivos() {
        return usuarioDAO.contarActivos();
    }
}