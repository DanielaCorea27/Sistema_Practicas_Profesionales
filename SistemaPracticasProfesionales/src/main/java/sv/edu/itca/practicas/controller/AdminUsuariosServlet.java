package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.UsuarioAdmin;
import sv.edu.itca.practicas.service.UsuarioAdminService;

@WebServlet(
        name = "AdminUsuariosServlet",
        urlPatterns = {"/admin/usuarios"}
)
public class AdminUsuariosServlet extends HttpServlet {

    private UsuarioAdminService service;

    @Override
    public void init() throws ServletException {
        service = new UsuarioAdminService();
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
                "usuarios",
                service.listarTodos()
        );

        request.setAttribute(
                "totalUsuarios",
                service.contarTodos()
        );

        request.setAttribute(
                "usuariosActivos",
                service.contarActivos()
        );

        request.getRequestDispatcher(
                "/admin/usuarios.jsp"
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

        String rol =
                request.getParameter("rol");

        UsuarioAdmin nuevo =
                new UsuarioAdmin();

        nuevo.setNombre(nombre);
        nuevo.setUsuario(usuario);
        nuevo.setRol(rol);

        service.guardar(nuevo);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/usuarios"
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
                + "/admin/usuarios"
        );
    }
}