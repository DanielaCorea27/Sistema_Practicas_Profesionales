<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="sv.edu.itca.practicas.model.AsignacionResumen" %>
<%@ page import="sv.edu.itca.practicas.model.EvaluacionRegistrada" %>
<%@ page import="sv.edu.itca.practicas.model.OpcionEvaluacion" %>
<%@ page import="sv.edu.itca.practicas.model.PreguntaEvaluacion" %>
<%@ page import="sv.edu.itca.practicas.model.RespuestaEvaluada" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();

    AsignacionResumen a = (AsignacionResumen) request.getAttribute("asignacion");
    EvaluacionRegistrada ev = (EvaluacionRegistrada) request.getAttribute("evaluacion");

    Boolean tutorEvaluoObj = (Boolean) request.getAttribute("tutorEvaluo");
    boolean tutorEvaluo = tutorEvaluoObj != null && tutorEvaluoObj;

    boolean horasIncompletas = a.getHorasAprobadas() < a.getHorasPlanificadas();

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String flashTipo = (String) session.getAttribute("flashTipo");
    String flashMensaje = (String) session.getAttribute("flashMensaje");
    session.removeAttribute("flashTipo");
    session.removeAttribute("flashMensaje");

    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Evaluación final | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
    <style>
        .nota-grande { font-size: 56px; font-weight: 800; line-height: 1; }
        .pregunta-num {
            width: 30px; height: 30px; border-radius: 50%;
            background: #123b6d; color: #fff; font-weight: 700; font-size: 14px;
            display: inline-flex; align-items: center; justify-content: center;
            margin-right: 8px;
        }
        .btn-check:checked + .btn-outline-primary {
            background: #123b6d; border-color: #123b6d; color: #fff;
        }
    </style>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <a href="<%= ctx %>/empresa/evaluaciones" class="text-decoration-none small">
        <i class="bi bi-arrow-left"></i> Volver a evaluaciones
    </a>

    <div class="d-flex flex-wrap justify-content-between align-items-start mt-2 mb-4 gap-2">
        <div>
            <h2 class="page-title mb-1">Evaluación final</h2>
            <div class="fw-semibold fs-5"><%= Html.esc(a.getAlumnoNombre()) %></div>
            <div class="text-muted">
                Carnet <%= Html.esc(a.getCarnet()) %>
                &middot; <%= Html.esc(a.getCarrera()) %>
            </div>
        </div>

        <div class="text-end small">
            <div>
                Horas aprobadas en tu empresa:
                <strong><%= Html.horas(a.getHorasAprobadas()) %> / <%= a.getHorasPlanificadas() %> h</strong>
            </div>
            <div class="mt-1">
                Evaluación del tutor:
                <% if (tutorEvaluo) { %>
                    <span class="badge bg-success">Registrada</span>
                <% } else { %>
                    <span class="badge bg-secondary">Pendiente</span>
                <% } %>
            </div>
        </div>
    </div>

    <% if (flashMensaje != null) { %>
        <div class="alert alert-<%= Html.esc(flashTipo) %>">
            <%= Html.esc(flashMensaje) %>
        </div>
    <% } %>

    <% if (ev != null) {

        /* ===================== SOLO LECTURA ===================== */

        @SuppressWarnings("unchecked")
        List<RespuestaEvaluada> respuestas =
                (List<RespuestaEvaluada>) request.getAttribute("respuestas");

        String colorNota;

        if (ev.getCalificacion() >= 9)      colorNota = "#2f9e44";
        else if (ev.getCalificacion() >= 7) colorNota = "#1d6fa5";
        else if (ev.getCalificacion() >= 6) colorNota = "#f08c00";
        else                                colorNota = "#e03131";
    %>

        <div class="alert alert-light border">
            <i class="bi bi-lock"></i>
            Esta evaluación ya fue registrada y <strong>no se puede modificar</strong>.
        </div>

        <div class="row g-4">

            <div class="col-lg-4">
                <div class="card panel-card h-100">
                    <div class="card-body p-4 text-center">
                        <div class="text-muted small text-uppercase mb-2">Calificación final</div>
                        <div class="nota-grande" style="color:<%= colorNota %>;">
                            <%= Html.horas(ev.getCalificacion()) %>
                        </div>
                        <div class="text-muted">de 10</div>
                        <hr>
                        <div class="small text-muted">
                            Evaluó: <strong><%= Html.esc(ev.getEvaluador()) %></strong><br>
                            <%= ev.getFecha().format(fmt) %>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-8">
                <div class="card panel-card mb-4">
                    <div class="card-body p-4">

                        <h5 class="fw-bold text-primary mb-3">
                            <i class="bi bi-ui-radios"></i> Respuestas
                        </h5>

                        <% if (respuestas.isEmpty()) { %>
                            <div class="text-muted">No hay preguntas respondidas.</div>
                        <% } else { %>
                            <ul class="list-group list-group-flush">
                            <% for (RespuestaEvaluada r : respuestas) { %>
                                <li class="list-group-item px-0 d-flex justify-content-between align-items-center gap-3">
                                    <span><%= Html.esc(r.getPregunta()) %></span>
                                    <span class="badge bg-primary"><%= Html.esc(r.getOpcion()) %></span>
                                </li>
                            <% } %>
                            </ul>
                        <% } %>

                    </div>
                </div>

                <div class="card panel-card">
                    <div class="card-body p-4">

                        <h5 class="fw-bold text-primary mb-3">
                            <i class="bi bi-chat-left-text"></i> Observaciones
                        </h5>

                        <% if (ev.getObservaciones() == null || ev.getObservaciones().isEmpty()) { %>
                            <div class="text-muted">Sin observaciones.</div>
                        <% } else { %>
                            <div style="white-space: pre-line;"><%= Html.esc(ev.getObservaciones()) %></div>
                        <% } %>

                    </div>
                </div>
            </div>

        </div>

    <% } else {

        /* ===================== FORMULARIO ===================== */

        @SuppressWarnings("unchecked")
        List<PreguntaEvaluacion> preguntas =
                (List<PreguntaEvaluacion>) request.getAttribute("preguntas");

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> seleccion =
                (Map<Integer, Integer>) request.getAttribute("seleccion");

        String calificacionForm = (String) request.getAttribute("calificacionForm");
        String observacionesForm = (String) request.getAttribute("observacionesForm");

        String mensajeConfirmar =
                "La evaluación final no se puede modificar después de guardarla.";

        if (horasIncompletas) {
            mensajeConfirmar += " Atención: el estudiante lleva "
                    + Html.horas(a.getHorasAprobadas()) + " de "
                    + a.getHorasPlanificadas()
                    + " horas aprobadas en tu empresa.";
        }
    %>

        <% if (error != null) { %>
            <div class="alert alert-danger">
                <i class="bi bi-exclamation-triangle"></i> <%= Html.esc(error) %>
            </div>
        <% } %>

        <% if (horasIncompletas) { %>
            <div class="alert alert-warning">
                <i class="bi bi-hourglass-split"></i>
                El estudiante aún tiene
                <strong><%= Html.horas(a.getHorasPorCubrirEmpresa()) %> h</strong>
                por cubrir en tu empresa (aprobadas por el tutor).
                Puedes evaluarlo igualmente si ya terminó su práctica.
            </div>
        <% } %>

        <form action="<%= ctx %>/empresa/evaluacion" method="post"
              data-confirm="<%= Html.esc(mensajeConfirmar) %>"
              data-confirm-titulo="¿Guardar evaluación final?"
              data-confirm-boton="Guardar evaluación"
              data-confirm-tipo="aviso">

            <input type="hidden" name="id" value="<%= a.getId() %>">

            <%-- ----- Preguntas ----- --%>
            <% if (preguntas.isEmpty()) { %>

                <div class="alert alert-light border">
                    No hay preguntas de evaluación activas. Solo se registrará la
                    calificación y las observaciones.
                </div>

            <% } else {

                int numero = 0;

                for (PreguntaEvaluacion p : preguntas) {

                    numero++;

                    Integer elegida = seleccion == null ? null : seleccion.get(p.getId());
            %>
                <div class="card panel-card mb-3">
                    <div class="card-body p-4">

                        <div class="fw-semibold mb-3">
                            <span class="pregunta-num"><%= numero %></span>
                            <%= Html.esc(p.getTexto()) %>
                        </div>

                        <div class="d-flex flex-wrap gap-2">
                        <% for (OpcionEvaluacion o : p.getOpciones()) {

                            String inputId = "p" + p.getId() + "_o" + o.getId();
                        %>
                            <input type="radio" class="btn-check"
                                   name="p_<%= p.getId() %>"
                                   id="<%= inputId %>"
                                   value="<%= o.getId() %>" required
                                   <%= elegida != null && elegida == o.getId() ? "checked" : "" %>>
                            <label class="btn btn-outline-primary rounded-pill px-4"
                                   for="<%= inputId %>">
                                <%= Html.esc(o.getTexto()) %>
                            </label>
                        <% } %>
                        </div>

                    </div>
                </div>
            <%  }
               } %>

            <%-- ----- Calificacion y observaciones ----- --%>
            <div class="card panel-card mb-4">
                <div class="card-body p-4">

                    <div class="row g-4">

                        <div class="col-md-3">
                            <label class="form-label fw-semibold">Calificación final (1 a 10) *</label>
                            <input type="number" name="calificacion"
                                   class="form-control form-control-lg text-center"
                                   min="1" max="10" step="0.1" required
                                   value="<%= Html.esc(calificacionForm) %>">
                        </div>

                        <div class="col-md-9">
                            <label class="form-label fw-semibold">Observaciones</label>
                            <textarea name="observaciones" class="form-control" rows="4"
                                      maxlength="1000"
                                      placeholder="Comentarios sobre el desempeño del estudiante (opcional)"><%= Html.esc(observacionesForm) %></textarea>
                        </div>

                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-itca">
                <i class="bi bi-send-check"></i> Guardar evaluación
            </button>

            <a href="<%= ctx %>/empresa/evaluaciones" class="btn btn-outline-secondary ms-2">
                Cancelar
            </a>

        </form>

    <% } %>

</div>

<jsp:include page="_scripts.jsp"/>
</body>
</html>
