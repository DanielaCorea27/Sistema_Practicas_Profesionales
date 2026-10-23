package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.PracticaInfo;
import sv.edu.itca.practicas.service.PracticaInfoService;
import sv.edu.itca.practicas.util.SesionAlumno;

@WebServlet(
        name = "PracticaInfoServlet",
        urlPatterns = {"/alumno/practica"}
)
public class PracticaInfoServlet extends HttpServlet {

    private PracticaInfoService service;

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

        // Practica del alumno que inicio sesion (antes era el alumno 1 fijo).
        PracticaInfo practica =
                service.buscarPorAlumno(
                        SesionAlumno.alumnoId(request)
                );

        if (practica == null) {

            request.setAttribute(
                    "mensaje",
                    "No se encontró información de la práctica. "
                    + "Aparecerá cuando una empresa acepte tu postulación."
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
