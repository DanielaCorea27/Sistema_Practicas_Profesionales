package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sv.edu.itca.practicas.model.MaestroAdmin;
import sv.edu.itca.practicas.service.MaestroAdminService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

@WebServlet(
        name = "AdminMaestrosServlet",
        urlPatterns = {"/admin/maestros"}
)
public class AdminMaestrosServlet extends HttpServlet {

    private static final String FLASH_OK = "flashOk";
    private static final String FLASH_ERROR = "flashError";

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

        if ("cambiarEstado".equals(request.getParameter("accion"))) {
            cambiarEstado(request, response);
            return;
        }

        HttpSession session = request.getSession();
        request.setAttribute("mensajeOk", session.getAttribute(FLASH_OK));
        request.setAttribute("mensajeError", session.getAttribute(FLASH_ERROR));
        session.removeAttribute(FLASH_OK);
        session.removeAttribute(FLASH_ERROR);

        request.setAttribute("maestros", service.listarTodos());
        request.setAttribute("totalMaestros", service.contarTodos());
        request.setAttribute("maestrosActivos", service.contarActivos());

        request.getRequestDispatcher("/admin/maestros.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        MaestroAdmin maestro = new MaestroAdmin();
        maestro.setNombre(request.getParameter("nombre"));
        maestro.setApellido(request.getParameter("apellido"));
        maestro.setCorreo(request.getParameter("correo"));
        maestro.setPassword(request.getParameter("password"));
        maestro.setCodigoEmpleado(request.getParameter("codigoEmpleado"));
        maestro.setEspecialidad(request.getParameter("especialidad"));
        maestro.setTelefono(request.getParameter("telefono"));

        HttpSession session = request.getSession();

        try {
            service.guardar(maestro);
            session.setAttribute(FLASH_OK, "Maestro registrado correctamente.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/maestros");
    }

    private void cambiarEstado(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            service.cambiarEstado(id);
            session.setAttribute(FLASH_OK, "Estado actualizado.");
        } catch (NumberFormatException e) {
            session.setAttribute(FLASH_ERROR, "Maestro no válido.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/maestros");
    }
}
