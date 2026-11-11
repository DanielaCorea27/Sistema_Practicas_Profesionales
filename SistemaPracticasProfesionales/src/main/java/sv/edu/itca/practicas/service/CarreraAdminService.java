package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.CarreraAdminDAO;
import sv.edu.itca.practicas.model.Carrera;
import sv.edu.itca.practicas.model.TipoCarrera;
import sv.edu.itca.practicas.util.ReglaNegocioException;

public class CarreraAdminService {

    private final CarreraAdminDAO carreraDAO;

    public CarreraAdminService() {
        carreraDAO = new CarreraAdminDAO();
    }

    public List<Carrera> listarTodos() {
        return carreraDAO.listarTodos();
    }

    public List<TipoCarrera> listarTipos() {
        return carreraDAO.listarTipos();
    }

    public Carrera buscarPorId(int id) {
        return carreraDAO.buscarPorId(id);
    }

    public void guardar(String nombre, int idTipoCarrera) throws ReglaNegocioException {

        String limpio = nombre == null ? "" : nombre.trim();

        if (limpio.isEmpty()) {
            throw new ReglaNegocioException("El nombre de la carrera es obligatorio.");
        }

        if (limpio.length() > 150) {
            throw new ReglaNegocioException("El nombre admite máximo 150 caracteres.");
        }

        if (!carreraDAO.existeTipo(idTipoCarrera)) {
            throw new ReglaNegocioException("Selecciona un tipo de carrera válido.");
        }

        if (carreraDAO.existeNombre(limpio)) {
            throw new ReglaNegocioException("Ya existe una carrera con ese nombre.");
        }

        Carrera carrera = new Carrera();
        carrera.setNombre(limpio);
        carrera.setIdTipoCarrera(idTipoCarrera);

        if (!carreraDAO.insertar(carrera)) {
            throw new ReglaNegocioException("No se pudo guardar la carrera. Intenta de nuevo.");
        }
    }

    public void eliminar(int id) throws ReglaNegocioException {

        int alumnos = carreraDAO.contarAlumnos(id);

        if (alumnos > 0) {
            throw new ReglaNegocioException(
                    "No se puede eliminar: la carrera tiene " + alumnos + " alumno(s) inscrito(s).");
        }

        if (!carreraDAO.eliminar(id)) {
            throw new ReglaNegocioException("No se encontró la carrera.");
        }
    }

    public int contarTodos() {
        return carreraDAO.contarTodos();
    }
}
