package sv.edu.itca.practicas.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.EvidenciaResumen;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.EvidenciaService;
import sv.edu.itca.practicas.util.SesionAlumno;

@WebServlet(
        name = "AlumnoEvidenciasServlet",
        urlPatterns = {"/alumno/evidencias"}
)
public class AlumnoEvidenciasServlet extends HttpServlet {

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

        String accion =
                request.getParameter("accion");

        if ("eliminar".equals(accion)) {

            eliminar(request, response);
            return;
        }

        request.setAttribute(
                "evidencias",
                service.listarPorAlumno(
                        SesionAlumno.alumnoId(request)
                )
        );

        request.getRequestDispatcher(
                "/alumno/evidencias.jsp"
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

        String titulo =
                request.getParameter("titulo");

        String descripcion =
                request.getParameter("descripcion");

        String tipo =
                request.getParameter("tipo");

        Usuario usuario =
                SesionAlumno.usuario(request);

        EvidenciaResumen evidencia =
                new EvidenciaResumen();

        // Alumno real de la sesion (antes: alumno 1 "Andersson Cienfuegos").
        evidencia.setAlumnoId(SesionAlumno.alumnoId(request));
        evidencia.setAlumno(
                usuario == null ? "" : usuario.getNombreCompleto()
        );
        evidencia.setTitulo(titulo);
        evidencia.setDescripcion(descripcion);
        evidencia.setTipo(tipo);
        evidencia.setFecha(
                LocalDate.now().toString()
        );
        evidencia.setEstado("PENDIENTE");

        // Falla si el alumno aun no tiene empresa asignada.
        if (service.guardar(evidencia)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/alumno/evidencias"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/alumno/evidencias?error=1"
            );
        }
    }

    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            // Solo elimina evidencias del propio alumno y no aprobadas.
            service.eliminar(
                    id,
                    SesionAlumno.alumnoId(request)
            );

        } catch (Exception e) {
        }

        response.sendRedirect(
                request.getContextPath()
                + "/alumno/evidencias"
        );
    }
}
