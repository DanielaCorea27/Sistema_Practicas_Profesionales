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
        name = "MaestroDashboardServlet",
        urlPatterns = {"/maestro/dashboard"}
)
public class MaestroDashboardServlet extends HttpServlet {

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

        request.setAttribute(
                "totalRegistros",
                registroHoraService.contarRegistros()
        );

        request.setAttribute(
                "pendientes",
                registroHoraService.contarPendientes()
        );

        request.setAttribute(
                "aprobados",
                registroHoraService.contarAprobados()
        );

        request.setAttribute(
                "rechazados",
                registroHoraService.contarRechazados()
        );

        request.setAttribute(
                "horasTotales",
                registroHoraService
                        .obtenerHorasTotalesSistema()
        );

        request.setAttribute(
                "horasAprobadas",
                registroHoraService
                        .obtenerHorasAprobadasSistema()
        );

        request.setAttribute(
                "horasPendientes",
                registroHoraService
                        .obtenerHorasPendientesSistema()
        );

        request.getRequestDispatcher(
                "/maestro/dashboard.jsp"
        ).forward(
                request,
                response
        );
    }
}