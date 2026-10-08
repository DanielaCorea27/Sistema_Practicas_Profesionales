package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.service.PostulacionService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Pantalla 19: Postulantes (listar, aceptar y rechazar).
 */
@WebServlet(
        name = "EmpresaPostulantesServlet",
        urlPatterns = {"/empresa/postulantes"}
)
public class EmpresaPostulantesServlet extends HttpServlet {

    private final PostulacionService service = new PostulacionService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        String estado = request.getParameter("estado");

        request.setAttribute("postulantes",
                service.listar(rep.getEmpresaId(), estado));

        request.setAttribute("filtro", estado == null ? "" : estado);
        request.setAttribute("menuActivo", "postulantes");

        request.getRequestDispatcher("/empresa/postulantes.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Representante rep = (Representante) session.getAttribute("representante");

        String accion = request.getParameter("accion");

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            if ("aceptar".equals(accion)) {

                int horas = service.aceptar(
                        id, rep.getEmpresaId(), rep.getId());

                mensaje(session, "success",
                        "Estudiante aceptado. Se creó su asignación en tu "
                        + "empresa con " + horas + " horas planificadas.");

            } else if ("rechazar".equals(accion)) {

                service.rechazar(id, rep.getEmpresaId());

                mensaje(session, "success", "Postulación rechazada.");

            } else {

                mensaje(session, "warning", "Acción no válida.");
            }

        } catch (ReglaNegocioException e) {

            mensaje(session, "danger", e.getMessage());

        } catch (NumberFormatException e) {

            mensaje(session, "warning", "Postulación no válida.");
        }

        response.sendRedirect(
                request.getContextPath() + "/empresa/postulantes");
    }

    /** Mensaje que se muestra una sola vez despues de redirigir. */
    private void mensaje(HttpSession session, String tipo, String texto) {

        session.setAttribute("flashTipo", tipo);
        session.setAttribute("flashMensaje", texto);
    }
}
