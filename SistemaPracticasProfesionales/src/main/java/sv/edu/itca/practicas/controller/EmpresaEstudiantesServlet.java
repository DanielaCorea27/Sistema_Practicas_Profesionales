package sv.edu.itca.practicas.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.service.SeguimientoService;

/**
 * Pantalla 20: Estudiantes en practica.
 * ?estado=ACTIVA (por defecto) | FINALIZADA | TODAS
 */
@WebServlet(
        name = "EmpresaEstudiantesServlet",
        urlPatterns = {"/empresa/estudiantes"}
)
public class EmpresaEstudiantesServlet extends HttpServlet {

    private final SeguimientoService service = new SeguimientoService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        String estado = request.getParameter("estado");

        if (estado == null || estado.isEmpty()) {
            estado = "ACTIVA";
        }

        // "TODAS" (o cualquier valor invalido) = sin filtro.
        request.setAttribute("asignaciones",
                service.listar(rep.getEmpresaId(), estado));

        request.setAttribute("filtro", estado);
        request.setAttribute("menuActivo", "estudiantes");

        request.getRequestDispatcher("/empresa/estudiantes.jsp")
                .forward(request, response);
    }
}
