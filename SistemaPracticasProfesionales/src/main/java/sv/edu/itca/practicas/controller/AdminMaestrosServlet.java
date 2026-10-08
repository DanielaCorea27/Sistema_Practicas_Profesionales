package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.MaestroAdmin;
import sv.edu.itca.practicas.service.MaestroAdminService;

@WebServlet(
        name = "AdminMaestrosServlet",
        urlPatterns = {"/admin/maestros"}
)
public class AdminMaestrosServlet extends HttpServlet {

    private MaestroAdminService service;

    @Override
    public void init() throws ServletException {

        service = new MaestroAdminService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");

        if ("cambiarEstado".equals(accion)) {

            cambiarEstado(request, response);

            return;
        }

        request.setAttribute(
                "maestros",
                service.listarTodos()
        );

        request.setAttribute(
                "totalMaestros",
                service.contarTodos()
        );

        request.setAttribute(
                "maestrosActivos",
                service.contarActivos()
        );

        request.getRequestDispatcher(
                "/admin/maestros.jsp"
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

        String usuario =
                request.getParameter("usuario");

        String especialidad =
                request.getParameter("especialidad");

        MaestroAdmin maestro =
                new MaestroAdmin();

        maestro.setNombre(nombre);
        maestro.setUsuario(usuario);
        maestro.setEspecialidad(especialidad);

        service.guardar(maestro);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/maestros"
        );
    }

    private void cambiarEstado(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            service.cambiarEstado(id);

        } catch (Exception e) {
        }

        response.sendRedirect(
                request.getContextPath()
                + "/admin/maestros"
        );
    }
}