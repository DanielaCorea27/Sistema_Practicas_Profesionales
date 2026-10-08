package sv.edu.itca.practicas.controller;

/**
 *
 * @author danie
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.model.AsignacionResumen;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.SeguimientoService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

@WebServlet(
        name = "EmpresaSeguimientoServlet",
        urlPatterns = {"/empresa/seguimiento"}
)
public class EmpresaSeguimientoServlet extends HttpServlet {

    private final SeguimientoService service = new SeguimientoService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        AsignacionResumen asignacion = null;

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            asignacion = service.obtener(id, rep.getEmpresaId());

        } catch (NumberFormatException e) {
            // id ausente o invalido
        }

        if (asignacion == null) {

            response.sendRedirect(
                    request.getContextPath() + "/empresa/estudiantes");

            return;
        }

        request.setAttribute("asignacion", asignacion);
        request.setAttribute("actividades", service.actividades(asignacion.getId()));
        request.setAttribute("horarios", service.horarios(asignacion.getId()));
        request.setAttribute("observaciones", service.observaciones(asignacion.getId()));
        request.setAttribute("menuActivo", "estudiantes");

        request.getRequestDispatcher("/empresa/seguimiento.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        Representante rep = (Representante) session.getAttribute("representante");
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String id = request.getParameter("id");

        try {

            service.agregarObservacion(
                    Integer.parseInt(id),
                    rep.getEmpresaId(),
                    usuario.getId(),
                    request.getParameter("texto"));

            session.setAttribute("flashTipo", "success");
            session.setAttribute("flashMensaje", "Observación guardada.");

        } catch (ReglaNegocioException e) {

            session.setAttribute("flashTipo", "danger");
            session.setAttribute("flashMensaje", e.getMessage());

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/empresa/estudiantes");

            return;
        }

        response.sendRedirect(
                request.getContextPath() + "/empresa/seguimiento?id=" + id);
    }
}
