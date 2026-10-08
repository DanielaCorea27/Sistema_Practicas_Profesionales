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

    public boolean guardar(PracticaInfo practica) {

        if (practica == null) {

            return false;
        }

        if (practica.getAlumnoId() <= 0) {

            return false;
        }

        if (practica.getAlumno() == null
                || practica.getAlumno().trim().isEmpty()) {

            return false;
        }

        if (practica.getCarrera() == null
                || practica.getCarrera().trim().isEmpty()) {

            return false;
        }

        if (practica.getHorasRequeridas() <= 0) {

            return false;
        }

        if (practica.getEmpresa() == null
                || practica.getEmpresa().trim().isEmpty()) {

            return false;
        }

        return dao.actualizar(practica);
    }
}