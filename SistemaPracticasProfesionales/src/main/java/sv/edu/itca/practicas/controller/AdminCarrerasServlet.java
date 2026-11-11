package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sv.edu.itca.practicas.service.CarreraAdminService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

@WebServlet(
        name = "AdminCarrerasServlet",
        urlPatterns = {"/admin/carreras"}
)
public class AdminCarrerasServlet extends HttpServlet {

    private static final String FLASH_OK = "flashOk";
    private static final String FLASH_ERROR = "flashError";

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

        if ("eliminar".equals(request.getParameter("accion"))) {
            eliminar(request, response);
            return;
        }

        HttpSession session = request.getSession();
        request.setAttribute("mensajeOk", session.getAttribute(FLASH_OK));
        request.setAttribute("mensajeError", session.getAttribute(FLASH_ERROR));
        session.removeAttribute(FLASH_OK);
        session.removeAttribute(FLASH_ERROR);

        request.setAttribute("carreras", service.listarTodos());
        request.setAttribute("tipos", service.listarTipos());
        request.setAttribute("totalCarreras", service.contarTodos());

        request.getRequestDispatcher("/admin/carreras.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        try {
            int idTipo = Integer.parseInt(request.getParameter("idTipoCarrera"));
            service.guardar(request.getParameter("nombre"), idTipo);
            session.setAttribute(FLASH_OK, "Carrera registrada correctamente.");
        } catch (NumberFormatException e) {
            session.setAttribute(FLASH_ERROR, "Selecciona un tipo de carrera.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/carreras");
    }

    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            service.eliminar(id);
            session.setAttribute(FLASH_OK, "Carrera eliminada.");
        } catch (NumberFormatException e) {
            session.setAttribute(FLASH_ERROR, "Carrera no válida.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/carreras");
    }
}
