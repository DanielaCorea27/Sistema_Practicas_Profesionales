package sv.edu.itca.practicas.service;

import sv.edu.itca.practicas.dao.AlumnoPortalDAO;

/**
 * Datos de apoyo del modulo del ALUMNO (quien es el alumno de la sesion,
 * si ya tiene empresa asignada y cuantas horas debe cumplir).
 */
public class AlumnoPortalService {

    private final AlumnoPortalDAO dao = new AlumnoPortalDAO();

    /** 0 si el usuario no tiene perfil de alumno. */
    public int obtenerAlumnoId(int usuarioId) {

        if (usuarioId <= 0) {
            return 0;
        }

        return dao.buscarIdAlumno(usuarioId);
    }

    /** true si el alumno ya fue aceptado por una empresa (asignacion ACTIVA). */
    public boolean tieneAsignacionActiva(int alumnoId) {

        return alumnoId > 0 && dao.buscarAsignacionActiva(alumnoId) > 0;
    }

    /** Horas que exige su carrera (320 o 640). 0 si no se encuentra. */
    public double obtenerHorasRequeridas(int alumnoId) {

        if (alumnoId <= 0) {
            return 0;
        }

        return dao.horasRequeridas(alumnoId);
    }
}
