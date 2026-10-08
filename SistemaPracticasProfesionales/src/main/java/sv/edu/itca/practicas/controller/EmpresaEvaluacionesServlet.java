package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.service.EvaluacionService;

/**
 * Listado de estudiantes pendientes y ya evaluados.
 */
@WebServlet(
        name = "EmpresaEvaluacionesServlet",
        urlPatterns = {"/empresa/evaluaciones"}
)
public class EmpresaEvaluacionesServlet extends HttpServlet {

    private final EvaluacionService service = new EvaluacionService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        request.setAttribute("evaluables", service.listar(rep.getEmpresaId()));
        request.setAttribute("menuActivo", "evaluaciones");

        request.getRequestDispatcher("/empresa/evaluaciones.jsp")
                .forward(request, response);
    }
}
