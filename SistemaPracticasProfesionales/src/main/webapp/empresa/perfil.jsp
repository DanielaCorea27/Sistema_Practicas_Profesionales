<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="sv.edu.itca.practicas.model.Empresa" %>
<%@ page import="sv.edu.itca.practicas.model.Representante" %>
<%@ page import="sv.edu.itca.practicas.util.Html" %>
<%
    Empresa e = (Empresa) request.getAttribute("empresa");
    Representante rep = (Representante) session.getAttribute("representante");
    String error = (String) request.getAttribute("error");
    boolean guardado = "1".equals(request.getParameter("ok"));

    String cargo = (String) request.getAttribute("cargoForm");
    if (cargo == null && rep != null) {
        cargo = rep.getCargo();
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Perfil de empresa | ITCA-FEPADE</title>
    <jsp:include page="_head.jsp"/>
</head>
<body>

<jsp:include page="_menu.jsp"/>

<div class="container-xl pb-5">

    <h2 class="page-title mb-4">Perfil de la empresa</h2>

    <% if (guardado) { %>
        <div class="alert alert-success">
            <i class="bi bi-check-circle"></i> Cambios guardados correctamente.
        </div>
    <% } %>

    <% if (error != null) { %>
        <div class="alert alert-danger">
            <i class="bi bi-exclamation-triangle"></i> <%= Html.esc(error) %>
        </div>
    <% } %>

    <% if (e == null) { %>
        <div class="alert alert-warning">No se encontró la información de la empresa.</div>
    <% } else { %>

    <form action="<%= request.getContextPath() %>/empresa/perfil" method="post"
          data-confirm="¿Deseas guardar los cambios del perfil de la empresa?"
          data-confirm-titulo="Guardar cambios"
          data-confirm-boton="Guardar"
          data-confirm-tipo="exito">

        <div class="card panel-card mb-4">
            <div class="card-body p-4">

                <h5 class="fw-bold text-primary mb-3">
                    <i class="bi bi-building"></i> Datos de la empresa
                </h5>

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Nombre *</label>
                        <input type="text" name="nombre" class="form-control"
                               maxlength="150" required
                               value="<%= Html.esc(e.getNombre()) %>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Persona de contacto</label>
                        <input type="text" name="contacto" class="form-control"
                               maxlength="150"
                               value="<%= Html.esc(e.getContacto()) %>">
                    </div>

                    <div class="col-12">
                        <label class="form-label fw-semibold">Descripción</label>
                        <textarea name="descripcion" class="form-control" rows="3"><%= Html.esc(e.getDescripcion()) %></textarea>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Dirección</label>
                        <input type="text" name="direccion" class="form-control"
                               maxlength="200"
                               value="<%= Html.esc(e.getDireccion()) %>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Sitio web</label>
                        <input type="text" name="sitioWeb" class="form-control"
                               maxlength="150"
                               value="<%= Html.esc(e.getSitioWeb()) %>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Teléfono</label>
                        <input type="text" name="telefono" class="form-control"
                               maxlength="20"
                               value="<%= Html.esc(e.getTelefono()) %>">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Correo de la empresa</label>
                        <input type="email" name="correo" class="form-control"
                               maxlength="120"
                               value="<%= Html.esc(e.getCorreo()) %>">
                    </div>

                </div>
            </div>
        </div>

        <div class="card panel-card mb-4">
            <div class="card-body p-4">

                <h5 class="fw-bold text-primary mb-3">
                    <i class="bi bi-person-badge"></i> Representante
                </h5>

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Cargo</label>
                        <input type="text" name="cargo" class="form-control"
                               maxlength="100"
                               value="<%= Html.esc(cargo) %>">
                    </div>

                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-itca">
            <i class="bi bi-save"></i> Guardar cambios
        </button>

    </form>

    <% } %>

</div>

<jsp:include page="_scripts.jsp"/>
</body>
</html>
