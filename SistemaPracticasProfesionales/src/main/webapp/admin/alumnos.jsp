<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.AlumnoAdmin"%>

<%
    List<AlumnoAdmin> alumnos =
            (List<AlumnoAdmin>) request.getAttribute("alumnos");

    Integer totalAlumnos =
            (Integer) request.getAttribute("totalAlumnos");

    Integer alumnosActivos =
            (Integer) request.getAttribute("alumnosActivos");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Alumnos - Administración</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

</head>

<body class="bg-light">

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>

            <h2>
                👨‍🎓 Gestión de Alumnos
            </h2>

            <p class="text-muted">
                Administración de alumnos registrados.
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
                        Alumnos registrados
                    </h6>

                    <h2>
                        <%= totalAlumnos %>
                    </h2>

                </div>

            </div>

        </div>


        <div class="col-md-6">

            <div class="card shadow-sm">

                <div class="card-body">

                    <h6 class="text-muted">
                        Alumnos activos
                    </h6>

                    <h2>
                        <%= alumnosActivos %>
                    </h2>

                </div>

            </div>

        </div>

    </div>


    <div class="card shadow-sm mb-4">

        <div class="card-header">

            <strong>
                Registrar alumno
            </strong>

        </div>

        <div class="card-body">

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/alumnos">

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

                    <div class="col-md-2">

                        <label class="form-label">
                            Carnet
                        </label>

                        <input type="text"
                               name="carnet"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-3">

                        <label class="form-label">
                            Carrera
                        </label>

                        <input type="text"
                               name="carrera"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-2">

                        <label class="form-label">
                            Usuario
                        </label>

                        <input type="text"
                               name="usuario"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-1 d-flex align-items-end">

                        <button type="submit"
                                class="btn btn-primary w-100">

                            +

                        </button>

                    </div>

                </div>

            </form>

        </div>

    </div>


    <div class="card shadow-sm">

        <div class="card-header">

            <strong>
                Lista de alumnos
            </strong>

        </div>

        <div class="table-responsive">

            <table class="table table-hover mb-0">

                <thead class="table-dark">

                <tr>

                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Carnet</th>
                    <th>Carrera</th>
                    <th>Usuario</th>
                    <th>Estado</th>
                    <th>Acción</th>

                </tr>

                </thead>

                <tbody>

                <%
                    if (alumnos != null) {

                        for (AlumnoAdmin alumno : alumnos) {
                %>

                <tr>

                    <td>
                        <%= alumno.getId() %>
                    </td>

                    <td>
                        <%= alumno.getNombre() %>
                    </td>

                    <td>
                        <%= alumno.getCarnet() %>
                    </td>

                    <td>
                        <%= alumno.getCarrera() %>
                    </td>

                    <td>
                        <%= alumno.getUsuario() %>
                    </td>

                    <td>

                        <% if ("ACTIVO".equalsIgnoreCase(
                                alumno.getEstado())) { %>

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
                           href="${pageContext.request.contextPath}/admin/alumnos?accion=cambiarEstado&id=<%= alumno.getId() %>">

                            Cambiar

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

</body>

</html>