package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.AlumnoAdmin;
import sv.edu.itca.practicas.service.AlumnoAdminService;

@WebServlet(
        name = "AdminAlumnosServlet",
        urlPatterns = {"/admin/alumnos"}
)
public class AdminAlumnosServlet extends HttpServlet {

    private AlumnoAdminService service;

    @Override
    public void init() throws ServletException {
        service = new AlumnoAdminService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("cambiarEstado".equals(accion)) {

            cambiarEstado(request, response);

            return;
        }

        request.setAttribute(
                "alumnos",
                service.listarTodos()
        );

        request.setAttribute(
                "totalAlumnos",
                service.contarTodos()
        );

        request.setAttribute(
                "alumnosActivos",
                service.contarActivos()
        );

        request.getRequestDispatcher(
                "/admin/alumnos.jsp"
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

        AlumnoAdmin alumno = new AlumnoAdmin();

        alumno.setNombre(
                request.getParameter("nombre")
        );

        alumno.setCarnet(
                request.getParameter("carnet")
        );

        alumno.setCarrera(
                request.getParameter("carrera")
        );

        alumno.setUsuario(
                request.getParameter("usuario")
        );

        service.guardar(alumno);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/alumnos"
        );
    }

    private void cambiarEstado(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            service.cambiarEstado(id);

        } catch (Exception e) {
            // Se mantiene el comportamiento original.
        }

        response.sendRedirect(
                request.getContextPath()
                + "/admin/alumnos"
        );
    }
}