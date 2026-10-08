package sv.edu.itca.practicas.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.itca.practicas.dao.FinalizacionPasantia;
import sv.edu.itca.practicas.model.AsignacionResumen;
import sv.edu.itca.practicas.model.EvaluacionRegistrada;
import sv.edu.itca.practicas.model.PreguntaEvaluacion;
import sv.edu.itca.practicas.model.Representante;
import sv.edu.itca.practicas.model.Usuario;
import sv.edu.itca.practicas.service.EvaluacionService;
import sv.edu.itca.practicas.util.ReglaNegocioException;

/**
 * Pantalla 22: Evaluacion final.
 *  - Si aun no se evaluo: formulario (preguntas + calificacion 1-10 + observaciones).
 *  - Si ya se evaluo: solo lectura (la evaluacion no se puede modificar).
 */
@WebServlet(
        name = "EmpresaEvaluacionServlet",
        urlPatterns = {"/empresa/evaluacion"}
)
public class EmpresaEvaluacionServlet extends HttpServlet {

    private final EvaluacionService service = new EvaluacionService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Representante rep = (Representante)
                request.getSession().getAttribute("representante");

        AsignacionResumen asignacion = cargar(request, rep);

        if (asignacion == null) {

            volverALista(request, response, "danger",
                    "No se encontró la asignación.");

            return;
        }

        if ("CANCELADA".equals(asignacion.getEstado())) {

            volverALista(request, response, "warning",
                    "No se puede evaluar una asignación cancelada.");

            return;
        }

        mostrar(request, response, asignacion);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        Representante rep = (Representante) session.getAttribute("representante");
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        AsignacionResumen asignacion = cargar(request, rep);

        if (asignacion == null) {

            volverALista(request, response, "danger",
                    "No se encontró la asignación.");

            return;
        }

        // Respuestas del formulario: p_<idPregunta> = idOpcion
        Map<Integer, Integer> respuestas = new LinkedHashMap<>();

        for (PreguntaEvaluacion p : service.preguntasActivas()) {

            String valor = request.getParameter("p_" + p.getId());

            if (valor != null) {

                try {
                    respuestas.put(p.getId(), Integer.parseInt(valor));
                } catch (NumberFormatException e) {
                    // se ignora: faltara la respuesta y se avisara
                }
            }
        }

        String calificacion = request.getParameter("calificacion");
        String observaciones = request.getParameter("observaciones");

        try {

            int resultado = service.guardar(
                    asignacion.getId(),
                    rep.getEmpresaId(),
                    usuario.getId(),
                    calificacion == null ? "" : calificacion,
                    observaciones,
                    respuestas);

            String mensaje;

            switch (resultado) {

                case FinalizacionPasantia.PASANTIA_FINALIZADA:
                    mensaje = "Evaluación guardada. ¡La pasantía del "
                            + "estudiante quedó FINALIZADA!";
                    break;

                case FinalizacionPasantia.ASIGNACION_FINALIZADA:
                    mensaje = "Evaluación guardada. La asignación del "
                            + "estudiante en tu empresa quedó FINALIZADA.";
                    break;

                default:
                    mensaje = "Evaluación guardada. Falta la evaluación del "
                            + "tutor de ITCA para finalizar la asignación.";
            }

            session.setAttribute("flashTipo", "success");
            session.setAttribute("flashMensaje", mensaje);

            response.sendRedirect(request.getContextPath()
                    + "/empresa/evaluacion?id=" + asignacion.getId());

        } catch (ReglaNegocioException e) {

            // Volver al formulario conservando lo que el usuario escribio.
            request.setAttribute("error", e.getMessage());
            request.setAttribute("seleccion", respuestas);
            request.setAttribute("calificacionForm", calificacion);
            request.setAttribute("observacionesForm", observaciones);

            mostrar(request, response, asignacion);
        }
    }

    private AsignacionResumen cargar(HttpServletRequest request, Representante rep) {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            return service.obtenerAsignacion(id, rep.getEmpresaId());

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void mostrar(
            HttpServletRequest request,
            HttpServletResponse response,
            AsignacionResumen asignacion)
            throws ServletException, IOException {

        EvaluacionRegistrada ev = service.evaluacionDeEmpresa(asignacion.getId());

        request.setAttribute("asignacion", asignacion);
        request.setAttribute("tutorEvaluo", service.tutorYaEvaluo(asignacion.getId()));

        if (ev != null) {

            request.setAttribute("evaluacion", ev);
            request.setAttribute("respuestas", service.respuestas(ev.getId()));

        } else {

            request.setAttribute("preguntas", service.preguntasActivas());
        }

        request.setAttribute("menuActivo", "evaluaciones");

        request.getRequestDispatcher("/empresa/evaluacion.jsp")
                .forward(request, response);
    }

    private void volverALista(
            HttpServletRequest request,
            HttpServletResponse response,
            String tipo,
            String mensaje)
            throws IOException {

        HttpSession session = request.getSession();

        session.setAttribute("flashTipo", tipo);
        session.setAttribute("flashMensaje", mensaje);

        response.sendRedirect(request.getContextPath() + "/empresa/evaluaciones");
    }
}
