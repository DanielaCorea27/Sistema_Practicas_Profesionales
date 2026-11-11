package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.AlumnoAdminDAO;
import sv.edu.itca.practicas.model.AlumnoAdmin;
import sv.edu.itca.practicas.util.ReglaNegocioException;

public class AlumnoAdminService {

    private static final String PATRON_CORREO =
            "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private final AlumnoAdminDAO alumnoDAO;

    public AlumnoAdminService() {
        alumnoDAO = new AlumnoAdminDAO();
    }

    public List<AlumnoAdmin> listarTodos() {
        return alumnoDAO.listarTodos();
    }

    public AlumnoAdmin buscarPorId(int id) {
        return alumnoDAO.buscarPorId(id);
    }

    public void guardar(AlumnoAdmin alumno) throws ReglaNegocioException {

        if (alumno == null) {
            throw new ReglaNegocioException("Datos del alumno no válidos.");
        }

        String nombre = limpiar(alumno.getNombre());
        String apellido = limpiar(alumno.getApellido());
        String correo = limpiar(alumno.getCorreo()).toLowerCase();
        String carnet = limpiar(alumno.getCarnet()).toUpperCase();
        String telefono = limpiar(alumno.getTelefono());
        String password = alumno.getPassword() == null ? "" : alumno.getPassword();

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

        if (carnet.isEmpty() || carnet.length() > 20) {
            throw new ReglaNegocioException("El carnet es obligatorio (máximo 20 caracteres).");
        }

        if (telefono.length() > 20) {
            throw new ReglaNegocioException("El teléfono admite máximo 20 caracteres.");
        }

        if (!alumnoDAO.existeCarrera(alumno.getIdCarrera())) {
            throw new ReglaNegocioException("Selecciona una carrera válida.");
        }

        if (alumnoDAO.existeCorreo(correo)) {
            throw new ReglaNegocioException("Ya existe un usuario con ese correo.");
        }

        if (alumnoDAO.existeCarnet(carnet)) {
            throw new ReglaNegocioException("Ya existe un alumno con ese carnet.");
        }

        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setCorreo(correo);
        alumno.setCarnet(carnet);
        alumno.setTelefono(telefono.isEmpty() ? null : telefono);

        if (!alumnoDAO.insertar(alumno)) {
            throw new ReglaNegocioException("No se pudo guardar el alumno. Intenta de nuevo.");
        }
    }

    public void cambiarEstado(int id) throws ReglaNegocioException {

        if (!alumnoDAO.cambiarEstado(id)) {
            throw new ReglaNegocioException("No se encontró el alumno.");
        }
    }

    public int contarTodos() {
        return alumnoDAO.contarTodos();
    }

    public int contarActivos() {
        return alumnoDAO.contarActivos();
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
