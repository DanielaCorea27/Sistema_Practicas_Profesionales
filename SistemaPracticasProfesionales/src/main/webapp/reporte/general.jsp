<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.RegistroHora"%>

<%
    List<RegistroHora> registros =
            (List<RegistroHora>) request.getAttribute("registros");

    Integer totalRegistros =
            (Integer) request.getAttribute("totalRegistros");

    Integer pendientes =
            (Integer) request.getAttribute("pendientes");

    Integer aprobados =
            (Integer) request.getAttribute("aprobados");

    Integer rechazados =
            (Integer) request.getAttribute("rechazados");

    Double horasTotales =
            (Double) request.getAttribute("horasTotales");

    Double horasAprobadas =
            (Double) request.getAttribute("horasAprobadas");

    Double horasPendientes =
            (Double) request.getAttribute("horasPendientes");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Reporte General</title>

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

        .stat {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 18px rgba(0,0,0,.08);
        }

    </style>

</head>

<body>

<div class="header">

    <div class="container">

        <h2>Reporte General</h2>

        <p class="mb-0">
            Sistema de Seguimiento de Prácticas Profesionales
        </p>

    </div>

</div>

<div class="container py-4">

    <div class="row g-4 mb-4">

        <div class="col-md-3">

            <div class="card stat p-4">

                <h6>Total registros</h6>

                <h2>
                    <%= totalRegistros %>
                </h2>

            </div>

        </div>

        <div class="col-md-3">

            <div class="card stat p-4">

                <h6>Pendientes</h6>

                <h2>
                    <%= pendientes %>
                </h2>

            </div>

        </div>

        <div class="col-md-3">

            <div class="card stat p-4">

                <h6>Aprobados</h6>

                <h2>
                    <%= aprobados %>
                </h2>

            </div>

        </div>

        <div class="col-md-3">

            <div class="card stat p-4">

                <h6>Rechazados</h6>

                <h2>
                    <%= rechazados %>
                </h2>

            </div>

        </div>

    </div>

    <div class="row g-4 mb-4">

        <div class="col-md-4">

            <div class="card stat p-4">

                <h6>Horas registradas</h6>

                <h3>
                    <%= horasTotales %>
                </h3>

            </div>

        </div>

        <div class="col-md-4">

            <div class="card stat p-4">

                <h6>Horas aprobadas</h6>

                <h3>
                    <%= horasAprobadas %>
                </h3>

            </div>

        </div>

        <div class="col-md-4">

            <div class="card stat p-4">

                <h6>Horas pendientes</h6>

                <h3>
                    <%= horasPendientes %>
                </h3>

            </div>

        </div>

    </div>

    <div class="card stat">

        <div class="card-body">

            <h4 class="mb-4">
                Registros de prácticas
            </h4>

            <div class="table-responsive">

                <table class="table table-hover">

                    <thead>

                        <tr>

                            <th>ID</th>
                            <th>Alumno</th>
                            <th>Fecha</th>
                            <th>Horas</th>
                            <th>Actividad</th>
                            <th>Estado</th>

                        </tr>

                    </thead>

                    <tbody>

                    <%
                        if (registros != null) {

                            for (RegistroHora r : registros) {
                    %>

                        <tr>

                            <td>
                                <%= r.getId() %>
                            </td>

                            <td>
                                <%= r.getAlumnoId() %>
                            </td>

                            <td>
                                <%= r.getFecha() %>
                            </td>

                            <td>
                                <%= r.getHoras() %>
                            </td>

                            <td>
                                <%= r.getActividad() %>
                            </td>

                            <td>
                                <%= r.getEstado() %>
                            </td>

                        </tr>

                    <%
                            }
                        }
                    %>

                    </tbody>

                </table>

            </div>

            <a href="<%=request.getContextPath()%>/reporte/exportar"
               class="btn btn-success me-2">

                Exportar CSV

            </a>

            <a href="<%=request.getContextPath()%>/admin/dashboard.jsp"
               class="btn btn-secondary">

                Volver

            </a>

        </div>

    </div>

</div>

</body>

</html>