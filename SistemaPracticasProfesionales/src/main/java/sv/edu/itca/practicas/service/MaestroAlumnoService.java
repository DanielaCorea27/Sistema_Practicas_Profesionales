package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.MaestroAlumnoDAO;
import sv.edu.itca.practicas.model.AlumnoResumen;

public class MaestroAlumnoService {

    private final MaestroAlumnoDAO alumnoDAO;

    public MaestroAlumnoService() {
        alumnoDAO = new MaestroAlumnoDAO();
    }

    public List<AlumnoResumen> listarTodos() {
        return alumnoDAO.listarTodos();
    }

    public AlumnoResumen buscarPorId(int id) {
        return alumnoDAO.buscarPorId(id);
    }

    public int contarAlumnos() {
        return alumnoDAO.contarAlumnos();
    }

    public int contarActivos() {
        return alumnoDAO.contarActivos();
    }
}