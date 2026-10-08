<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.edu.itca.practicas.model.Oportunidad" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();

    @SuppressWarnings("unchecked")
    List<Oportunidad> lista = (List<Oportunidad>) request.getAttribute("oportunidades");

    String ok = request.getParameter("ok");
    String mensaje = null;
    String tipo = "success";

    if ("creada".equals(ok)) {
        mensaje = "Oportunidad creada. Quedó pendiente de aprobación por el tutor.";
    } else if ("editada".equals(ok)) {
        mensaje = "Oportunidad actualizada. Volvió a quedar pendiente de aprobación.";
    } else if ("cerrada".equals(ok)) {
        mensaje = "Oportunidad cerrada.";
    } else if ("noeditable".equals(ok)) {
        mensaje = "Esa oportunidad no existe o ya está cerrada.";
        tipo = "warning";
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Mis oportunidades | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="page-title mb-0">Mis oportunidades</h2>

        <a href="<%= ctx %>/empresa/oportunidad" class="btn btn-itca">
            <i class="bi bi-plus-circle"></i> Nueva oportunidad
        </a>
    </div>

    <% if (mensaje != null) { %>
        <div class="alert alert-<%= tipo %>"><%= Html.esc(mensaje) %></div>
    <% } %>

    <div class="card panel-card">
        <div class="card-body p-0">

            <% if (lista == null || lista.isEmpty()) { %>

                <div class="p-5 text-center text-muted">
                    <i class="bi bi-briefcase fs-1"></i>
                    <p class="mt-2 mb-0">
                        Aún no has publicado oportunidades.
                    </p>
                </div>

            <% } else { %>

            <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>Título</th>
                        <th>Modalidad</th>
                        <th class="text-end">Horas</th>
                        <th>Fechas</th>
                        <th class="text-center">Postulantes</th>
                        <th>Estado</th>
                        <th class="text-end">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% for (Oportunidad o : lista) {

                    String badge;

                    switch (o.getEstado()) {
                        case "APROBADA":  badge = "success";   break;
                        case "RECHAZADA": badge = "danger";    break;
                        case "CERRADA":   badge = "secondary"; break;
                        default:          badge = "warning text-dark";
                    }
                %>
                    <tr>
                        <td>
                            <div class="fw-semibold"><%= Html.esc(o.getTitulo()) %></div>
                            <% if (o.getDuracion() != null && !o.getDuracion().isEmpty()) { %>
                                <div class="small text-muted"><%= Html.esc(o.getDuracion()) %></div>
                            <% } %>
                            <% if ("RECHAZADA".equals(o.getEstado())
                                    && o.getObservacion() != null) { %>
                                <div class="small text-danger">
                                    <i class="bi bi-chat-left-text"></i>
                                    <%= Html.esc(o.getObservacion()) %>
                                </div>
                            <% } %>
                        </td>
                        <td><%= Html.esc(o.getModalidad()) %></td>
                        <td class="text-end"><%= o.getHorasOfrecidas() %></td>
                        <td class="small">
                            <%= o.getFechaInicio() == null ? "—" : o.getFechaInicio() %>
                            <br>
                            <%= o.getFechaFin() == null ? "—" : o.getFechaFin() %>
                        </td>
                        <td class="text-center">
                            <span class="badge bg-primary rounded-pill">
                                <%= o.getTotalPostulaciones() %>
                            </span>
                        </td>
                        <td>
                            <span class="badge bg-<%= badge %>">
                                <%= Html.esc(o.getEstado()) %>
                            </span>
                        </td>
                        <td class="text-end text-nowrap">

                            <% if (!"CERRADA".equals(o.getEstado())) { %>

                                <a class="btn btn-sm btn-outline-primary"
                                   href="<%= ctx %>/empresa/oportunidad?id=<%= o.getId() %>">
                                    <i class="bi bi-pencil"></i> Editar
                                </a>

                                <form action="<%= ctx %>/empresa/oportunidades"
                                      method="post" class="d-inline"
                                      data-confirm="¿Cerrar esta oportunidad? Ya no recibirá postulaciones."
                                      data-confirm-titulo="Cerrar oportunidad"
                                      data-confirm-boton="Cerrar oportunidad"
                                      data-confirm-tipo="peligro">
                                    <input type="hidden" name="accion" value="cerrar">
                                    <input type="hidden" name="id" value="<%= o.getId() %>">
                                    <button class="btn btn-sm btn-outline-secondary">
                                        <i class="bi bi-x-circle"></i> Cerrar
                                    </button>
                                </form>

                            <% } else { %>
                                <span class="text-muted small">—</span>
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
