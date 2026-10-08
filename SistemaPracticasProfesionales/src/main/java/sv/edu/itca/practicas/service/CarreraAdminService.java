package sv.edu.itca.practicas.service;

import java.util.List;
import sv.edu.itca.practicas.dao.CarreraAdminDAO;
import sv.edu.itca.practicas.model.Carrera;

public class CarreraAdminService {

    private final CarreraAdminDAO carreraDAO;

    public CarreraAdminService() {
        carreraDAO = new CarreraAdminDAO();
    }

    public List<Carrera> listarTodos() {
        return carreraDAO.listarTodos();
    }

    public Carrera buscarPorId(int id) {
        return carreraDAO.buscarPorId(id);
    }

    public boolean guardar(String nombre, double horas) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (horas <= 0) {
            return false;
        }

        Carrera carrera = new Carrera();

        carrera.setNombre(nombre.trim());
        carrera.setHorasRequeridas(horas);

        return carreraDAO.insertar(carrera);
    }

    public boolean eliminar(int id) {

        if (id <= 0) {
            return false;
        }

        return carreraDAO.eliminar(id);
    }

    public int contarTodos() {
        return carreraDAO.contarTodos();
    }
}