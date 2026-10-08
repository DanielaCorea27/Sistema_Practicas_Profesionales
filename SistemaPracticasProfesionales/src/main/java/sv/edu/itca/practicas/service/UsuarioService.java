package sv.edu.itca.practicas.service;

import java.util.List;
import sv.edu.itca.practicas.dao.UsuarioDAO;
import sv.edu.itca.practicas.model.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(
            String correo,
            String password) {

        return usuarioDAO.autenticar(
                correo,
                password
        );
    }

    public Usuario buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public int contarTodos() {
        return usuarioDAO.contarTodos();
    }

    public int contarActivos() {
        return usuarioDAO.contarActivos();
    }
}