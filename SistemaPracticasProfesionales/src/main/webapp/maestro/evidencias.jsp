<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.EvidenciaResumen"%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Evidencias | Maestro</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

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

<nav class="navbar navbar-dark bg-dark">

    <div class="container">

        <a
            class="navbar-brand"
            href="<%=request.getContextPath()%>/maestro/dashboard">

            ITCA-FEPADE

        </a>

        <a
            href="<%=request.getContextPath()%>/maestro/dashboard"
            class="btn btn-outline-light btn-sm">

            Dashboard

        </a>

    </div>

</nav>

<div class="container py-4">

    <div class="d-flex justify-content-between
                align-items-center mb-4">

        <div>

            <h2 class="fw-bold">
                Evidencias
            </h2>

            <p class="text-muted mb-0">
                Revisión de evidencias enviadas por los alumnos.
            </p>

        </div>

    </div>


    <div class="row g-4 mb-4">

        <div class="col-md-6">

            <div class="card p-4">

                <div class="text-muted">
                    Pendientes
                </div>

                <div class="fs-2 fw-bold text-warning">

                    <%=request.getAttribute("pendientes")%>

                </div>

            </div>

        </div>

        <div class="col-md-6">

            <div class="card p-4">

                <div class="text-muted">
                    Aprobadas
                </div>

                <div class="fs-2 fw-bold text-success">

                    <%=request.getAttribute("aprobadas")%>

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

                            <th>Alumno</th>
                            <th>Evidencia</th>
                            <th>Tipo</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Acciones</th>

                        </tr>

                    </thead>

                    <tbody>

                    <%
                        List<EvidenciaResumen> evidencias =
                                (List<EvidenciaResumen>)
                                request.getAttribute("evidencias");

                        if (evidencias != null
                                && !evidencias.isEmpty()) {

                            for (EvidenciaResumen evidencia :
                                    evidencias) {
                    %>

                        <tr>

                            <td>

                                <strong>
                                    <%=evidencia.getAlumno()%>
                                </strong>

                            </td>

                            <td>

                                <%=evidencia.getTitulo()%>

                                <div class="small text-muted">

                                    <%=evidencia.getDescripcion()%>

                                </div>

                            </td>

                            <td>
                                <%=evidencia.getTipo()%>
                            </td>

                            <td>
                                <%=evidencia.getFecha()%>
                            </td>

                            <td>

                                <% if ("APROBADA".equalsIgnoreCase(
                                        evidencia.getEstado())) { %>

                                    <span class="badge bg-success">
                                        APROBADA
                                    </span>

                                <% } else if ("RECHAZADA".equalsIgnoreCase(
                                        evidencia.getEstado())) { %>

                                    <span class="badge bg-danger">
                                        RECHAZADA
                                    </span>

                                <% } else { %>

                                    <span class="badge bg-warning text-dark">
                                        PENDIENTE
                                    </span>

                                <% } %>

                            </td>

                            <td>

                                <% if ("PENDIENTE".equalsIgnoreCase(
                                        evidencia.getEstado())) { %>

                                    <form
                                        method="POST"
                                        action="<%=request.getContextPath()%>/maestro/evidencias"
                                        class="d-flex gap-2">

                                        <input
                                            type="hidden"
                                            name="id"
                                            value="<%=evidencia.getId()%>">

                                        <button
                                            type="submit"
                                            name="accion"
                                            value="aprobar"
                                            class="btn btn-success btn-sm">

                                            Aprobar

                                        </button>

                                        <button
                                            type="submit"
                                            name="accion"
                                            value="rechazar"
                                            class="btn btn-danger btn-sm">

                                            Rechazar

                                        </button>

                                    </form>

                                <% } %>

                            </td>

                        </tr>

                    <%
                            }

                        } else {
                    %>

                        <tr>

                            <td
                                colspan="6"
                                class="text-center text-muted py-4">

                                No hay evidencias registradas.

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