package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.service.RegistroHoraService;

@WebServlet(
        name = "MaestroHorasServlet",
        urlPatterns = {"/maestro/horas"}
)
public class MaestroHorasServlet extends HttpServlet {

    private RegistroHoraService service;


    @Override
    public void init() {

        service =
                new RegistroHoraService();
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        request.setAttribute(
                "registros",
                service.listarTodos()
        );

        request.setAttribute(
                "pendientes",
                service.obtenerRegistrosPendientes()
        );

        request.getRequestDispatcher(
                "/maestro/horas.jsp"
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

        String accion =
                request.getParameter("accion");

        String idParametro =
                request.getParameter("id");

        String observacion =
                request.getParameter(
                        "observacion"
                );


        int id;

        try {

            id = Integer.parseInt(
                    idParametro
            );

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/horas?error=id"
            );

            return;
        }


        boolean resultado = false;


        if ("aprobar".equals(accion)) {

            resultado =
                    service.aprobar(
                            id,
                            observacion
                    );

        } else if ("rechazar".equals(accion)) {

            resultado =
                    service.rechazar(
                            id,
                            observacion
                    );
        }


        if (resultado) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/horas?mensaje=ok"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/horas?error=accion"
            );
        }

    }

}