package sv.edu.itca.practicas.service;

import java.util.ArrayList;
import java.util.List;
import sv.edu.itca.practicas.dao.AlumnoPerfilDAO;
import sv.edu.itca.practicas.model.ObservacionAsignacion;
import sv.edu.itca.practicas.model.PerfilAlumno;

public class AlumnoPerfilService {

    private final AlumnoPerfilDAO dao = new AlumnoPerfilDAO();

    /** null si el alumno no existe. */
    public PerfilAlumno obtenerPerfil(int alumnoId) {

        return alumnoId <= 0 ? null : dao.buscarPerfil(alumnoId);
    }

    public List<ObservacionAsignacion> listarObservaciones(int alumnoId) {

        return alumnoId <= 0
                ? new ArrayList<ObservacionAsignacion>()
                : dao.listarObservaciones(alumnoId);
    }
}
