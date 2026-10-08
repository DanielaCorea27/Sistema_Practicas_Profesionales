<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.edu.itca.practicas.model.Postulante" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();

    @SuppressWarnings("unchecked")
    List<Postulante> lista = (List<Postulante>) request.getAttribute("postulantes");

    String filtro = (String) request.getAttribute("filtro");
    if (filtro == null) {
        filtro = "";
    }

    // Mensaje de una sola vez (lo deja el servlet tras aceptar/rechazar).
    String flashTipo = (String) session.getAttribute("flashTipo");
    String flashMensaje = (String) session.getAttribute("flashMensaje");
    session.removeAttribute("flashTipo");
    session.removeAttribute("flashMensaje");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Postulantes | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <h2 class="page-title mb-3">Postulantes</h2>

    <% if (flashMensaje != null) { %>
        <div class="alert alert-<%= Html.esc(flashTipo) %>">
            <%= Html.esc(flashMensaje) %>
        </div>
    <% } %>

    <ul class="nav nav-pills mb-3">
        <li class="nav-item">
            <a class="nav-link <%= filtro.isEmpty() ? "active" : "" %>"
               href="<%= ctx %>/empresa/postulantes">Todos</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <%= "PENDIENTE".equals(filtro) ? "active" : "" %>"
               href="<%= ctx %>/empresa/postulantes?estado=PENDIENTE">Pendientes</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <%= "ACEPTADA".equals(filtro) ? "active" : "" %>"
               href="<%= ctx %>/empresa/postulantes?estado=ACEPTADA">Aceptados</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <%= "RECHAZADA".equals(filtro) ? "active" : "" %>"
               href="<%= ctx %>/empresa/postulantes?estado=RECHAZADA">Rechazados</a>
        </li>
    </ul>

    <div class="card panel-card">
        <div class="card-body p-0">

            <% if (lista == null || lista.isEmpty()) { %>

                <div class="p-5 text-center text-muted">
                    <i class="bi bi-people fs-1"></i>
                    <p class="mt-2 mb-0">No hay postulantes para mostrar.</p>
                </div>

            <% } else { %>

            <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>Estudiante</th>
                        <th>Carrera</th>
                        <th>Oportunidad</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                        <th class="text-end">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% for (Postulante p : lista) {

                    String badge;

                    switch (p.getEstado()) {
                        case "ACEPTADA":  badge = "success"; break;
                        case "RECHAZADA": badge = "danger";  break;
                        default:          badge = "warning text-dark";
                    }
                %>
                    <tr>
                        <td>
                            <div class="fw-semibold"><%= Html.esc(p.getAlumnoNombre()) %></div>
                            <div class="small text-muted">
                                Carnet <%= Html.esc(p.getCarnet()) %>
                            </div>
                            <div class="small text-muted">
                                <%= Html.esc(p.getCorreo()) %>
                                <% if (p.getTelefono() != null && !p.getTelefono().isEmpty()) { %>
                                    &middot; <%= Html.esc(p.getTelefono()) %>
                                <% } %>
                            </div>
                        </td>

                        <td>
                            <div><%= Html.esc(p.getCarrera()) %></div>
                            <div class="small text-muted">
                                <%= Html.esc(p.getTipoCarrera()) %>
                                &middot; requiere <%= p.getHorasRequeridas() %> h
                            </div>
                            <div class="small <%= p.getHorasPorCubrir() == 0 ? "text-danger" : "text-muted" %>">
                                Horas por cubrir: <%= p.getHorasPorCubrir() %>
                            </div>
                        </td>

                        <td>
                            <div class="fw-semibold"><%= Html.esc(p.getOportunidadTitulo()) %></div>
                            <div class="small text-muted">
                                Ofrece <%= p.getHorasOfrecidas() %> h
                            </div>
                        </td>

                        <td class="small text-nowrap">
                            <%= p.getFechaPostulacion() %>
                        </td>

                        <td>
                            <span class="badge bg-<%= badge %>">
                                <%= Html.esc(p.getEstado()) %>
                            </span>
                        </td>

                        <td class="text-end text-nowrap">
                            <% if ("PENDIENTE".equals(p.getEstado())) { %>

                                <form action="<%= ctx %>/empresa/postulantes"
                                      method="post" class="d-inline"
                                      data-confirm="Se creará la pasantía del estudiante y su asignación en tu empresa."
                                      data-confirm-titulo="¿Aceptar a este estudiante?"
                                      data-confirm-boton="Aceptar estudiante"
                                      data-confirm-tipo="exito">
                                    <input type="hidden" name="accion" value="aceptar">
                                    <input type="hidden" name="id" value="<%= p.getPostulacionId() %>">
                                    <button class="btn btn-sm btn-success">
                                        <i class="bi bi-check-lg"></i> Aceptar
                                    </button>
                                </form>

                                <form action="<%= ctx %>/empresa/postulantes"
                                      method="post" class="d-inline"
                                      data-confirm="La postulación quedará como rechazada."
                                      data-confirm-titulo="¿Rechazar esta postulación?"
                                      data-confirm-boton="Rechazar"
                                      data-confirm-tipo="peligro">
                                    <input type="hidden" name="accion" value="rechazar">
                                    <input type="hidden" name="id" value="<%= p.getPostulacionId() %>">
                                    <button class="btn btn-sm btn-outline-danger">
                                        <i class="bi bi-x-lg"></i> Rechazar
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
