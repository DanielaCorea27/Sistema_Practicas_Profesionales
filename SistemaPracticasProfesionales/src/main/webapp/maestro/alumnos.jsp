<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.AlumnoResumen"%>

<!DOCTYPE html>
<html lang="es">
<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Alumnos | Maestro</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <style>

        body {
            background: #f4f6f9;
        }

        .navbar-brand {
            font-weight: 700;
        }

        .page-title {
            font-weight: 700;
        }

        .stat-card {
            border: none;
            border-radius: 16px;
            box-shadow: 0 4px 18px rgba(0,0,0,.06);
        }

        .table-card {
            border: none;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 4px 18px rgba(0,0,0,.06);
        }

        .badge-activo {
            background: #198754;
        }

        .badge-pendiente {
            background: #ffc107;
            color: #212529;
        }

        .btn-rounded {
            border-radius: 10px;
        }

    </style>

</head>

<body>

<nav class="navbar navbar-dark bg-dark">
    <div class="container">

        <a class="navbar-brand"
           href="<%= request.getContextPath() %>/maestro/dashboard">
            ITCA-FEPADE
        </a>

        <a class="btn btn-outline-light btn-sm"
           href="<%= request.getContextPath() %>/maestro/dashboard">
            Dashboard
        </a>

    </div>
</nav>

<div class="container py-4">

    <div class="d-flex justify-content-between
                align-items-center mb-4">

        <div>

            <h2 class="page-title mb-1">
                Alumnos
            </h2>

            <p class="text-muted mb-0">
                Gestión y seguimiento de alumnos en prácticas profesionales.
            </p>

        </div>

        <a href="<%= request.getContextPath() %>/maestro/dashboard"
           class="btn btn-secondary btn-rounded">
            ← Regresar
        </a>

    </div>

    <div class="row g-4 mb-4">

        <div class="col-md-6">

            <div class="card stat-card p-4">

                <div class="text-muted">
                    Total de alumnos
                </div>

                <div class="fs-2 fw-bold">
                    <%= request.getAttribute("totalAlumnos") %>
                </div>

            </div>

        </div>

        <div class="col-md-6">

            <div class="card stat-card p-4">

                <div class="text-muted">
                    Alumnos activos
                </div>

                <div class="fs-2 fw-bold text-success">
                    <%= request.getAttribute("alumnosActivos") %>
                </div>

            </div>

        </div>

    </div>

    <div class="card table-card">

        <div class="card-header bg-white p-3">

            <h5 class="mb-0">
                Lista de alumnos
            </h5>

        </div>

        <div class="card-body">

            <div class="table-responsive">

                <table class="table table-hover align-middle">

                    <thead>

                        <tr>

                            <th>#</th>
                            <th>Carnet</th>
                            <th>Alumno</th>
                            <th>Carrera</th>
                            <th>Empresa</th>
                            <th>Estado</th>
                            <th>Acciones</th>

                        </tr>

                    </thead>

                    <tbody>

                    <%
                        List<AlumnoResumen> alumnos =
                                (List<AlumnoResumen>)
                                request.getAttribute("alumnos");

                        if (alumnos != null && !alumnos.isEmpty()) {

                            for (AlumnoResumen alumno : alumnos) {
                    %>

                        <tr>

                            <td>
                                <%= alumno.getId() %>
                            </td>

                            <td>
                                <strong>
                                    <%= alumno.getCarnet() %>
                                </strong>
                            </td>

                            <td>
                                <%= alumno.getNombre() %>
                            </td>

                            <td>
                                <%= alumno.getCarrera() %>
                            </td>

                            <td>
                                <%= alumno.getEmpresa() %>
                            </td>

                            <td>

                                <% if ("ACTIVO".equalsIgnoreCase(
                                        alumno.getEstado())) { %>

                                    <span class="badge badge-activo">
                                        ACTIVO
                                    </span>

                                <% } else { %>

                                    <span class="badge badge-pendiente">
                                        <%= alumno.getEstado() %>
                                    </span>

                                <% } %>

                            </td>

                            <td>

                                <a
                                    href="<%= request.getContextPath() %>/maestro/alumno?id=<%= alumno.getId() %>"
                                    class="btn btn-primary btn-sm btn-rounded">

                                    Ver perfil

                                </a>

                            </td>

                        </tr>

                    <%
                            }

                        } else {
                    %>

                        <tr>

                            <td colspan="7"
                                class="text-center text-muted py-4">

                                No hay alumnos registrados.

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

</div>

</body>
</html>