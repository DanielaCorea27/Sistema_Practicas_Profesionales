package sv.edu.itca.practicas.controller;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sv.edu.itca.practicas.model.RegistroHora;
import sv.edu.itca.practicas.service.RegistroHoraService;

@WebServlet(
        name = "RegistroHoraServlet",
        urlPatterns = {"/alumno/horas"}
)
public class RegistroHoraServlet
        extends HttpServlet {


    private RegistroHoraService service;


    /*
     * Para esta primera versión utilizaremos
     * el alumno 1 como alumno de prueba.
     *
     * Cuando conectemos Usuarios + Alumnos,
     * este valor vendrá de la sesión.
     */
    private static final int ALUMNO_PRUEBA = 1;


    /*
     * Ejemplo para Ingeniería en Desarrollo
     * de Software.
     *
     * Más adelante esta información
     * vendrá directamente de Carrera.
     */
    private static final double HORAS_REQUERIDAS = 640;


    @Override
    public void init() {

        service =
                new RegistroHoraService();
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        String accion =
                request.getParameter("accion");


        if (accion == null
                || accion.trim().isEmpty()) {

            mostrarPanel(
                    request,
                    response
            );

            return;
        }


        switch (accion) {

            case "nuevo":

                nuevo(
                        request,
                        response
                );

                break;


            case "eliminar":

                eliminar(
                        request,
                        response
                );

                break;


            default:

                mostrarPanel(
                        request,
                        response
                );

                break;
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding(
                "UTF-8"
        );


        String accion =
                request.getParameter(
                        "accion"
                );


        if ("guardar".equals(accion)) {

            guardar(
                    request,
                    response
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/alumno/horas"
            );
        }
    }


    private void mostrarPanel(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "alumnoId",
                ALUMNO_PRUEBA
        );


        request.setAttribute(
                "registros",
                service.listarPorAlumno(
                        ALUMNO_PRUEBA
                )
        );


        double total =
                service.obtenerTotalHoras(
                        ALUMNO_PRUEBA
                );


        double aprobadas =
                service.obtenerHorasAprobadas(
                        ALUMNO_PRUEBA
                );


        double restantes =
                service.obtenerHorasRestantes(
                        ALUMNO_PRUEBA,
                        HORAS_REQUERIDAS
                );


        double porcentaje =
                service.obtenerPorcentaje(
                        ALUMNO_PRUEBA,
                        HORAS_REQUERIDAS
                );


        request.setAttribute(
                "totalHoras",
                total
        );


        request.setAttribute(
                "horasAprobadas",
                aprobadas
        );


        request.setAttribute(
                "horasRestantes",
                restantes
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
                "/alumno/horas.jsp"
        ).forward(
                request,
                response
        );
    }


    private void nuevo(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "fechaActual",
                LocalDate.now()
        );


        request.getRequestDispatcher(
                "/alumno/registro-hora.jsp"
        ).forward(
                request,
                response
        );
    }


    private void guardar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String fechaParametro =
                request.getParameter("fecha");


        String horasParametro =
                request.getParameter("horas");


        String actividad =
                request.getParameter(
                        "actividad"
                );


        LocalDate fecha;


        double horas;


        try {

            fecha =
                    LocalDate.parse(
                            fechaParametro
                    );

        } catch (Exception e) {

            mostrarError(
                    request,
                    response,
                    "La fecha ingresada no es válida."
            );

            return;
        }


        try {

            horas =
                    Double.parseDouble(
                            horasParametro
                    );

        } catch (Exception e) {

            mostrarError(
                    request,
                    response,
                    "Las horas deben ser un número válido."
            );

            return;
        }


        if (horas <= 0) {

            mostrarError(
                    request,
                    response,
                    "Las horas deben ser mayores que cero."
            );

            return;
        }


        if (horas > 24) {

            mostrarError(
                    request,
                    response,
                    "No se pueden registrar más de 24 horas en un día."
            );

            return;
        }


        if (actividad == null
                || actividad.trim().isEmpty()) {

            mostrarError(
                    request,
                    response,
                    "Debe describir la actividad realizada."
            );

            return;
        }


        RegistroHora registro =
                new RegistroHora();


        registro.setAlumnoId(
                ALUMNO_PRUEBA
        );


        registro.setFecha(
                fecha
        );


        registro.setHoras(
                horas
        );


        registro.setActividad(
                actividad.trim()
        );


        registro.setEstado(
                "PENDIENTE"
        );


        registro.setObservacion(
                ""
        );


        boolean guardado =
                service.guardar(
                        registro
                );


        if (guardado) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/alumno/horas"
            );

        } else {

            mostrarError(
                    request,
                    response,
                    "No fue posible guardar el registro."
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
                            request.getParameter(
                                    "id"
                            )
                    );


            service.eliminar(id);


            response.sendRedirect(
                    request.getContextPath()
                    + "/alumno/horas"
            );


        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse
                            .SC_BAD_REQUEST,
                    "ID inválido."
            );
        }
    }


    private void mostrarError(
            HttpServletRequest request,
            HttpServletResponse response,
            String mensaje)
            throws ServletException, IOException {

        request.setAttribute(
                "error",
                mensaje
        );


        request.setAttribute(
                "fechaActual",
                LocalDate.now()
        );


        request.getRequestDispatcher(
                "/alumno/registro-hora.jsp"
        ).forward(
                request,
                response
        );
    }
}