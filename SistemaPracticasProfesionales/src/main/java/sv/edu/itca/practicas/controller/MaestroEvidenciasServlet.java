package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.service.EvidenciaService;

@WebServlet(
        name = "MaestroEvidenciasServlet",
        urlPatterns = {"/maestro/evidencias"}
)
public class MaestroEvidenciasServlet extends HttpServlet {

    private EvidenciaService service;

    @Override
    public void init() throws ServletException {
        service = new EvidenciaService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "evidencias",
                service.listarTodos()
        );

        request.setAttribute(
                "pendientes",
                service.contarPendientes()
        );

        request.setAttribute(
                "aprobadas",
                service.contarAprobadas()
        );

        request.getRequestDispatcher(
                "/maestro/evidencias.jsp"
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

        int id;

        try {

            id = Integer.parseInt(
                    request.getParameter("id")
            );

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/evidencias"
            );

            return;
        }

        if ("aprobar".equals(accion)) {

            service.aprobar(id);

        } else if ("rechazar".equals(accion)) {

            service.rechazar(id);
        }

        response.sendRedirect(
                request.getContextPath()
                + "/maestro/evidencias"
        );
    }
}