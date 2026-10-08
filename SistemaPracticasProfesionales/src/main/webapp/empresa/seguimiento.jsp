<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.edu.itca.practicas.model.ActividadAsignacion" %>
<%@ page import="sv.edu.itca.practicas.model.AsignacionResumen" %>
<%@ page import="sv.edu.itca.practicas.model.HorarioAsignacion" %>
<%@ page import="sv.edu.itca.practicas.model.ObservacionAsignacion" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();

    AsignacionResumen a = (AsignacionResumen) request.getAttribute("asignacion");

    @SuppressWarnings("unchecked")
    List<ActividadAsignacion> actividades =
            (List<ActividadAsignacion>) request.getAttribute("actividades");

    @SuppressWarnings("unchecked")
    List<HorarioAsignacion> horarios =
            (List<HorarioAsignacion>) request.getAttribute("horarios");

    @SuppressWarnings("unchecked")
    List<ObservacionAsignacion> observaciones =
            (List<ObservacionAsignacion>) request.getAttribute("observaciones");

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Mensaje de una sola vez.
    String flashTipo = (String) session.getAttribute("flashTipo");
    String flashMensaje = (String) session.getAttribute("flashMensaje");
    session.removeAttribute("flashTipo");
    session.removeAttribute("flashMensaje");

    String estadoBadge;

    switch (a.getEstado()) {
        case "ACTIVA":     estadoBadge = "success"; break;
        case "FINALIZADA": estadoBadge = "primary"; break;
        default:           estadoBadge = "secondary";
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Seguimiento | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
    <style>
        .barra { height: 16px; border-radius: 20px; background: #e9eef5; }
        .barra .progress-bar { border-radius: 20px; }
        .dato-label { font-size: 12px; color: #6c7a89; text-transform: uppercase; letter-spacing: .4px; }
        .nav-tabs .nav-link { color: #123b6d; font-weight: 600; }
        .nav-tabs .nav-link.active { color: #123b6d; }
    </style>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <a href="<%= ctx %>/empresa/estudiantes" class="text-decoration-none small">
        <i class="bi bi-arrow-left"></i> Volver a estudiantes
    </a>

    <div class="d-flex flex-wrap justify-content-between align-items-start mt-2 mb-4 gap-2">
        <div>
            <h2 class="page-title mb-1"><%= Html.esc(a.getAlumnoNombre()) %></h2>
            <div class="text-muted">
                Carnet <%= Html.esc(a.getCarnet()) %>
                &middot; <%= Html.esc(a.getCarrera()) %>
                (<%= Html.esc(a.getTipoCarrera()) %>)
            </div>
        </div>
        <div class="d-flex align-items-center gap-2">
            <span class="badge bg-<%= estadoBadge %> fs-6"><%= Html.esc(a.getEstado()) %></span>
            <% if (!"CANCELADA".equals(a.getEstado())) { %>
                <a class="btn btn-sm btn-outline-primary"
                   href="<%= ctx %>/empresa/evaluacion?id=<%= a.getId() %>">
                    <i class="bi bi-clipboard-check"></i> Evaluación final
                </a>
            <% } %>
        </div>
    </div>

    <% if (flashMensaje != null) { %>
        <div class="alert alert-<%= Html.esc(flashTipo) %>">
            <%= Html.esc(flashMensaje) %>
        </div>
    <% } %>

    <%-- ============ Avance ============ --%>
    <div class="row g-4 mb-4">

        <div class="col-lg-6">
            <div class="card panel-card h-100">
                <div class="card-body p-4">
                    <div class="dato-label mb-1">Horas en tu empresa</div>
                    <div class="d-flex justify-content-between align-items-end mb-2">
                        <div>
                            <span class="stat-number"><%= Html.horas(a.getHorasAprobadas()) %></span>
                            <span class="text-muted">de <%= a.getHorasPlanificadas() %> h</span>
                        </div>
                        <strong class="fs-4 text-success"><%= a.getPorcentajeEmpresa() %>%</strong>
                    </div>
                    <div class="progress barra">
                        <div class="progress-bar bg-success" role="progressbar"
                             style="width: <%= a.getPorcentajeEmpresa() %>%"></div>
                    </div>
                    <div class="small text-muted mt-2">
                        Por cubrir: <strong><%= Html.horas(a.getHorasPorCubrirEmpresa()) %> h</strong>
                        <% if (a.getHorasPendientes() > 0) { %>
                            &middot; <span class="text-warning">
                                <%= Html.horas(a.getHorasPendientes()) %> h por aprobar
                            </span>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-6">
            <div class="card panel-card h-100">
                <div class="card-body p-4">
                    <div class="dato-label mb-1">Avance total de la pasantía</div>
                    <div class="d-flex justify-content-between align-items-end mb-2">
                        <div>
                            <span class="stat-number"><%= Html.horas(a.getHorasTotalesPasantia()) %></span>
                            <span class="text-muted">de <%= a.getHorasRequeridas() %> h</span>
                        </div>
                        <strong class="fs-4" style="color:#1d6fa5;"><%= a.getPorcentajePasantia() %>%</strong>
                    </div>
                    <div class="progress barra">
                        <div class="progress-bar" role="progressbar"
                             style="width: <%= a.getPorcentajePasantia() %>%; background:#1d6fa5;"></div>
                    </div>
                    <div class="small text-muted mt-2">
                        Incluye las horas aprobadas en todas las empresas del estudiante.
                    </div>
                </div>
            </div>
        </div>

    </div>

    <%-- ============ Datos de la asignacion ============ --%>
    <div class="card panel-card mb-4">
        <div class="card-body p-4">
            <div class="row g-3">

                <div class="col-6 col-md-3">
                    <div class="dato-label">Tutor ITCA</div>
                    <div class="fw-semibold">
                        <%= a.getMaestroNombre() == null
                                ? "Sin asignar" : Html.esc(a.getMaestroNombre()) %>
                    </div>
                </div>

                <div class="col-6 col-md-3">
                    <div class="dato-label">Oportunidad</div>
                    <div class="fw-semibold">
                        <%= a.getOportunidadTitulo() == null
                                ? "—" : Html.esc(a.getOportunidadTitulo()) %>
                    </div>
                </div>

                <div class="col-6 col-md-3">
                    <div class="dato-label">Periodo</div>
                    <div class="fw-semibold">
                        <%= a.getFechaInicio() == null ? "—" : a.getFechaInicio() %>
                        a
                        <%= a.getFechaFin() == null ? "—" : a.getFechaFin() %>
                    </div>
                </div>

                <div class="col-6 col-md-3">
                    <div class="dato-label">Modalidad</div>
                    <div class="fw-semibold"><%= Html.esc(a.getModalidad()) %></div>
                </div>

                <div class="col-6 col-md-3">
                    <div class="dato-label">Correo</div>
                    <div><%= Html.esc(a.getCorreo()) %></div>
                </div>

                <div class="col-6 col-md-3">
                    <div class="dato-label">Teléfono</div>
                    <div><%= a.getTelefono() == null || a.getTelefono().isEmpty()
                                ? "—" : Html.esc(a.getTelefono()) %></div>
                </div>

            </div>
        </div>
    </div>

    <%-- ============ Pestañas ============ --%>
    <ul class="nav nav-tabs mb-3" id="tabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" data-bs-toggle="tab"
                    data-bs-target="#tab-actividades" type="button" role="tab">
                <i class="bi bi-list-check"></i> Actividades
                <span class="badge bg-secondary"><%= actividades.size() %></span>
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" data-bs-toggle="tab"
                    data-bs-target="#tab-horario" type="button" role="tab">
                <i class="bi bi-calendar-week"></i> Horario planificado
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" data-bs-toggle="tab"
                    data-bs-target="#tab-observaciones" type="button" role="tab">
                <i class="bi bi-chat-left-text"></i> Observaciones
                <span class="badge bg-secondary"><%= observaciones.size() %></span>
            </button>
        </li>
    </ul>

    <div class="tab-content">

        <%-- ----- Actividades ----- --%>
        <div class="tab-pane fade show active" id="tab-actividades" role="tabpanel">

            <div class="alert alert-light border small">
                <i class="bi bi-info-circle"></i>
                Solo las actividades <strong>aprobadas por el tutor</strong>
                cuentan para las horas realizadas y el avance.
            </div>

            <div class="card panel-card">
                <div class="card-body p-0">

                    <% if (actividades.isEmpty()) { %>

                        <div class="p-5 text-center text-muted">
                            <i class="bi bi-list-check fs-1"></i>
                            <p class="mt-2 mb-0">El estudiante aún no ha registrado actividades.</p>
                        </div>

                    <% } else { %>

                    <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Fecha</th>
                                <th>Actividad</th>
                                <th>Horario</th>
                                <th class="text-end">Horas</th>
                                <th>Estado</th>
                                <th>Observación del tutor</th>
                            </tr>
                        </thead>
                        <tbody>
                        <% for (ActividadAsignacion act : actividades) {

                            String b;

                            switch (act.getEstado()) {
                                case "APROBADO":  b = "success"; break;
                                case "RECHAZADO": b = "danger";  break;
                                default:          b = "warning text-dark";
                            }
                        %>
                            <tr>
                                <td class="text-nowrap"><%= act.getFecha() %></td>
                                <td>
                                    <div class="fw-semibold"><%= Html.esc(act.getTitulo()) %></div>
                                    <% if (act.getDescripcion() != null && !act.getDescripcion().isEmpty()) { %>
                                        <div class="small text-muted"><%= Html.esc(act.getDescripcion()) %></div>
                                    <% } %>
                                    <% if (act.getEvidencia() != null && !act.getEvidencia().isEmpty()) { %>
                                        <div class="small">
                                            <i class="bi bi-paperclip"></i>
                                            <%= Html.esc(act.getEvidencia()) %>
                                        </div>
                                    <% } %>
                                </td>
                                <td class="text-nowrap small">
                                    <%= Html.esc(act.getHoraInicio()) %> – <%= Html.esc(act.getHoraFin()) %>
                                </td>
                                <td class="text-end"><%= Html.horas(act.getHoras()) %></td>
                                <td><span class="badge bg-<%= b %>"><%= Html.esc(act.getEstado()) %></span></td>
                                <td class="small">
                                    <%= act.getObservacion() == null || act.getObservacion().isEmpty()
                                            ? "—" : Html.esc(act.getObservacion()) %>
                                </td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                    </div>

                    <% } %>

                </div>
            </div>
        </div>

        <%-- ----- Horario ----- --%>
        <div class="tab-pane fade" id="tab-horario" role="tabpanel">

            <div class="alert alert-light border small">
                <i class="bi bi-info-circle"></i>
                El horario es solo una <strong>planificación</strong>:
                no cuenta como horas realizadas.
            </div>

            <div class="card panel-card">
                <div class="card-body p-0">

                    <% if (horarios.isEmpty()) { %>

                        <div class="p-5 text-center text-muted">
                            <i class="bi bi-calendar-week fs-1"></i>
                            <p class="mt-2 mb-0">El estudiante aún no ha registrado su horario.</p>
                        </div>

                    <% } else { %>

                    <table class="table align-middle mb-0">
                        <thead class="table-light">
                            <tr><th>Día</th><th>Entrada</th><th>Salida</th></tr>
                        </thead>
                        <tbody>
                        <% for (HorarioAsignacion h : horarios) { %>
                            <tr>
                                <td class="fw-semibold"><%= Html.esc(h.getDiaNombre()) %></td>
                                <td><%= Html.esc(h.getHoraInicio()) %></td>
                                <td><%= Html.esc(h.getHoraFin()) %></td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>

                    <% } %>

                </div>
            </div>
        </div>

        <%-- ----- Observaciones ----- --%>
        <div class="tab-pane fade" id="tab-observaciones" role="tabpanel">

            <% if (!"CANCELADA".equals(a.getEstado())) { %>
            <div class="card panel-card mb-3">
                <div class="card-body p-4">

                    <form action="<%= ctx %>/empresa/seguimiento" method="post"
                          data-confirm="La observación quedará visible para el tutor de ITCA."
                          data-confirm-titulo="¿Guardar observación?"
                          data-confirm-boton="Guardar"
                          data-confirm-tipo="exito">

                        <input type="hidden" name="id" value="<%= a.getId() %>">

                        <label class="form-label fw-semibold">Nueva observación</label>

                        <textarea name="texto" class="form-control mb-3" rows="3"
                                  maxlength="1000" required
                                  placeholder="Escribe aquí tus comentarios sobre el desempeño del estudiante..."></textarea>

                        <button type="submit" class="btn btn-itca">
                            <i class="bi bi-save"></i> Guardar observación
                        </button>
                    </form>

                </div>
            </div>
            <% } %>

            <div class="card panel-card">
                <div class="card-body p-0">

                    <% if (observaciones.isEmpty()) { %>

                        <div class="p-5 text-center text-muted">
                            <i class="bi bi-chat-left-text fs-1"></i>
                            <p class="mt-2 mb-0">Todavía no hay observaciones.</p>
                        </div>

                    <% } else { %>

                    <ul class="list-group list-group-flush">
                    <% for (ObservacionAsignacion ob : observaciones) { %>
                        <li class="list-group-item p-4">
                            <div class="d-flex justify-content-between flex-wrap gap-1 mb-1">
                                <div>
                                    <strong><%= Html.esc(ob.getAutor()) %></strong>
                                    <span class="badge bg-light text-dark border ms-1">
                                        <%= Html.esc(ob.getRol()) %>
                                    </span>
                                </div>
                                <span class="small text-muted">
                                    <%= ob.getFechaHora() == null ? "" : ob.getFechaHora().format(fmt) %>
                                </span>
                            </div>
                            <div style="white-space: pre-line;"><%= Html.esc(ob.getTexto()) %></div>
                        </li>
                    <% } %>
                    </ul>

                    <% } %>

                </div>
            </div>
        </div>

    </div>

</div>

<jsp:include page="_scripts.jsp"/>
</body>
</html>
