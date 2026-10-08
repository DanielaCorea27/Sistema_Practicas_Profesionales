<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="sv.edu.itca.practicas.model.Oportunidad" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    String ctx = request.getContextPath();

    Oportunidad o = (Oportunidad) request.getAttribute("oportunidad");
    String error = (String) request.getAttribute("error");

    boolean edicion = o != null && o.getId() > 0;

    String modalidad = o != null && o.getModalidad() != null
            ? o.getModalidad() : "";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title><%= edicion ? "Editar" : "Nueva" %> oportunidad | Empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <h2 class="page-title mb-1">
        <%= edicion ? "Editar oportunidad" : "Nueva oportunidad" %>
    </h2>

    <p class="text-muted mb-4">
        <% if (edicion) { %>
            Al guardar, la oportunidad volverá a revisión del tutor.
        <% } else { %>
            Será revisada por un tutor de ITCA antes de mostrarse a los estudiantes.
        <% } %>
    </p>

    <% if (error != null) { %>
        <div class="alert alert-danger">
            <i class="bi bi-exclamation-triangle"></i> <%= Html.esc(error) %>
        </div>
    <% } %>

    <form action="<%= ctx %>/empresa/oportunidad" method="post"
          data-confirm="<%= edicion
                  ? "Al guardar, la oportunidad volverá a revisión del tutor. ¿Deseas continuar?"
                  : "Se enviará a revisión del tutor antes de mostrarse a los estudiantes. ¿Publicar la oportunidad?" %>"
          data-confirm-titulo="<%= edicion ? "Guardar cambios" : "Publicar oportunidad" %>"
          data-confirm-boton="<%= edicion ? "Guardar" : "Publicar" %>"
          data-confirm-tipo="exito">

        <% if (edicion) { %>
            <input type="hidden" name="id" value="<%= o.getId() %>">
        <% } %>

        <div class="card panel-card mb-4">
            <div class="card-body p-4">

                <div class="row g-3">

                    <div class="col-12">
                        <label class="form-label fw-semibold">Título *</label>
                        <input type="text" name="titulo" class="form-control"
                               maxlength="150" required
                               placeholder="Ej: Desarrollador Java Junior"
                               value="<%= Html.esc(o != null ? o.getTitulo() : "") %>">
                    </div>

                    <div class="col-12">
                        <label class="form-label fw-semibold">Descripción *</label>
                        <textarea name="descripcion" class="form-control" rows="4"
                                  required><%= Html.esc(o != null ? o.getDescripcion() : "") %></textarea>
                    </div>

                    <div class="col-12">
                        <label class="form-label fw-semibold">Requisitos</label>
                        <textarea name="requisitos" class="form-control" rows="3"><%= Html.esc(o != null ? o.getRequisitos() : "") %></textarea>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label fw-semibold">Modalidad *</label>
                        <select name="modalidad" class="form-select" required>
                            <option value="">Selecciona...</option>
                            <option value="PRESENCIAL" <%= "PRESENCIAL".equals(modalidad) ? "selected" : "" %>>Presencial</option>
                            <option value="REMOTA"     <%= "REMOTA".equals(modalidad) ? "selected" : "" %>>Remota</option>
                            <option value="HIBRIDA"    <%= "HIBRIDA".equals(modalidad) ? "selected" : "" %>>Híbrida</option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label fw-semibold">Horas ofrecidas *</label>
                        <input type="number" name="horas" class="form-control"
                               min="1" max="640" required
                               placeholder="320 ó 640"
                               value="<%= o != null && o.getHorasOfrecidas() > 0 ? o.getHorasOfrecidas() : "" %>">
                        <div class="form-text">Técnico 320 h · Ingeniería 640 h</div>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label fw-semibold">Duración</label>
                        <input type="text" name="duracion" class="form-control"
                               maxlength="50" placeholder="Ej: 4 meses"
                               value="<%= Html.esc(o != null ? o.getDuracion() : "") %>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Fecha de inicio</label>
                        <input type="date" name="fechaInicio" class="form-control"
                               value="<%= o != null && o.getFechaInicio() != null ? o.getFechaInicio() : "" %>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Fecha de fin</label>
                        <input type="date" name="fechaFin" class="form-control"
                               value="<%= o != null && o.getFechaFin() != null ? o.getFechaFin() : "" %>">
                    </div>

                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-itca">
            <i class="bi bi-save"></i>
            <%= edicion ? "Guardar cambios" : "Publicar oportunidad" %>
        </button>

        <a href="<%= ctx %>/empresa/oportunidades" class="btn btn-outline-secondary ms-2">
            Cancelar
        </a>

    </form>

</div>

<jsp:include page="_scripts.jsp"/>
</body>
</html>
