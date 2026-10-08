package sv.edu.itca.practicas.service;

import java.util.List;
import sv.edu.itca.practicas.dao.RegistroHoraDAO;
import sv.edu.itca.practicas.model.RegistroHora;

public class RegistroHoraService {

    private final RegistroHoraDAO registroHoraDAO;

    public RegistroHoraService() {
        registroHoraDAO = new RegistroHoraDAO();
    }

    public List<RegistroHora> listarPorAlumno(int alumnoId) {
        return registroHoraDAO.listarPorAlumno(alumnoId);
    }

    public List<RegistroHora> listarTodos() {
        return registroHoraDAO.listarTodos();
    }

    public RegistroHora buscarPorId(int id) {
        return registroHoraDAO.buscarPorId(id);
    }

    public boolean guardar(RegistroHora registro) {

        if (registro == null) {
            return false;
        }

        if (registro.getAlumnoId() <= 0) {
            return false;
        }

        if (registro.getFecha() == null) {
            return false;
        }

        if (registro.getHoras() <= 0 || registro.getHoras() > 24) {
            return false;
        }

        if (registro.getActividad() == null
                || registro.getActividad().trim().isEmpty()) {
            return false;
        }

        registro.setActividad(
                registro.getActividad().trim()
        );

        if (registro.getEstado() == null
                || registro.getEstado().trim().isEmpty()) {
            registro.setEstado("PENDIENTE");
        }

        if (registro.getId() == 0) {
            return registroHoraDAO.insertar(registro);
        }

        return registroHoraDAO.actualizar(registro);
    }

    public boolean eliminar(int id) {
        return registroHoraDAO.eliminar(id);
    }

    public boolean aprobar(int id, String observacion) {

        if (id <= 0) {
            return false;
        }

        return registroHoraDAO.aprobar(
                id,
                observacion
        );
    }

    public boolean rechazar(int id, String observacion) {

        if (id <= 0) {
            return false;
        }

        return registroHoraDAO.rechazar(
                id,
                observacion
        );
    }

    public double obtenerTotalHoras(int alumnoId) {
        return registroHoraDAO.obtenerTotalHoras(alumnoId);
    }

    public double obtenerHorasAprobadas(int alumnoId) {
        return registroHoraDAO.obtenerHorasAprobadas(alumnoId);
    }

    public double obtenerHorasRestantes(
            int alumnoId,
            double horasRequeridas) {

        double aprobadas =
                obtenerHorasAprobadas(alumnoId);

        return Math.max(
                horasRequeridas - aprobadas,
                0
        );
    }

    public double obtenerPorcentaje(
            int alumnoId,
            double horasRequeridas) {

        if (horasRequeridas <= 0) {
            return 0;
        }

        double aprobadas =
                obtenerHorasAprobadas(alumnoId);

        double porcentaje =
                (aprobadas / horasRequeridas) * 100;

        return Math.min(porcentaje, 100);
    }

    public int obtenerRegistrosPendientes() {
        return registroHoraDAO.obtenerRegistrosPendientes();
    }

    public int contarRegistros() {
        return registroHoraDAO.contarRegistros();
    }

    public int contarPendientes() {
        return registroHoraDAO.contarPendientes();
    }

    public int contarAprobados() {
        return registroHoraDAO.contarAprobados();
    }

    public int contarRechazados() {
        return registroHoraDAO.contarRechazados();
    }

    public double obtenerHorasTotalesSistema() {
        return registroHoraDAO.obtenerHorasTotalesSistema();
    }

    public double obtenerHorasAprobadasSistema() {
        return registroHoraDAO.obtenerHorasAprobadasSistema();
    }

    public double obtenerHorasPendientesSistema() {
        return registroHoraDAO.obtenerHorasPendientesSistema();
    }
}