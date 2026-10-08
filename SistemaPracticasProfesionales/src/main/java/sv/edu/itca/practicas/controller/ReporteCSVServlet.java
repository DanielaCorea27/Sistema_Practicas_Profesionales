package sv.edu.itca.practicas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.itca.practicas.model.RegistroHora;
import sv.edu.itca.practicas.service.RegistroHoraService;

@WebServlet(name = "ReporteCSVServlet", urlPatterns = {"/reporte/exportar"})
public class ReporteCSVServlet extends HttpServlet {

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

        response.setContentType("text/csv; charset=UTF-8");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=registro_practicas.csv"
        );

        PrintWriter out =
                response.getWriter();

        out.println(
                "ID,Alumno,Fecha,Horas,Actividad,Estado,Observacion"
        );

        for (RegistroHora r : registros) {

            String actividad =
                    r.getActividad() == null
                    ? ""
                    : r.getActividad()
                        .replace(",", " ");

            String observacion =
                    r.getObservacion() == null
                    ? ""
                    : r.getObservacion()
                        .replace(",", " ");

            out.println(
                    r.getId() + ","
                    + r.getAlumnoId() + ","
                    + r.getFecha() + ","
                    + r.getHoras() + ","
                    + actividad + ","
                    + r.getEstado() + ","
                    + observacion
            );
        }

        out.flush();
    }
}