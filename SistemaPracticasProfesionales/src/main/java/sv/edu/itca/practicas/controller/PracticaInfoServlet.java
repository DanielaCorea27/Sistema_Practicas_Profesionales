package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.PracticaInfo;
import sv.edu.itca.practicas.service.PracticaInfoService;

@WebServlet(
        name = "PracticaInfoServlet",
        urlPatterns = {"/alumno/practica"}
)
public class PracticaInfoServlet extends HttpServlet {

    private PracticaInfoService service;

    private static final int ALUMNO_PRUEBA = 1;

    @Override
    public void init() {

        service = new PracticaInfoService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        PracticaInfo practica =
                service.buscarPorAlumno(ALUMNO_PRUEBA);

        if (practica == null) {

            request.setAttribute(
                    "mensaje",
                    "No se encontró información de la práctica."
            );
        }

        request.setAttribute(
                "practica",
                practica
        );

        request.getRequestDispatcher(
                "/alumno/practica.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}