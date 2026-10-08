<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.RegistroHora"%>

<%
    List<RegistroHora> registros =
            (List<RegistroHora>) request.getAttribute("registros");

    Double horasTotales =
            (Double) request.getAttribute("horasTotales");

    Double horasAprobadas =
            (Double) request.getAttribute("horasAprobadas");

    Double horasRestantes =
            (Double) request.getAttribute("horasRestantes");

    Double porcentaje =
            (Double) request.getAttribute("porcentaje");

    Double horasRequeridas =
            (Double) request.getAttribute("horasRequeridas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Alumno</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: #f4f7fb;
        }

        .header {
            background: #123b6d;
            color: white;
            padding: 25px;
            border-radius: 0 0 20px 20px;
        }

        .card-stat {
            border: none;
            border-radius: 16px;
            box-shadow: 0 5px 18px rgba(0,0,0,.08);
        }

        .progress {
            height: 25px;
            border-radius: 20px;
        }
    </style>
</head>

<body>

<div class="header">
    <div class="container">
        <h2>Reporte de Prácticas Profesionales</h2>
        <p class="mb-0">Ingeniería en Desarrollo de Software</p>
    </div>
</div>

<div class="container py-4">

    <div class="row g-4 mb-4">

        <div class="col-md-3">
            <div class="card card-stat p-4">
                <h6>Horas requeridas</h6>
                <h2><%= horasRequeridas %></h2>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card card-stat p-4">
                <h6>Horas registradas</h6>
                <h2><%= horasTotales %></h2>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card card-stat p-4">
                <h6>Horas aprobadas</h6>
                <h2><%= horasAprobadas %></h2>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card card-stat p-4">
                <h6>Horas restantes</h6>
                <h2><%= horasRestantes %></h2>
            </div>
        </div>

    </div>

    <div class="card card-stat p-4 mb-4">

        <div class="d-flex justify-content-between">
            <h5>Avance de prácticas</h5>
            <strong><%= String.format("%.2f", porcentaje) %>%</strong>
        </div>

        <div class="progress mt-3">
            <div class="progress-bar"
                 role="progressbar"
                 style="width: <%= porcentaje %>%">
                <%= String.format("%.2f", porcentaje) %>%
            </div>
        </div>

    </div>

    <div class="card card-stat">

        <div class="card-body">

            <h4 class="mb-4">Detalle de horas</h4>

            <div class="table-responsive">

                <table class="table table-hover align-middle">

                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Horas</th>
                            <th>Actividad</th>
                            <th>Estado</th>
                            <th>Observación</th>
                        </tr>
                    </thead>

                    <tbody>

                    <%
                        if (registros != null && !registros.isEmpty()) {

                            for (RegistroHora r : registros) {
                    %>

                        <tr>

                            <td><%= r.getFecha() %></td>

                            <td>
                                <strong><%= r.getHoras() %></strong>
                            </td>

                            <td><%= r.getActividad() %></td>

                            <td>

                                <% if ("APROBADO".equalsIgnoreCase(r.getEstado())) { %>

                                    <span class="badge bg-success">
                                        APROBADO
                                    </span>

                                <% } else if ("RECHAZADO".equalsIgnoreCase(r.getEstado())) { %>

                                    <span class="badge bg-danger">
                                        RECHAZADO
                                    </span>

                                <% } else { %>

                                    <span class="badge bg-warning text-dark">
                                        PENDIENTE
                                    </span>

                                <% } %>

                            </td>

                            <td>
                                <%= r.getObservacion() == null
                                        ? ""
                                        : r.getObservacion() %>
                            </td>

                        </tr>

                    <%
                            }

                        } else {
                    %>

                        <tr>
                            <td colspan="5"
                                class="text-center py-4">
                                No hay registros.
                            </td>
                        </tr>

                    <%
                        }
                    %>

                    </tbody>

                </table>

            </div>

            <a href="<%=request.getContextPath()%>/alumno/dashboard"
               class="btn btn-secondary">
                Volver
            </a>

        </div>

    </div>

</div>

</body>
</html>