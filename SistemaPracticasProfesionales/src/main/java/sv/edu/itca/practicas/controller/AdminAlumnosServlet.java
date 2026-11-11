package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sv.edu.itca.practicas.model.AlumnoAdmin;
import sv.edu.itca.practicas.service.AlumnoAdminService;
import sv.edu.itca.practicas.service.CarreraAdminService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

@WebServlet(
        name = "AdminAlumnosServlet",
        urlPatterns = {"/admin/alumnos"}
)
public class AdminAlumnosServlet extends HttpServlet {

    private static final String FLASH_OK = "flashOk";
    private static final String FLASH_ERROR = "flashError";

    private AlumnoAdminService service;
    private CarreraAdminService carreraService;

    @Override
    public void init() throws ServletException {
        service = new AlumnoAdminService();
        carreraService = new CarreraAdminService();
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

        request.setAttribute("alumnos", service.listarTodos());
        request.setAttribute("carreras", carreraService.listarTodos());
        request.setAttribute("totalAlumnos", service.contarTodos());
        request.setAttribute("alumnosActivos", service.contarActivos());

        request.getRequestDispatcher("/admin/alumnos.jsp")
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
            AlumnoAdmin alumno = new AlumnoAdmin();
            alumno.setNombre(request.getParameter("nombre"));
            alumno.setApellido(request.getParameter("apellido"));
            alumno.setCorreo(request.getParameter("correo"));
            alumno.setPassword(request.getParameter("password"));
            alumno.setCarnet(request.getParameter("carnet"));
            alumno.setTelefono(request.getParameter("telefono"));
            alumno.setIdCarrera(Integer.parseInt(request.getParameter("idCarrera")));

            service.guardar(alumno);
            session.setAttribute(FLASH_OK, "Alumno registrado correctamente.");

        } catch (NumberFormatException e) {
            session.setAttribute(FLASH_ERROR, "Selecciona una carrera.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/alumnos");
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
            session.setAttribute(FLASH_ERROR, "Alumno no válido.");
        } catch (ReglaNegocioException e) {
            session.setAttribute(FLASH_ERROR, e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/alumnos");
    }
}
