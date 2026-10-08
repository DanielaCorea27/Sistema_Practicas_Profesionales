package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.service.CarreraAdminService;

@WebServlet(
        name = "AdminCarrerasServlet",
        urlPatterns = {"/admin/carreras"}
)
public class AdminCarrerasServlet extends HttpServlet {

    private CarreraAdminService service;

    @Override
    public void init() throws ServletException {

        service = new CarreraAdminService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");

        if ("eliminar".equals(accion)) {

            try {

                int id =
                        Integer.parseInt(
                                request.getParameter("id")
                        );

                service.eliminar(id);

            } catch (Exception e) {
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/carreras"
            );

            return;
        }

        request.setAttribute(
                "carreras",
                service.listarTodos()
        );

        request.setAttribute(
                "totalCarreras",
                service.contarTodos()
        );

        request.getRequestDispatcher(
                "/admin/carreras.jsp"
        ).forward(
                request,
                response
        );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nombre =
                request.getParameter("nombre");

        String horasParametro =
                request.getParameter("horas");

        try {

            double horas =
                    Double.parseDouble(horasParametro);

            service.guardar(
                    nombre,
                    horas
            );

        } catch (Exception e) {
        }

        response.sendRedirect(
                request.getContextPath()
                + "/admin/carreras"
        );
    }
}