package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.AlumnoPortalService;
import sv.edu.itca.practicas.service.RegistroHoraService;
import sv.edu.itca.practicas.util.SesionAlumno;

@WebServlet(
        name = "AlumnoDashboardServlet",
        urlPatterns = {"/alumno/dashboard"}
)
public class AlumnoDashboardServlet extends HttpServlet {

    private final RegistroHoraService registroHoraService =
            new RegistroHoraService();

    private final AlumnoPortalService portal =
            new AlumnoPortalService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );
            return;
        }

        Usuario usuario =
                (Usuario) session.getAttribute(
                        "usuario"
                );

        if (usuario == null) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );
            return;
        }

        // Alumno real de la sesion (antes era alumnoId = 1).
        int alumnoId =
                SesionAlumno.alumnoId(request);

        double horasAprobadas =
                registroHoraService
                        .obtenerHorasAprobadas(
                                alumnoId
                        );

        double horasTotales =
                registroHoraService
                        .obtenerTotalHoras(
                                alumnoId
                        );

        request.setAttribute(
                "horasAprobadas",
                horasAprobadas
        );

        request.setAttribute(
                "horasTotales",
                horasTotales
        );

        // Horas reales de la carrera (320 / 640). El dashboard.jsp actual
        // todavia las tiene fijas en 640; este atributo queda listo para
        // cuando el equipo actualice la vista.
        request.setAttribute(
                "horasRequeridas",
                portal.obtenerHorasRequeridas(alumnoId)
        );

        request.getRequestDispatcher(
                "/alumno/dashboard.jsp"
        ).forward(
                request,
                response
        );
    }
}
