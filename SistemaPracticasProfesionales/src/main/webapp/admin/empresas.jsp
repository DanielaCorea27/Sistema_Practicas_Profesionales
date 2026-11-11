<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.EmpresaAdmin"%>
<%@page import="sv.edu.itca.practicas.util.Html"%>

<%
    @SuppressWarnings("unchecked")
    List<EmpresaAdmin> empresas = (List<EmpresaAdmin>) request.getAttribute("empresas");

    Integer totalEmpresas = (Integer) request.getAttribute("totalEmpresas");
    Integer empresasActivas = (Integer) request.getAttribute("empresasActivas");

    String mensajeOk = (String) request.getAttribute("mensajeOk");
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Empresas | Administración</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

    <style>

        body {
            background: #f4f6f9;
        }

        .card {
            border: none;
            border-radius: 16px;
            box-shadow: 0 4px 18px rgba(0,0,0,.06);
        }

        .descripcion {
            max-width: 280px;
        }

    </style>

</head>

<body>

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>
            <h2 class="fw-bold mb-1">🏢 Gestión de Empresas</h2>
            <p class="text-muted mb-0">Empresas que ofrecen prácticas profesionales.</p>
        </div>

        <a href="${pageContext.request.contextPath}/admin/dashboard.jsp"
           class="btn btn-secondary">
            ← Dashboard
        </a>

    </div>

    <% if (mensajeOk != null) { %>
        <div class="alert alert-success"><%= Html.esc(mensajeOk) %></div>
    <% } %>

    <% if (mensajeError != null) { %>
        <div class="alert alert-danger"><%= Html.esc(mensajeError) %></div>
    <% } %>

    <div class="row g-3 mb-4">

        <div class="col-md-6">
            <div class="card p-4">
                <div class="text-muted">Empresas registradas</div>
                <div class="fs-2 fw-bold"><%= totalEmpresas %></div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card p-4">
                <div class="text-muted">Empresas activas</div>
                <div class="fs-2 fw-bold text-success"><%= empresasActivas %></div>
            </div>
        </div>

    </div>

    <div class="card p-4 mb-4">

        <h5 class="fw-bold mb-3">Registrar empresa</h5>

        <form method="post"
              action="${pageContext.request.contextPath}/admin/empresas">

            <div class="row g-3">

                <div class="col-md-4">
                    <label class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control"
                           maxlength="150" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Persona de contacto</label>
                    <input type="text" name="contacto" class="form-control"
                           maxlength="150" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Teléfono</label>
                    <input type="text" name="telefono" class="form-control"
                           maxlength="20" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Correo</label>
                    <input type="email" name="correo" class="form-control"
                           maxlength="120" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Sitio web</label>
                    <input type="text" name="sitioWeb" class="form-control"
                           maxlength="150" placeholder="Opcional">
                </div>

                <div class="col-md-4">
                    <label class="form-label">Dirección</label>
                    <input type="text" name="direccion" class="form-control"
                           maxlength="200" placeholder="Opcional">
                </div>

                <div class="col-md-10">
                    <label class="form-label">Descripción</label>
                    <textarea name="descripcion" class="form-control" rows="2"
                              placeholder="Opcional"></textarea>
                </div>

                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">
                        Registrar
                    </button>
                </div>

            </div>

        </form>

    </div>

    <div class="card">

        <div class="card-header bg-white p-3">
            <h5 class="mb-0">Empresas registradas</h5>
        </div>

        <div class="table-responsive">

            <table class="table table-hover align-middle mb-0">

                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Empresa</th>
                        <th>Contacto</th>
                        <th>Teléfono</th>
                        <th>Correo</th>
                        <th>Representantes</th>
                        <th>Estado</th>
                        <th>Acción</th>
                    </tr>
                </thead>

                <tbody>

                <%
                    if (empresas != null && !empresas.isEmpty()) {
                        for (EmpresaAdmin empresa : empresas) {
                %>

                    <tr>
                        <td><%= empresa.getId() %></td>
                        <td class="descripcion">
                            <strong><%= Html.esc(empresa.getNombre()) %></strong>
                            <% if (empresa.getSitioWeb() != null) { %>
                                <div class="small">
                                    <a href="<%= Html.esc(empresa.getSitioWeb()) %>" target="_blank" rel="noopener">
                                        <%= Html.esc(empresa.getSitioWeb()) %>
                                    </a>
                                </div>
                            <% } %>
                            <% if (empresa.getDireccion() != null) { %>
                                <div class="small text-muted"><%= Html.esc(empresa.getDireccion()) %></div>
                            <% } %>
                            <% if (empresa.getDescripcion() != null) { %>
                                <div class="small text-muted"><%= Html.esc(empresa.getDescripcion()) %></div>
                            <% } %>
                        </td>
                        <td><%= empresa.getContacto() == null ? "—" : Html.esc(empresa.getContacto()) %></td>
                        <td><%= empresa.getTelefono() == null ? "—" : Html.esc(empresa.getTelefono()) %></td>
                        <td><%= empresa.getCorreo() == null ? "—" : Html.esc(empresa.getCorreo()) %></td>
                        <td><%= empresa.getTotalRepresentantes() %></td>
                        <td>
                            <% if (empresa.isActivo()) { %>
                                <span class="badge bg-success">ACTIVA</span>
                            <% } else { %>
                                <span class="badge bg-secondary">INACTIVA</span>
                            <% } %>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/admin/empresas?accion=cambiarEstado&id=<%= empresa.getId() %>">
                                <%= empresa.isActivo() ? "Desactivar" : "Activar" %>
                            </a>
                        </td>
                    </tr>

                <%
                        }
                    } else {
                %>

                    <tr>
                        <td colspan="8" class="text-center text-muted py-4">
                            No hay empresas registradas.
                        </td>
                    </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        </div>

    </div>

</div>

</body>

</html>
