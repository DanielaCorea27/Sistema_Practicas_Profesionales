<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.MaestroAdmin"%>
<%@page import="sv.edu.itca.practicas.util.Html"%>

<%
    @SuppressWarnings("unchecked")
    List<MaestroAdmin> maestros = (List<MaestroAdmin>) request.getAttribute("maestros");

    Integer totalMaestros = (Integer) request.getAttribute("totalMaestros");
    Integer maestrosActivos = (Integer) request.getAttribute("maestrosActivos");

    String mensajeOk = (String) request.getAttribute("mensajeOk");
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Maestros | Administración</title>

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

    </style>

</head>

<body>

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>
            <h2 class="fw-bold mb-1">👩‍🏫 Gestión de Maestros</h2>
            <p class="text-muted mb-0">Registro de tutores y control de acceso al sistema.</p>
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
                <div class="text-muted">Maestros registrados</div>
                <div class="fs-2 fw-bold"><%= totalMaestros %></div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card p-4">
                <div class="text-muted">Maestros activos</div>
                <div class="fs-2 fw-bold text-success"><%= maestrosActivos %></div>
            </div>
        </div>

    </div>

    <div class="card p-4 mb-4">

        <h5 class="fw-bold mb-3">Registrar maestro</h5>

        <form method="post"
              action="${pageContext.request.contextPath}/admin/maestros">

            <div class="row g-3">

                <div class="col-md-3">
                    <label class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control"
                           maxlength="80" required>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Apellido</label>
                    <input type="text" name="apellido" class="form-control"
                           maxlength="80" required>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Correo</label>
                    <input type="email" name="correo" class="form-control"
                           maxlength="120" required>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Contraseña inicial</label>
                    <input type="password" name="password" class="form-control"
                           minlength="8" required>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Código de empleado</label>
                    <input type="text" name="codigoEmpleado" class="form-control"
                           maxlength="20" placeholder="Ej. MAE-002" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Especialidad</label>
                    <input type="text" name="especialidad" class="form-control"
                           maxlength="100" placeholder="Opcional">
                </div>

                <div class="col-md-3">
                    <label class="form-label">Teléfono</label>
                    <input type="text" name="telefono" class="form-control"
                           maxlength="20" placeholder="Opcional">
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
            <h5 class="mb-0">Maestros registrados</h5>
        </div>

        <div class="table-responsive">

            <table class="table table-hover align-middle mb-0">

                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Código</th>
                        <th>Especialidad</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Estado</th>
                        <th>Acción</th>
                    </tr>
                </thead>

                <tbody>

                <%
                    if (maestros != null && !maestros.isEmpty()) {
                        for (MaestroAdmin maestro : maestros) {
                %>

                    <tr>
                        <td><%= maestro.getId() %></td>
                        <td><strong><%= Html.esc(maestro.getNombreCompleto()) %></strong></td>
                        <td><%= maestro.getCodigoEmpleado() == null ? "—" : Html.esc(maestro.getCodigoEmpleado()) %></td>
                        <td><%= maestro.getEspecialidad() == null ? "—" : Html.esc(maestro.getEspecialidad()) %></td>
                        <td><%= Html.esc(maestro.getCorreo()) %></td>
                        <td><%= maestro.getTelefono() == null ? "—" : Html.esc(maestro.getTelefono()) %></td>
                        <td>
                            <% if (maestro.isActivo()) { %>
                                <span class="badge bg-success">ACTIVO</span>
                            <% } else { %>
                                <span class="badge bg-secondary">INACTIVO</span>
                            <% } %>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/admin/maestros?accion=cambiarEstado&id=<%= maestro.getId() %>">
                                <%= maestro.isActivo() ? "Desactivar" : "Activar" %>
                            </a>
                        </td>
                    </tr>

                <%
                        }
                    } else {
                %>

                    <tr>
                        <td colspan="8" class="text-center text-muted py-4">
                            No hay maestros registrados.
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
