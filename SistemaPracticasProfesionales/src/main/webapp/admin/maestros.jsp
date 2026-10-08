<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.MaestroAdmin"%>

<%
    List<MaestroAdmin> maestros =
            (List<MaestroAdmin>) request.getAttribute("maestros");

    Integer totalMaestros =
            (Integer) request.getAttribute("totalMaestros");

    Integer maestrosActivos =
            (Integer) request.getAttribute("maestrosActivos");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Maestros - Administración</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

</head>

<body class="bg-light">

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>

            <h2>
                👨‍🏫 Gestión de Maestros
            </h2>

            <p class="text-muted">
                Administración del personal docente.
            </p>

        </div>

        <a href="${pageContext.request.contextPath}/admin/dashboard.jsp"
           class="btn btn-secondary">

            ← Dashboard

        </a>

    </div>


    <div class="row g-3 mb-4">

        <div class="col-md-6">

            <div class="card shadow-sm">

                <div class="card-body">

                    <h6 class="text-muted">
                        Maestros registrados
                    </h6>

                    <h2>
                        <%= totalMaestros %>
                    </h2>

                </div>

            </div>

        </div>


        <div class="col-md-6">

            <div class="card shadow-sm">

                <div class="card-body">

                    <h6 class="text-muted">
                        Maestros activos
                    </h6>

                    <h2>
                        <%= maestrosActivos %>
                    </h2>

                </div>

            </div>

        </div>

    </div>


    <div class="card shadow-sm mb-4">

        <div class="card-header">

            <strong>
                Registrar nuevo maestro
            </strong>

        </div>

        <div class="card-body">

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/maestros">

                <div class="row g-3">

                    <div class="col-md-4">

                        <label class="form-label">
                            Nombre completo
                        </label>

                        <input type="text"
                               name="nombre"
                               class="form-control"
                               required>

                    </div>


                    <div class="col-md-3">

                        <label class="form-label">
                            Usuario
                        </label>

                        <input type="text"
                               name="usuario"
                               class="form-control"
                               required>

                    </div>


                    <div class="col-md-3">

                        <label class="form-label">
                            Especialidad
                        </label>

                        <input type="text"
                               name="especialidad"
                               class="form-control"
                               required>

                    </div>


                    <div class="col-md-2 d-flex align-items-end">

                        <button type="submit"
                                class="btn btn-primary w-100">

                            + Registrar

                        </button>

                    </div>

                </div>

            </form>

        </div>

    </div>


    <div class="card shadow-sm">

        <div class="card-header">

            <strong>
                Maestros registrados
            </strong>

        </div>

        <div class="card-body p-0">

            <div class="table-responsive">

                <table class="table table-hover mb-0">

                    <thead class="table-dark">

                    <tr>

                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Usuario</th>
                        <th>Especialidad</th>
                        <th>Estado</th>
                        <th>Acción</th>

                    </tr>

                    </thead>

                    <tbody>

                    <%
                        if (maestros != null) {

                            for (MaestroAdmin maestro : maestros) {
                    %>

                    <tr>

                        <td>
                            <%= maestro.getId() %>
                        </td>

                        <td>
                            <%= maestro.getNombre() %>
                        </td>

                        <td>
                            <%= maestro.getUsuario() %>
                        </td>

                        <td>
                            <%= maestro.getEspecialidad() %>
                        </td>

                        <td>

                            <% if ("ACTIVO".equalsIgnoreCase(
                                    maestro.getEstado())) { %>

                                <span class="badge bg-success">
                                    ACTIVO
                                </span>

                            <% } else { %>

                                <span class="badge bg-secondary">
                                    INACTIVO
                                </span>

                            <% } %>

                        </td>

                        <td>

                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/admin/maestros?accion=cambiarEstado&id=<%= maestro.getId() %>">

                                Cambiar estado

                            </a>

                        </td>

                    </tr>

                    <%
                            }
                        }
                    %>

                    </tbody>

                </table>

            </div>

        </div>

    </div>

</div>

</body>

</html>