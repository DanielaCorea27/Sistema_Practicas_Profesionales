<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.edu.itca.practicas.model.AsignacionEvaluable" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();

    @SuppressWarnings("unchecked")
    List<AsignacionEvaluable> lista =
            (List<AsignacionEvaluable>) request.getAttribute("evaluables");

    int pendientes = 0;

    for (AsignacionEvaluable e : lista) {
        if (!e.isEvaluadoPorEmpresa()) {
            pendientes++;
        }
    }

    String flashTipo = (String) session.getAttribute("flashTipo");
    String flashMensaje = (String) session.getAttribute("flashMensaje");
    session.removeAttribute("flashTipo");
    session.removeAttribute("flashMensaje");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Evaluaciones | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <h2 class="page-title mb-1">Evaluaciones finales</h2>
    <p class="text-muted mb-4">
        <% if (pendientes == 0) { %>
            No tienes evaluaciones pendientes.
        <% } else { %>
            Tienes <strong><%= pendientes %></strong>
            evaluación<%= pendientes == 1 ? "" : "es" %> pendiente<%= pendientes == 1 ? "" : "s" %>.
        <% } %>
    </p>

    <% if (flashMensaje != null) { %>
        <div class="alert alert-<%= Html.esc(flashTipo) %>">
            <%= Html.esc(flashMensaje) %>
        </div>
    <% } %>

    <div class="card panel-card">
        <div class="card-body p-0">

            <% if (lista.isEmpty()) { %>

                <div class="p-5 text-center text-muted">
                    <i class="bi bi-clipboard-check fs-1"></i>
                    <p class="mt-2 mb-0">Aún no tienes estudiantes para evaluar.</p>
                </div>

            <% } else { %>

            <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>Estudiante</th>
                        <th>Tutor ITCA</th>
                        <th>Horas aprobadas</th>
                        <th>Tu evaluación</th>
                        <th>Evaluación del tutor</th>
                        <th>Asignación</th>
                        <th class="text-end">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% for (AsignacionEvaluable e : lista) { %>
                    <tr>
                        <td>
                            <div class="fw-semibold"><%= Html.esc(e.getAlumnoNombre()) %></div>
                            <div class="small text-muted">
                                Carnet <%= Html.esc(e.getCarnet()) %>
                                &middot; <%= Html.esc(e.getCarrera()) %>
                            </div>
                        </td>

                        <td>
                            <% if (e.getMaestroNombre() == null) { %>
                                <span class="badge bg-light text-dark border">Sin asignar</span>
                            <% } else { %>
                                <%= Html.esc(e.getMaestroNombre()) %>
                            <% } %>
                        </td>

                        <td class="text-nowrap">
                            <%= Html.horas(e.getHorasAprobadas()) %>
                            / <%= e.getHorasPlanificadas() %> h
                        </td>

                        <td>
                            <% if (e.isEvaluadoPorEmpresa()) { %>
                                <span class="badge bg-success">
                                    <%= Html.horas(e.getCalificacionEmpresa()) %> / 10
                                </span>
                            <% } else { %>
                                <span class="badge bg-warning text-dark">Pendiente</span>
                            <% } %>
                        </td>

                        <td>
                            <% if (e.isEvaluadoPorTutor()) { %>
                                <span class="badge bg-success">Registrada</span>
                            <% } else { %>
                                <span class="badge bg-secondary">Pendiente</span>
                            <% } %>
                        </td>

                        <td>
                            <span class="badge bg-<%= "FINALIZADA".equals(e.getEstado()) ? "primary" : "success" %>">
                                <%= Html.esc(e.getEstado()) %>
                            </span>
                        </td>

                        <td class="text-end text-nowrap">
                            <% if (e.isEvaluadoPorEmpresa()) { %>
                                <a class="btn btn-sm btn-outline-primary"
                                   href="<%= ctx %>/empresa/evaluacion?id=<%= e.getAsignacionId() %>">
                                    <i class="bi bi-eye"></i> Ver evaluación
                                </a>
                            <% } else { %>
                                <a class="btn btn-sm btn-primary"
                                   href="<%= ctx %>/empresa/evaluacion?id=<%= e.getAsignacionId() %>">
                                    <i class="bi bi-pencil-square"></i> Evaluar
                                </a>
                            <% } %>
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

<jsp:include page="_scripts.jsp"/>
</body>
</html>
