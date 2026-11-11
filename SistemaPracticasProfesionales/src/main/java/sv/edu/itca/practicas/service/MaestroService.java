package sv.edu.itca.practicas.service;

import sv.edu.itca.practicas.dao.MaestroDAO;

public class MaestroService {

    private final MaestroDAO maestroDAO;

    public MaestroService() {
        maestroDAO = new MaestroDAO();
    }

    public int obtenerIdMaestro(int idUsuario) {
        return maestroDAO.obtenerIdMaestro(idUsuario);
    }
}