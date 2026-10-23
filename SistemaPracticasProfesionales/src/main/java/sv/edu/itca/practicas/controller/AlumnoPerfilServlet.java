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
 * GET /alumno/perfil -> carga el perfil real del alumno y muestra
 * /alumno/perfil.jsp. El atributo "perfil" esta disponible para la vista
 * (hoy perfil.jsp todavia tiene los datos fijos).
 */
@WebServlet(
        name = "AlumnoPerfilServlet",
        urlPatterns = {"/alumno/perfil"}
)
public class AlumnoPerfilServlet extends HttpServlet {

    private final AlumnoPerfilService service = new AlumnoPerfilService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "perfil",
                service.obtenerPerfil(SesionAlumno.alumnoId(request))
        );

        request.getRequestDispatcher(
                "/alumno/perfil.jsp"
        ).forward(request, response);
    }
}
