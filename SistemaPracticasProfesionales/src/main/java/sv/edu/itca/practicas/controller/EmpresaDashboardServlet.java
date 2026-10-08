package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.service.EmpresaPanelService;

@WebServlet(
        name = "EmpresaDashboardServlet",
        urlPatterns = {"/empresa/dashboard"}
)
public class EmpresaDashboardServlet extends HttpServlet {

    private final EmpresaPanelService service = new EmpresaPanelService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // EmpresaFilter ya garantiza que existe en la sesion.
        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        int empresaId = rep.getEmpresaId();

        request.setAttribute("totalOportunidades",
                service.totalOportunidades(empresaId));

        request.setAttribute("postulacionesPendientes",
                service.postulacionesPendientes(empresaId));

        request.setAttribute("estudiantesActivos",
                service.estudiantesActivos(empresaId));

        request.setAttribute("evaluacionesPendientes",
                service.evaluacionesPendientes(empresaId));

        request.setAttribute("menuActivo", "dashboard");

        request.getRequestDispatcher("/empresa/dashboard.jsp")
                .forward(request, response);
    }
}
