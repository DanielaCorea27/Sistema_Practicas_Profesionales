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

@WebServlet(name = "ReporteGeneralServlet", urlPatterns = {"/reporte/general"})
public class ReporteGeneralServlet extends HttpServlet {

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

        List<RegistroHora> registros =
                service.listarTodos();

        int totalRegistros =
                service.contarRegistros();

        int pendientes =
                service.contarPendientes();

        int aprobados =
                service.contarAprobados();

        int rechazados =
                service.contarRechazados();

        double horasTotales =
                service.obtenerHorasTotalesSistema();

        double horasAprobadas =
                service.obtenerHorasAprobadasSistema();

        double horasPendientes =
                service.obtenerHorasPendientesSistema();

        request.setAttribute("registros", registros);
        request.setAttribute("totalRegistros", totalRegistros);
        request.setAttribute("pendientes", pendientes);
        request.setAttribute("aprobados", aprobados);
        request.setAttribute("rechazados", rechazados);
        request.setAttribute("horasTotales", horasTotales);
        request.setAttribute("horasAprobadas", horasAprobadas);
        request.setAttribute("horasPendientes", horasPendientes);

        request.getRequestDispatcher(
                "/reporte/general.jsp"
        ).forward(request, response);
    }
}