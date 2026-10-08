<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.UsuarioAdmin"%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Usuarios | Administración</title>

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
            href="<%=request.getContextPath()%>/admin/dashboard.jsp">

            ITCA-FEPADE

        </a>

        <a
            href="<%=request.getContextPath()%>/admin/dashboard.jsp"
            class="btn btn-outline-light btn-sm">

            Dashboard

        </a>

    </div>

</nav>

<div class="container py-4">

    <div class="d-flex justify-content-between
                align-items-center mb-4">

        <div>

            <h2 class="fw-bold mb-1">
                Gestión de usuarios
            </h2>

            <p class="text-muted mb-0">
                Administración de usuarios y roles del sistema.
            </p>

        </div>

    </div>


    <div class="row g-4 mb-4">

        <div class="col-md-6">

            <div class="card p-4">

                <div class="text-muted">
                    Usuarios registrados
                </div>

                <div class="fs-2 fw-bold">

                    <%=request.getAttribute("totalUsuarios")%>

                </div>

            </div>

        </div>


        <div class="col-md-6">

            <div class="card p-4">

                <div class="text-muted">
                    Usuarios activos
                </div>

                <div class="fs-2 fw-bold text-success">

                    <%=request.getAttribute("usuariosActivos")%>

                </div>

            </div>

        </div>

    </div>


    <div class="card p-4 mb-4">

        <h5 class="fw-bold mb-3">
            Crear usuario
        </h5>

        <form
            method="POST"
            action="<%=request.getContextPath()%>/admin/usuarios">

            <div class="row">

                <div class="col-md-4 mb-3">

                    <label class="form-label">
                        Nombre
                    </label>

                    <input
                        type="text"
                        name="nombre"
                        class="form-control"
                        required>

                </div>

                <div class="col-md-4 mb-3">

                    <label class="form-label">
                        Usuario
                    </label>

                    <input
                        type="text"
                        name="usuario"
                        class="form-control"
                        required>

                </div>

                <div class="col-md-4 mb-3">

                    <label class="form-label">
                        Rol
                    </label>

                    <select
                        name="rol"
                        class="form-select"
                        required>

                        <option value="">
                            Seleccionar
                        </option>

                        <option value="ALUMNO">
                            Alumno
                        </option>

                        <option value="MAESTRO">
                            Maestro
                        </option>

                        <option value="ADMIN">
                            Administrador
                        </option>

                    </select>

                </div>

            </div>

            <button
                type="submit"
                class="btn btn-primary btn-rounded">

                Crear usuario

            </button>

        </form>

    </div>


    <div class="card">

        <div class="card-header bg-white p-3">

            <h5 class="mb-0">
                Usuarios registrados
            </h5>

        </div>

        <div class="card-body">

            <div class="table-responsive">

                <table class="table table-hover align-middle">

                    <thead>

                        <tr>

                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Usuario</th>
                            <th>Rol</th>
                            <th>Estado</th>
                            <th>Acción</th>

                        </tr>

                    </thead>

                    <tbody>

                    <%
                        List<UsuarioAdmin> usuarios =
                                (List<UsuarioAdmin>)
                                request.getAttribute("usuarios");

                        if (usuarios != null
                                && !usuarios.isEmpty()) {

                            for (UsuarioAdmin usuario : usuarios) {
                    %>

                        <tr>

                            <td>
                                <%=usuario.getId()%>
                            </td>

                            <td>
                                <strong>
                                    <%=usuario.getNombre()%>
                                </strong>
                            </td>

                            <td>
                                <%=usuario.getUsuario()%>
                            </td>

                            <td>

                                <span class="badge bg-primary">

                                    <%=usuario.getRol()%>

                                </span>

                            </td>

                            <td>

                                <% if ("ACTIVO".equalsIgnoreCase(
                                        usuario.getEstado())) { %>

                                    <span class="badge bg-success">
                                        ACTIVO
                                    </span>

                                <% } else { %>

                                    <span class="badge bg-danger">
                                        INACTIVO
                                    </span>

                                <% } %>

                            </td>

                            <td>

                                <a
                                    href="<%=request.getContextPath()%>/admin/usuarios?accion=cambiarEstado&id=<%=usuario.getId()%>"
                                    class="btn btn-outline-secondary btn-sm">

                                    Cambiar estado

                                </a>

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

                                No hay usuarios registrados.

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