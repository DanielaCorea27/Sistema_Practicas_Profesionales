package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.service.MaestroPracticaService;

@WebServlet(
        name = "MaestroPracticasServlet",
        urlPatterns = {"/maestro/practicas"}
)
public class MaestroPracticasServlet extends HttpServlet {

    private MaestroPracticaService service;

    @Override
    public void init() throws ServletException {
        service = new MaestroPracticaService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        request.setAttribute(
                "practicas",
                service.listarTodos()
        );

        request.setAttribute(
                "totalPracticas",
                service.contarPracticas()
        );

        request.setAttribute(
                "practicasActivas",
                service.contarActivas()
        );

        request.getRequestDispatcher(
                "/maestro/practicas.jsp"
        ).forward(
                request,
                response
        );
    }
}