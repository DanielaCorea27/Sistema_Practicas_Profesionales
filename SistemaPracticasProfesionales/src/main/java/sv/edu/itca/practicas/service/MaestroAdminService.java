package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.MaestroAdminDAO;
import sv.edu.itca.practicas.model.MaestroAdmin;
import sv.edu.itca.practicas.util.ReglaNegocioException;

public class MaestroAdminService {

    private static final String PATRON_CORREO =
            "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

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

    public void guardar(MaestroAdmin maestro) throws ReglaNegocioException {

        if (maestro == null) {
            throw new ReglaNegocioException("Datos del maestro no válidos.");
        }

        String nombre = limpiar(maestro.getNombre());
        String apellido = limpiar(maestro.getApellido());
        String correo = limpiar(maestro.getCorreo()).toLowerCase();
        String codigo = limpiar(maestro.getCodigoEmpleado()).toUpperCase();
        String especialidad = limpiar(maestro.getEspecialidad());
        String telefono = limpiar(maestro.getTelefono());
        String password = maestro.getPassword() == null ? "" : maestro.getPassword();

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

        if (codigo.isEmpty() || codigo.length() > 20) {
            throw new ReglaNegocioException("El código de empleado es obligatorio (máximo 20 caracteres).");
        }

        if (especialidad.length() > 100) {
            throw new ReglaNegocioException("La especialidad admite máximo 100 caracteres.");
        }

        if (telefono.length() > 20) {
            throw new ReglaNegocioException("El teléfono admite máximo 20 caracteres.");
        }

        if (maestroDAO.existeCorreo(correo)) {
            throw new ReglaNegocioException("Ya existe un usuario con ese correo.");
        }

        if (maestroDAO.existeCodigo(codigo)) {
            throw new ReglaNegocioException("Ya existe un maestro con ese código de empleado.");
        }

        maestro.setNombre(nombre);
        maestro.setApellido(apellido);
        maestro.setCorreo(correo);
        maestro.setCodigoEmpleado(codigo);
        maestro.setEspecialidad(especialidad.isEmpty() ? null : especialidad);
        maestro.setTelefono(telefono.isEmpty() ? null : telefono);

        if (!maestroDAO.insertar(maestro)) {
            throw new ReglaNegocioException("No se pudo guardar el maestro. Intenta de nuevo.");
        }
    }

    public void cambiarEstado(int id) throws ReglaNegocioException {

        if (!maestroDAO.cambiarEstado(id)) {
            throw new ReglaNegocioException("No se encontró el maestro.");
        }
    }

    public int contarTodos() {
        return maestroDAO.contarTodos();
    }

    public int contarActivos() {
        return maestroDAO.contarActivos();
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
