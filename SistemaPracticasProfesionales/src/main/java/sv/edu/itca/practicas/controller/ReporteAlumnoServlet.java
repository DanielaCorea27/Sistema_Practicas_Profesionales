package sv.edu.itca.practicas.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.RegistroHora;
import sv.edu.itca.practicas.service.RegistroHoraService;

@WebServlet(name = "ReporteAlumnoServlet", urlPatterns = {"/reporte/alumno"})
public class ReporteAlumnoServlet extends HttpServlet {

    private RegistroHoraService service;

    @Override
    public void init() throws ServletException {
        service = new RegistroHoraService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int alumnoId = 1;
        double horasRequeridas = 640;

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