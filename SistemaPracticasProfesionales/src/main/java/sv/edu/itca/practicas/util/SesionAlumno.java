package sv.edu.itca.practicas.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.AlumnoPortalService;

/**
 * Atajos para saber quien es el alumno de la sesion actual.
 * Reemplaza a las constantes ALUMNO_PRUEBA = 1.
 */
public final class SesionAlumno {

    private static final AlumnoPortalService PORTAL = new AlumnoPortalService();

    private SesionAlumno() {
    }

    /** Usuario guardado en sesion por el LoginServlet (null si no hay). */
    public static Usuario usuario(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        return session == null
                ? null
                : (Usuario) session.getAttribute("usuario");
    }

    /** alumnos.id_alumno del usuario logueado (0 si no tiene perfil de alumno). */
    public static int alumnoId(HttpServletRequest request) {

        Usuario usuario = usuario(request);

        return usuario == null ? 0 : PORTAL.obtenerAlumnoId(usuario.getId());
    }
}
