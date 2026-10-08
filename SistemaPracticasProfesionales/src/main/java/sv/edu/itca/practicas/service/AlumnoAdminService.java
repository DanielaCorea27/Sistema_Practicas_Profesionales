package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.AlumnoAdminDAO;
import sv.edu.itca.practicas.model.AlumnoAdmin;

public class AlumnoAdminService {

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

    public boolean guardar(AlumnoAdmin alumno) {

        if (alumno == null) {
            return false;
        }

        if (alumno.getNombre() == null
                || alumno.getNombre().trim().isEmpty()) {
            return false;
        }

        if (alumno.getCarnet() == null
                || alumno.getCarnet().trim().isEmpty()) {
            return false;
        }

        if (alumno.getCarrera() == null
                || alumno.getCarrera().trim().isEmpty()) {
            return false;
        }

        if (alumno.getUsuario() == null
                || alumno.getUsuario().trim().isEmpty()) {
            return false;
        }

        alumno.setNombre(
                alumno.getNombre().trim()
        );

        alumno.setCarnet(
                alumno.getCarnet().trim()
        );

        alumno.setCarrera(
                alumno.getCarrera().trim()
        );

        alumno.setUsuario(
                alumno.getUsuario().trim()
        );

        alumno.setEstado("ACTIVO");

        return alumnoDAO.insertar(alumno);
    }

    public boolean cambiarEstado(int id) {

        return alumnoDAO.cambiarEstado(id);
    }

    public int contarTodos() {

        return alumnoDAO.contarTodos();
    }

    public int contarActivos() {

        return alumnoDAO.contarActivos();
    }
}