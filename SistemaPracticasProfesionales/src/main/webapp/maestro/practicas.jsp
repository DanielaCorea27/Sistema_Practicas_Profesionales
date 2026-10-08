<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.PracticaResumen"%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Prácticas | Maestro</title>

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

        .card {
            border: none;
            border-radius: 16px;
            box-shadow: 0 4px 18px rgba(0,0,0,.06);
        }

        .page-title {
            font-weight: 700;
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
                Prácticas profesionales
            </h2>

            <p class="text-muted mb-0">
                Seguimiento de las prácticas asignadas a los alumnos.
            </p>

        </div>

        <a href="<%= request.getContextPath() %>/maestro/dashboard"
           class="btn btn-secondary btn-rounded">
            ← Regresar
        </a>

    </div>

    <div class="row g-4 mb-4">

        <div class="col-md-6">

            <div class="card p-4">

                <div class="text-muted">
                    Total de prácticas
                </div>

                <div class="fs-2 fw-bold">
                    <%= request.getAttribute("totalPracticas") %>
                </div>

            </div>

        </div>

        <div class="col-md-6">

            <div class="card p-4">

                <div class="text-muted">
                    Prácticas activas
                </div>

                <div class="fs-2 fw-bold text-success">
                    <%= request.getAttribute("practicasActivas") %>
                </div>

            </div>

        </div>

    </div>

    <div class="card">

        <div class="card-body">

            <div class="table-responsive">

                <table class="table table-hover align-middle">

                    <thead>

                        <tr>

                            <th>#</th>
                            <th>Alumno</th>
                            <th>Empresa</th>
                            <th>Carrera</th>
                            <th>Inicio</th>
                            <th>Fin</th>
                            <th>Estado</th>

                        </tr>

                    </thead>

                    <tbody>

                    <%
                        List<PracticaResumen> practicas =
                                (List<PracticaResumen>)
                                request.getAttribute("practicas");

                        if (practicas != null && !practicas.isEmpty()) {

                            for (PracticaResumen practica : practicas) {
                    %>

                        <tr>

                            <td>
                                <%= practica.getId() %>
                            </td>

                            <td>
                                <strong>
                                    <%= practica.getAlumno() %>
                                </strong>
                            </td>

                            <td>
                                <%= practica.getEmpresa() %>
                            </td>

                            <td>
                                <%= practica.getCarrera() %>
                            </td>

                            <td>
                                <%= practica.getFechaInicio() %>
                            </td>

                            <td>
                                <%= practica.getFechaFin() %>
                            </td>

                            <td>

                                <% if ("ACTIVA".equalsIgnoreCase(
                                        practica.getEstado())) { %>

                                    <span class="badge bg-success">
                                        ACTIVA
                                    </span>

                                <% } else { %>

                                    <span class="badge bg-warning text-dark">
                                        <%= practica.getEstado() %>
                                    </span>

                                <% } %>

                            </td>

                        </tr>

                    <%
                            }

                        } else {
                    %>

                        <tr>

                            <td colspan="7"
                                class="text-center text-muted py-4">

                                No hay prácticas registradas.

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