package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sv.edu.itca.practicas.model.EmpresaAdmin;
import sv.edu.itca.practicas.service.EmpresaAdminService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

@WebServlet(
        name = "AdminEmpresasServlet",
        urlPatterns = {"/admin/empresas"}
)
public class AdminEmpresasServlet extends HttpServlet {

    private static final String FLASH_OK = "flashOk";
    private static final String FLASH_ERROR = "flashError";

    private EmpresaAdminService service;

    @Override
    public void init() throws ServletException {
        service = new EmpresaAdminService();
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

        request.setAttribute("empresas", service.listarTodos());
        request.setAttribute("totalEmpresas", service.contarTodos());
        request.setAttribute("empresasActivas", service.contarActivas());

        request.getRequestDispatcher("/admin/empresas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        EmpresaAdmin empresa = new EmpresaAdmin();
        empresa.setNombre(request.getParameter("nombre"));
        empresa.setDescripcion(request.getParameter("descripcion"));
        empresa.setDireccion(request.getParameter("direccion"));
        empresa.setTelefono(request.getParameter("telefono"));
        empresa.setCorreo(request.getParameter("correo"));
        empresa.setSitioWeb(request.getParameter("sitioWeb"));
        empresa.setContacto(request.getParameter("contacto"));

        HttpSession session = request.getSession();

        try {
            service.guardar(empresa);
            session.setAttribute(FLASH_OK, "Empresa registrada correctamente.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/empresas");
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
            session.setAttribute(FLASH_ERROR, "Empresa no válida.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/empresas");
    }
}
