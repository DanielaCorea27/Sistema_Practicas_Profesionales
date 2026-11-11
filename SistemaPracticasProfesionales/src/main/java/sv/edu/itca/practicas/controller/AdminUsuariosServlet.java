package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.model.UsuarioAdmin;
import sv.edu.itca.practicas.service.UsuarioAdminService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

@WebServlet(
        name = "AdminUsuariosServlet",
        urlPatterns = {"/admin/usuarios"}
)
public class AdminUsuariosServlet extends HttpServlet {

    private static final String FLASH_OK = "flashOk";
    private static final String FLASH_ERROR = "flashError";

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

        if ("cambiarEstado".equals(request.getParameter("accion"))) {
            cambiarEstado(request, response);
            return;
        }

        HttpSession session = request.getSession();
        request.setAttribute("mensajeOk", session.getAttribute(FLASH_OK));
        request.setAttribute("mensajeError", session.getAttribute(FLASH_ERROR));
        session.removeAttribute(FLASH_OK);
        session.removeAttribute(FLASH_ERROR);

        request.setAttribute("usuarios", service.listarTodos());
        request.setAttribute("totalUsuarios", service.contarTodos());
        request.setAttribute("usuariosActivos", service.contarActivos());
        request.setAttribute("idUsuarioSesion", idUsuarioSesion(request));

        request.getRequestDispatcher("/admin/usuarios.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        UsuarioAdmin nuevo = new UsuarioAdmin();
        nuevo.setNombre(request.getParameter("nombre"));
        nuevo.setApellido(request.getParameter("apellido"));
        nuevo.setCorreo(request.getParameter("correo"));
        nuevo.setPassword(request.getParameter("password"));

        HttpSession session = request.getSession();

        try {
            service.guardarAdministrador(nuevo);
            session.setAttribute(FLASH_OK, "Administrador creado correctamente.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/usuarios");
    }

    private void cambiarEstado(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            service.cambiarEstado(id, idUsuarioSesion(request));
            session.setAttribute(FLASH_OK, "Estado actualizado.");
        } catch (NumberFormatException e) {
            session.setAttribute(FLASH_ERROR, "Usuario no válido.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/usuarios");
    }

    private int idUsuarioSesion(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return -1;
        }

        Object usuario = session.getAttribute("usuario");

        return usuario instanceof Usuario ? ((Usuario) usuario).getId() : -1;
    }
}