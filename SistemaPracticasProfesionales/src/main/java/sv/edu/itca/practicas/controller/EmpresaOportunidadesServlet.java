package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.service.OportunidadService;

/**
 * Pantalla 17: Mis oportunidades (listar y cerrar).
 */
@WebServlet(
        name = "EmpresaOportunidadesServlet",
        urlPatterns = {"/empresa/oportunidades"}
)
public class EmpresaOportunidadesServlet extends HttpServlet {

    private final OportunidadService service = new OportunidadService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        request.setAttribute("oportunidades",
                service.listarPorEmpresa(rep.getEmpresaId()));

        request.setAttribute("menuActivo", "oportunidades");

        request.getRequestDispatcher("/empresa/oportunidades.jsp")
                .forward(request, response);
    }

    /** Cerrar una oportunidad. */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        String accion = request.getParameter("accion");

        if ("cerrar".equals(accion)) {

            try {

                int id = Integer.parseInt(request.getParameter("id"));

                service.cerrar(id, rep.getEmpresaId());

            } catch (NumberFormatException e) {
                // id invalido: se ignora
            }
        }

        response.sendRedirect(
                request.getContextPath() + "/empresa/oportunidades?ok=cerrada");
    }
}
