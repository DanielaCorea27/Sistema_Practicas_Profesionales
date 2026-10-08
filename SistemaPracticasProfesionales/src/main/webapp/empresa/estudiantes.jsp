<%-- 
    Document   : sss
    Created on : 7 oct 2026, 8:08:12 p. m.
    Author     : danie
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.edu.itca.practicas.model.AsignacionResumen" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();

    @SuppressWarnings("unchecked")
    List<AsignacionResumen> lista =
            (List<AsignacionResumen>) request.getAttribute("asignaciones");

    String filtro = (String) request.getAttribute("filtro");
    if (filtro == null) {
        filtro = "ACTIVA";
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Estudiantes en práctica | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
    <style>
        .barra { height: 12px; border-radius: 20px; background: #e9eef5; }
        .barra .progress-bar { border-radius: 20px; background: #2f9e44; }
    </style>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <h2 class="page-title mb-3">Estudiantes en práctica</h2>

    <ul class="nav nav-pills mb-3">
        <li class="nav-item">
            <a class="nav-link <%= "ACTIVA".equals(filtro) ? "active" : "" %>"
               href="<%= ctx %>/empresa/estudiantes?estado=ACTIVA">Activos</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <%= "FINALIZADA".equals(filtro) ? "active" : "" %>"
               href="<%= ctx %>/empresa/estudiantes?estado=FINALIZADA">Finalizados</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <%= "TODAS".equals(filtro) ? "active" : "" %>"
               href="<%= ctx %>/empresa/estudiantes?estado=TODAS">Todos</a>
        </li>
    </ul>

    <div class="card panel-card">
        <div class="card-body p-0">

            <% if (lista == null || lista.isEmpty()) { %>

                <div class="p-5 text-center text-muted">
                    <i class="bi bi-mortarboard fs-1"></i>
                    <p class="mt-2 mb-0">No hay estudiantes para mostrar.</p>
                </div>

            <% } else { %>

            <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>Estudiante</th>
                        <th>Tutor ITCA</th>
                        <th style="min-width:230px;">Horas en tu empresa</th>
                        <th>Periodo</th>
                        <th>Estado</th>
                        <th class="text-end">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% for (AsignacionResumen a : lista) {

                    String badge;

                    switch (a.getEstado()) {
                        case "ACTIVA":     badge = "success";   break;
                        case "FINALIZADA": badge = "primary";   break;
                        default:           badge = "secondary";
                    }
                %>
                    <tr>
                        <td>
                            <div class="fw-semibold"><%= Html.esc(a.getAlumnoNombre()) %></div>
                            <div class="small text-muted">
                                Carnet <%= Html.esc(a.getCarnet()) %>
                            </div>
                            <div class="small text-muted"><%= Html.esc(a.getCarrera()) %></div>
                        </td>

                        <td>
                            <% if (a.getMaestroNombre() == null) { %>
                                <span class="badge bg-light text-dark border">Sin asignar</span>
                            <% } else { %>
                                <%= Html.esc(a.getMaestroNombre()) %>
                            <% } %>
                        </td>

                        <td>
                            <div class="d-flex justify-content-between small mb-1">
                                <span>
                                    <strong><%= Html.horas(a.getHorasAprobadas()) %></strong>
                                    de <%= a.getHorasPlanificadas() %> h
                                </span>
                                <strong><%= a.getPorcentajeEmpresa() %>%</strong>
                            </div>
                            <div class="progress barra">
                                <div class="progress-bar" role="progressbar"
                                     style="width: <%= a.getPorcentajeEmpresa() %>%"></div>
                            </div>
                            <% if (a.getHorasPendientes() > 0) { %>
                                <div class="small text-warning mt-1">
                                    <i class="bi bi-hourglass-split"></i>
                                    <%= Html.horas(a.getHorasPendientes()) %> h por aprobar
                                </div>
                            <% } %>
                        </td>

                        <td class="small">
                            <%= a.getFechaInicio() == null ? "—" : a.getFechaInicio() %>
                            <br>
                            <%= a.getFechaFin() == null ? "—" : a.getFechaFin() %>
                            <div class="text-muted"><%= Html.esc(a.getModalidad()) %></div>
                        </td>

                        <td>
                            <span class="badge bg-<%= badge %>"><%= Html.esc(a.getEstado()) %></span>
                        </td>

                        <td class="text-end text-nowrap">
                            <a class="btn btn-sm btn-outline-primary"
                               href="<%= ctx %>/empresa/seguimiento?id=<%= a.getId() %>">
                                <i class="bi bi-graph-up"></i> Seguimiento
                            </a>
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
