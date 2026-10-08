<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.EvidenciaResumen"%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Mis evidencias</title>

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

        .btn-rounded {
            border-radius: 10px;
        }

    </style>

</head>

<body>

<nav class="navbar navbar-dark bg-dark">

    <div class="container">

        <a
            class="navbar-brand"
            href="<%=request.getContextPath()%>/alumno/dashboard.jsp">

            ITCA-FEPADE

        </a>

        <a
            class="btn btn-outline-light btn-sm"
            href="<%=request.getContextPath()%>/alumno/dashboard.jsp">

            Dashboard

        </a>

    </div>

</nav>

<div class="container py-4">

    <div class="d-flex justify-content-between
                align-items-center mb-4">

        <div>

            <h2 class="fw-bold">
                Mis evidencias
            </h2>

            <p class="text-muted">
                Presenta evidencias relacionadas con tu práctica profesional.
            </p>

        </div>

        <a
            href="<%=request.getContextPath()%>/alumno/dashboard.jsp"
            class="btn btn-secondary btn-rounded">

            ← Regresar

        </a>

    </div>


    <div class="card p-4 mb-4">

        <h5 class="fw-bold mb-3">
            Registrar evidencia
        </h5>

        <form
            method="POST"
            action="<%=request.getContextPath()%>/alumno/evidencias">

            <div class="row">

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Título
                    </label>

                    <input
                        type="text"
                        name="titulo"
                        class="form-control"
                        placeholder="Ej. Informe semanal 2"
                        required>

                </div>

                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Tipo
                    </label>

                    <select
                        name="tipo"
                        class="form-select"
                        required>

                        <option value="">
                            Seleccionar
                        </option>

                        <option value="INFORME">
                            Informe
                        </option>

                        <option value="CAPTURA">
                            Captura
                        </option>

                        <option value="DOCUMENTO">
                            Documento
                        </option>

                        <option value="OTRO">
                            Otro
                        </option>

                    </select>

                </div>

            </div>

            <div class="mb-3">

                <label class="form-label">
                    Descripción
                </label>

                <textarea
                    name="descripcion"
                    class="form-control"
                    rows="4"
                    placeholder="Describe la evidencia..."
                    required></textarea>

            </div>

            <button
                type="submit"
                class="btn btn-primary btn-rounded">

                Registrar evidencia

            </button>

        </form>

    </div>


    <div class="card">

        <div class="card-header bg-white p-3">

            <h5 class="mb-0">
                Historial de evidencias
            </h5>

        </div>

        <div class="card-body">

            <div class="table-responsive">

                <table class="table table-hover align-middle">

                    <thead>

                        <tr>

                            <th>Título</th>
                            <th>Tipo</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Acción</th>

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
                                    <%=evidencia.getTitulo()%>
                                </strong>

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

                                <% if (!"APROBADA".equalsIgnoreCase(
                                        evidencia.getEstado())) { %>

                                    <a
                                        href="<%=request.getContextPath()%>/alumno/evidencias?accion=eliminar&id=<%=evidencia.getId()%>"
                                        class="btn btn-outline-danger btn-sm">

                                        Eliminar

                                    </a>

                                <% } %>

                            </td>

                        </tr>

                    <%
                            }

                        } else {
                    %>

                        <tr>

                            <td
                                colspan="5"
                                class="text-center text-muted py-4">

                                No tienes evidencias registradas.

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