package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.service.MaestroAlumnoService;

@WebServlet(
        name = "MaestroAlumnosServlet",
        urlPatterns = {"/maestro/alumnos"}
)
public class MaestroAlumnosServlet extends HttpServlet {

    private MaestroAlumnoService service;

    @Override
    public void init() throws ServletException {
        service = new MaestroAlumnoService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        request.setAttribute(
                "alumnos",
                service.listarTodos()
        );

        request.setAttribute(
                "totalAlumnos",
                service.contarAlumnos()
        );

        request.setAttribute(
                "alumnosActivos",
                service.contarActivos()
        );

        request.getRequestDispatcher(
                "/maestro/alumnos.jsp"
        ).forward(
                request,
                response
        );
    }
}