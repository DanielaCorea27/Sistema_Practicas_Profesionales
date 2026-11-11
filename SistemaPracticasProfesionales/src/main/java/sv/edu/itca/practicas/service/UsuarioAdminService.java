package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.UsuarioAdminDAO;
import sv.edu.itca.practicas.model.Rol;
import sv.edu.itca.practicas.model.UsuarioAdmin;
import sv.edu.itca.practicas.util.ReglaNegocioException;

public class UsuarioAdminService {

    private static final String PATRON_CORREO =
            "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

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

    public void guardarAdministrador(UsuarioAdmin usuario)
            throws ReglaNegocioException {

        if (usuario == null) {
            throw new ReglaNegocioException("Datos de usuario no válidos.");
        }

        String nombre = limpiar(usuario.getNombre());
        String apellido = limpiar(usuario.getApellido());
        String correo = limpiar(usuario.getCorreo()).toLowerCase();
        String password = usuario.getPassword() == null ? "" : usuario.getPassword();

        if (nombre.isEmpty() || apellido.isEmpty()) {
            throw new ReglaNegocioException("Nombre y apellido son obligatorios.");
        }

        if (nombre.length() > 80 || apellido.length() > 80) {
            throw new ReglaNegocioException("Nombre y apellido admiten máximo 80 caracteres.");
        }

        if (!correo.matches(PATRON_CORREO) || correo.length() > 120) {
            throw new ReglaNegocioException("El correo no es válido.");
        }

        if (password.length() < 8) {
            throw new ReglaNegocioException("La contraseña debe tener al menos 8 caracteres.");
        }

        if (usuarioDAO.existeCorreo(correo)) {
            throw new ReglaNegocioException("Ya existe un usuario con ese correo.");
        }

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        usuario.setRolEnum(Rol.ADMINISTRADOR);

        if (!usuarioDAO.insertar(usuario)) {
            throw new ReglaNegocioException("No se pudo guardar el usuario. Intenta de nuevo.");
        }
    }

    public void cambiarEstado(int id, int idUsuarioSesion)
            throws ReglaNegocioException {

        if (id == idUsuarioSesion) {
            throw new ReglaNegocioException("No puedes desactivar tu propio usuario.");
        }

        if (!usuarioDAO.cambiarEstado(id)) {
            throw new ReglaNegocioException("No se encontró el usuario.");
        }
    }

    public int contarTodos() {
        return usuarioDAO.contarTodos();
    }

    public int contarActivos() {
        return usuarioDAO.contarActivos();
    }

    public int contarPorRol(Rol rol) {
        return usuarioDAO.contarPorRol(rol);
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}