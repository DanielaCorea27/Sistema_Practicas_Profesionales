<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sv.edu.itca.practicas.model.AlumnoResumen"%>

<%
    AlumnoResumen alumno =
            (AlumnoResumen) request.getAttribute("alumno");

    double horasRegistradas =
            (Double) request.getAttribute("horasRegistradas");

    double horasAprobadas =
            (Double) request.getAttribute("horasAprobadas");

    double horasRestantes =
            (Double) request.getAttribute("horasRestantes");

    double porcentaje =
            (Double) request.getAttribute("porcentaje");

    double horasRequeridas =
            (Double) request.getAttribute("horasRequeridas");
%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Perfil del alumno | Maestro</title>

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
            border-radius: 18px;
            box-shadow: 0 4px 18px rgba(0,0,0,.06);
        }

        .profile-header {
            background: linear-gradient(
                135deg,
                #212529,
                #343a40
            );

            color: white;
            border-radius: 18px;
        }

        .avatar {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: white;
            color: #212529;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 32px;
            font-weight: 700;
        }

        .stat-number {
            font-size: 28px;
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
           href="<%=request.getContextPath()%>/maestro/dashboard">

            ITCA-FEPADE

        </a>

        <a
            href="<%=request.getContextPath()%>/maestro/alumnos"
            class="btn btn-outline-light btn-sm">

            Alumnos

        </a>

    </div>

</nav>

<div class="container py-4">

    <div class="d-flex justify-content-between
                align-items-center mb-4">

        <div>

            <h2 class="fw-bold mb-1">
                Perfil del alumno
            </h2>

            <p class="text-muted mb-0">
                Información y seguimiento de la práctica profesional.
            </p>

        </div>

        <a
            href="<%=request.getContextPath()%>/maestro/alumnos"
            class="btn btn-secondary btn-rounded">

            ← Regresar

        </a>

    </div>


    <div class="card profile-header p-4 mb-4">

        <div class="d-flex align-items-center">

            <div class="avatar me-4">

                <%= alumno.getNombre().substring(0, 1) %>

            </div>

            <div>

                <h3 class="mb-1">

                    <%= alumno.getNombre() %>

                </h3>

                <div>

                    Carnet:
                    <strong>
                        <%= alumno.getCarnet() %>
                    </strong>

                </div>

                <div>

                    Carrera:
                    <%= alumno.getCarrera() %>

                </div>

            </div>

        </div>

    </div>


    <div class="row g-4 mb-4">

        <div class="col-md-4">

            <div class="card p-4 h-100">

                <div class="text-muted">
                    Horas requeridas
                </div>

                <div class="stat-number">

                    <%= String.format(
                            "%.2f",
                            horasRequeridas
                    ) %> h

                </div>

            </div>

        </div>


        <div class="col-md-4">

            <div class="card p-4 h-100">

                <div class="text-muted">
                    Horas aprobadas
                </div>

                <div class="stat-number text-success">

                    <%= String.format(
                            "%.2f",
                            horasAprobadas
                    ) %> h

                </div>

            </div>

        </div>


        <div class="col-md-4">

            <div class="card p-4 h-100">

                <div class="text-muted">
                    Horas restantes
                </div>

                <div class="stat-number text-warning">

                    <%= String.format(
                            "%.2f",
                            horasRestantes
                    ) %> h

                </div>

            </div>

        </div>

    </div>


    <div class="card p-4 mb-4">

        <h5 class="fw-bold mb-3">
            Progreso de la práctica
        </h5>

        <div class="d-flex justify-content-between mb-2">

            <span>
                Avance
            </span>

            <strong>

                <%= String.format(
                        "%.1f",
                        porcentaje
                ) %>%

            </strong>

        </div>

        <div class="progress"
             style="height: 18px;">

            <div
                class="progress-bar bg-success"
                role="progressbar"
                style="width: <%= porcentaje %>%">

                <%= String.format(
                        "%.1f",
                        porcentaje
                ) %>%

            </div>

        </div>

        <div class="text-muted mt-2">

            Horas registradas:
            <strong>
                <%= String.format(
                        "%.2f",
                        horasRegistradas
                ) %>
            </strong>

        </div>

    </div>


    <div class="card p-4">

        <h5 class="fw-bold mb-3">
            Información de la práctica
        </h5>

        <div class="row">

            <div class="col-md-6 mb-3">

                <small class="text-muted">
                    Empresa
                </small>

                <div class="fw-bold">

                    <%= alumno.getEmpresa() %>

                </div>

            </div>


            <div class="col-md-6 mb-3">

                <small class="text-muted">
                    Estado
                </small>

                <div>

                    <% if ("ACTIVO".equalsIgnoreCase(
                            alumno.getEstado())) { %>

                        <span class="badge bg-success">
                            ACTIVO
                        </span>

                    <% } else { %>

                        <span class="badge bg-warning text-dark">

                            <%= alumno.getEstado() %>

                        </span>

                    <% } %>

                </div>

            </div>

        </div>

    </div>

</div>

</body>

</html>