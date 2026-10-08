package sv.edu.itca.practicas.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.AlumnoResumen;
import sv.edu.itca.practicas.service.MaestroAlumnoService;
import sv.edu.itca.practicas.service.RegistroHoraService;

@WebServlet(
        name = "MaestroAlumnoDetalleServlet",
        urlPatterns = {"/maestro/alumno"}
)
public class MaestroAlumnoDetalleServlet extends HttpServlet {

    private MaestroAlumnoService alumnoService;
    private RegistroHoraService horaService;

    private static final double HORAS_REQUERIDAS = 640;

    @Override
    public void init() throws ServletException {

        alumnoService = new MaestroAlumnoService();
        horaService = new RegistroHoraService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idParametro =
                request.getParameter("id");

        int id;

        try {

            id = Integer.parseInt(idParametro);

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/alumnos"
            );

            return;
        }

        AlumnoResumen alumno =
                alumnoService.buscarPorId(id);

        if (alumno == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/maestro/alumnos"
            );

            return;
        }

        double horasRegistradas =
                horaService.obtenerTotalHoras(id);

        double horasAprobadas =
                horaService.obtenerHorasAprobadas(id);

        double horasRestantes =
                horaService.obtenerHorasRestantes(
                        id,
                        HORAS_REQUERIDAS
                );

        double porcentaje =
                horaService.obtenerPorcentaje(
                        id,
                        HORAS_REQUERIDAS
                );

        request.setAttribute(
                "alumno",
                alumno
        );

        request.setAttribute(
                "horasRegistradas",
                horasRegistradas
        );

        request.setAttribute(
                "horasAprobadas",
                horasAprobadas
        );

        request.setAttribute(
                "horasRestantes",
                horasRestantes
        );

        request.setAttribute(
                "porcentaje",
                porcentaje
        );

        request.setAttribute(
                "horasRequeridas",
                HORAS_REQUERIDAS
        );

        request.getRequestDispatcher(
                "/maestro/alumno-detalle.jsp"
        ).forward(
                request,
                response
        );
    }
}