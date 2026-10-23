package sv.edu.itca.practicas.service;

import java.util.List;

import sv.edu.itca.practicas.dao.PracticaInfoDAO;
import sv.edu.itca.practicas.model.PracticaInfo;

public class PracticaInfoService {

    private final PracticaInfoDAO dao;

    public PracticaInfoService() {

        dao = new PracticaInfoDAO();
    }

    public PracticaInfo buscarPorAlumno(int alumnoId) {

        if (alumnoId <= 0) {

            return null;
        }

        return dao.buscarPorAlumno(alumnoId);
    }

    public List<PracticaInfo> listar() {

        return dao.listar();
    }
}
