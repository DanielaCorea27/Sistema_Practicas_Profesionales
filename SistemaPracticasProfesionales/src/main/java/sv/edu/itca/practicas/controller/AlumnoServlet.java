package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.Alumno;
import sv.edu.itca.practicas.service.AlumnoService;

@WebServlet(
        name = "AlumnoServlet",
        urlPatterns = {"/admin/alumno"}
)
public class AlumnoServlet extends HttpServlet {

    private AlumnoService alumnoService;

    @Override
    public void init() {
        alumnoService = new AlumnoService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null || accion.trim().isEmpty()) {

            listar(request, response);

            return;
        }

        switch (accion) {

            case "nuevo":

                nuevo(request, response);

                break;

            case "editar":

                editar(request, response);

                break;

            case "eliminar":

                eliminar(request, response);

                break;

            default:

                listar(request, response);

                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {

            guardar(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/alumnos"
            );
        }
    }

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "alumnos",
                alumnoService.listarTodos()
        );

        request.getRequestDispatcher(
                "/admin/alumnos.jsp"
        ).forward(
                request,
                response
        );
    }

    private void nuevo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Alumno alumno = new Alumno();

        request.setAttribute(
                "alumno",
                alumno
        );

        request.setAttribute(
                "modo",
                "nuevo"
        );

        request.getRequestDispatcher(
                "/admin/alumno-form.jsp"
        ).forward(
                request,
                response
        );
    }

    private void editar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            Alumno alumno =
                    alumnoService.buscarPorId(id);

            if (alumno == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "El alumno no existe."
                );

                return;
            }

            request.setAttribute(
                    "alumno",
                    alumno
            );

            request.setAttribute(
                    "modo",
                    "editar"
            );

            request.getRequestDispatcher(
                    "/admin/alumno-form.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de alumno inválido."
            );
        }
    }

    private void guardar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro =
                request.getParameter("id");

        String carnet =
                request.getParameter("carnet");

        String telefono =
                request.getParameter("telefono");

        int id = 0;

        if (idParametro != null
                && !idParametro.trim().isEmpty()) {

            try {

                id = Integer.parseInt(
                        idParametro
                );

            } catch (NumberFormatException e) {

                mostrarError(
                        request,
                        response,
                        "El ID del alumno no es válido.",
                        construirAlumno(
                                id,
                                carnet,
                                telefono
                        )
                );

                return;
            }
        }

        Alumno alumno =
                construirAlumno(
                        id,
                        carnet,
                        telefono
                );

        String error =
                validar(alumno);

        if (error != null) {

            mostrarError(
                    request,
                    response,
                    error,
                    alumno
            );

            return;
        }

        boolean resultado =
                alumnoService.guardar(alumno);

        if (resultado) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/alumnos"
            );

        } else {

            mostrarError(
                    request,
                    response,
                    "No fue posible guardar el alumno. "
                    + "Verifique que el carnet no esté repetido.",
                    alumno
            );
        }
    }

    private Alumno construirAlumno(
            int id,
            String carnet,
            String telefono) {

        Alumno alumno = new Alumno();

        alumno.setId(id);

        alumno.setCarnet(
                carnet != null
                        ? carnet.trim()
                        : ""
        );

        alumno.setTelefono(
                telefono != null
                        ? telefono.trim()
                        : ""
        );

        return alumno;
    }

    private String validar(
            Alumno alumno) {

        if (alumno.getCarnet() == null
                || alumno.getCarnet().isEmpty()) {

            return "Debe ingresar el carnet del alumno.";
        }

        if (alumno.getTelefono() == null
                || alumno.getTelefono().isEmpty()) {

            return "Debe ingresar el teléfono del alumno.";
        }

        return null;
    }

    private void mostrarError(
            HttpServletRequest request,
            HttpServletResponse response,
            String mensaje,
            Alumno alumno)
            throws ServletException, IOException {

        request.setAttribute(
                "error",
                mensaje
        );

        request.setAttribute(
                "alumno",
                alumno
        );

        request.setAttribute(
                "modo",
                alumno.getId() == 0
                        ? "nuevo"
                        : "editar"
        );

        request.getRequestDispatcher(
                "/admin/alumno-form.jsp"
        ).forward(
                request,
                response
        );
    }

    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            alumnoService.eliminar(id);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/alumnos"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de alumno inválido."
            );
        }
    }
}