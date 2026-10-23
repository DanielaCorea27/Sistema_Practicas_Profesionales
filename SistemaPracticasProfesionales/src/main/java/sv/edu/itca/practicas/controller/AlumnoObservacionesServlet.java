package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.service.AlumnoPerfilService;
import sv.edu.itca.practicas.util.SesionAlumno;

/**
 * GET /alumno/observaciones -> carga las observaciones reales que la
 * empresa o el tutor dejaron al alumno y muestra /alumno/observaciones.jsp.
 * El atributo "observaciones" esta disponible para la vista (hoy
 * observaciones.jsp todavia muestra un texto fijo).
 */
@WebServlet(
        name = "AlumnoObservacionesServlet",
        urlPatterns = {"/alumno/observaciones"}
)
public class AlumnoObservacionesServlet extends HttpServlet {

    private final AlumnoPerfilService service = new AlumnoPerfilService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "observaciones",
                service.listarObservaciones(SesionAlumno.alumnoId(request))
        );

        request.getRequestDispatcher(
                "/alumno/observaciones.jsp"
        ).forward(request, response);
    }
}
