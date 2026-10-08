<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.EmpresaAdmin"%>

<%
    List<EmpresaAdmin> empresas =
            (List<EmpresaAdmin>) request.getAttribute("empresas");

    Integer totalEmpresas =
            (Integer) request.getAttribute("totalEmpresas");

    Integer empresasActivas =
            (Integer) request.getAttribute("empresasActivas");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Empresas</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

</head>

<body class="bg-light">

<div class="container py-4">

    <div class="d-flex justify-content-between mb-4">

        <div>

            <h2>
                🏢 Empresas
            </h2>

            <p class="text-muted">
                Empresas disponibles para prácticas profesionales.
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

                    <small class="text-muted">
                        Empresas registradas
                    </small>

                    <h2>
                        <%= totalEmpresas %>
                    </h2>

                </div>

            </div>

        </div>

        <div class="col-md-6">

            <div class="card shadow-sm">

                <div class="card-body">

                    <small class="text-muted">
                        Empresas activas
                    </small>

                    <h2>
                        <%= empresasActivas %>
                    </h2>

                </div>

            </div>

        </div>

    </div>


    <div class="card shadow-sm mb-4">

        <div class="card-header">

            <strong>
                Registrar empresa
            </strong>

        </div>

        <div class="card-body">

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/empresas">

                <div class="row g-3">

                    <div class="col-md-3">

                        <label class="form-label">
                            Empresa
                        </label>

                        <input type="text"
                               name="nombre"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-3">

                        <label class="form-label">
                            Contacto
                        </label>

                        <input type="text"
                               name="contacto"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-2">

                        <label class="form-label">
                            Teléfono
                        </label>

                        <input type="text"
                               name="telefono"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-3">

                        <label class="form-label">
                            Correo
                        </label>

                        <input type="email"
                               name="correo"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-1 d-flex align-items-end">

                        <button class="btn btn-primary w-100"
                                type="submit">

                            +

                        </button>

                    </div>

                </div>

            </form>

        </div>

    </div>


    <div class="card shadow-sm">

        <div class="table-responsive">

            <table class="table table-hover mb-0">

                <thead class="table-dark">

                <tr>

                    <th>ID</th>
                    <th>Empresa</th>
                    <th>Contacto</th>
                    <th>Teléfono</th>
                    <th>Correo</th>
                    <th>Estado</th>
                    <th>Acción</th>

                </tr>

                </thead>

                <tbody>

                <%
                    if (empresas != null) {

                        for (EmpresaAdmin empresa : empresas) {
                %>

                <tr>

                    <td>
                        <%= empresa.getId() %>
                    </td>

                    <td>
                        <%= empresa.getNombre() %>
                    </td>

                    <td>
                        <%= empresa.getContacto() %>
                    </td>

                    <td>
                        <%= empresa.getTelefono() %>
                    </td>

                    <td>
                        <%= empresa.getCorreo() %>
                    </td>

                    <td>

                        <% if ("ACTIVO".equalsIgnoreCase(
                                empresa.getEstado())) { %>

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
                           href="${pageContext.request.contextPath}/admin/empresas?accion=cambiarEstado&id=<%= empresa.getId() %>">

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