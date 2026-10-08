package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.MaestroPracticaDAO;
import sv.edu.itca.practicas.model.PracticaResumen;

public class MaestroPracticaService {

    private final MaestroPracticaDAO practicaDAO;

    public MaestroPracticaService() {
        practicaDAO = new MaestroPracticaDAO();
    }

    public List<PracticaResumen> listarTodos() {
        return practicaDAO.listarTodos();
    }

    public PracticaResumen buscarPorId(int id) {
        return practicaDAO.buscarPorId(id);
    }

    public int contarPracticas() {
        return practicaDAO.contarPracticas();
    }

    public int contarActivas() {
        return practicaDAO.contarActivas();
    }
}