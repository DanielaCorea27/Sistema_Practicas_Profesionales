package sv.edu.itca.practicas.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.RegistroHora;
import sv.edu.itca.practicas.service.AlumnoPortalService;
import sv.edu.itca.practicas.service.RegistroHoraService;
import sv.edu.itca.practicas.util.SesionAlumno;

@WebServlet(name = "ReporteAlumnoServlet", urlPatterns = {"/reporte/alumno"})
public class ReporteAlumnoServlet extends HttpServlet {

    private RegistroHoraService service;

    private AlumnoPortalService portal;

    @Override
    public void init() throws ServletException {
        service = new RegistroHoraService();
        portal = new AlumnoPortalService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Alumno real de la sesion y horas que exige su carrera (320 / 640).
        int alumnoId = SesionAlumno.alumnoId(request);
        double horasRequeridas = portal.obtenerHorasRequeridas(alumnoId);

        List<RegistroHora> registros =
                service.listarPorAlumno(alumnoId);

        double horasTotales =
                service.obtenerTotalHoras(alumnoId);

        double horasAprobadas =
                service.obtenerHorasAprobadas(alumnoId);

        double horasRestantes =
                service.obtenerHorasRestantes(
                        alumnoId,
                        horasRequeridas
                );

        double porcentaje =
                service.obtenerPorcentaje(
                        alumnoId,
                        horasRequeridas
                );

        request.setAttribute("registros", registros);
        request.setAttribute("horasTotales", horasTotales);
        request.setAttribute("horasAprobadas", horasAprobadas);
        request.setAttribute("horasRestantes", horasRestantes);
        request.setAttribute("porcentaje", porcentaje);
        request.setAttribute("horasRequeridas", horasRequeridas);

        request.getRequestDispatcher(
                "/reporte/alumno.jsp"
        ).forward(request, response);
    }
}
