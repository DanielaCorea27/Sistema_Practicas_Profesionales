package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.RegistroHoraService;

@WebServlet(
        name = "AlumnoDashboardServlet",
        urlPatterns = {"/alumno/dashboard"}
)
public class AlumnoDashboardServlet extends HttpServlet {

    private final RegistroHoraService registroHoraService =
            new RegistroHoraService();

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

        int alumnoId = 1;

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

        request.getRequestDispatcher(
                "/alumno/dashboard.jsp"
        ).forward(
                request,
                response
        );
    }
}