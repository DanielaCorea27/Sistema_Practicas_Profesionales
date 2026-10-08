<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sv.edu.itca.practicas.model.Usuario"%>
<%@page import="sv.edu.itca.practicas.model.Carrera"%>
<%@page import="java.util.List"%>

<%
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

    @SuppressWarnings("unchecked")
    List<Carrera> carreras = (List<Carrera>) request.getAttribute("carreras");

    Integer totalCarrerasObj = (Integer) request.getAttribute("totalCarreras");
    int totalCarreras = totalCarrerasObj != null ? totalCarrerasObj : (carreras != null ? carreras.size() : 0);
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Gestión de Carreras | ITCA-FEPADE
    </title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>

        body {
            background: #f4f6f9;
            font-family: Arial, sans-serif;
        }

        .navbar-custom {
            background: #111827;
        }

        .brand {
            color: white;
            font-size: 21px;
            font-weight: bold;
            text-decoration: none;
        }

        .navbar-text-custom {
            color: #d1d5db;
        }

        .dashboard-container {
            padding: 35px;
        }

        .welcome {
            margin-bottom: 25px;
        }

        .welcome h1 {
            font-size: 28px;
            font-weight: 700;
            color: #111827;
        }

        .welcome p {
            color: #6b7280;
        }

        .stat-card {
            background: white;
            border: 1px solid #e5e7eb;
            border-radius: 18px;
            padding: 20px;
            height: 100%;
            box-shadow: 0 8px 25px rgba(0,0,0,0.05);
            transition: 0.2s;
        }

        .icon-box {
            width: 48px;
            height: 48px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 22px;
            margin-bottom: 12px;
        }

        .icon-purple {
            background: #f3e8ff;
        }

        .stat-title {
            color: #6b7280;
            font-size: 13px;
            margin-bottom: 4px;
        }

        .stat-number {
            color: #111827;
            font-size: 28px;
            font-weight: bold;
        }

        .section-card {
            background: white;
            border: 1px solid #e5e7eb;
            border-radius: 18px;
            padding: 25px;
            margin-top: 25px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.05);
        }

        .table-custom th {
            background-color: #f9fafb;
            color: #374151;
            font-weight: 600;
            font-size: 14px;
            border-bottom: 2px solid #e5e7eb;
        }

        .table-custom td {
            vertical-align: middle;
            font-size: 14px;
            color: #1f2937;
        }

        footer {
            text-align: center;
            padding: 30px;
            color: #9ca3af;
            font-size: 13px;
        }

        @media (max-width: 768px) {
            .dashboard-container {
                padding: 15px;
            }
        }

    </style>

</head>

<body>

<!-- =====================================================
     NAVBAR
     ===================================================== -->

<nav class="navbar navbar-dark navbar-custom">

    <div class="container-fluid px-4">

        <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">
            🎓 ITCA-FEPADE
        </a>

        <div class="d-flex align-items-center gap-3">

            <span class="navbar-text-custom">
                👑 <%= usuarioSesion != null ? usuarioSesion.getNombreCompleto() : "Administrador" %>
            </span>

            <a href="${pageContext.request.contextPath}/logout"
               class="btn btn-outline-light btn-sm">
                Cerrar sesión
            </a>

        </div>

    </div>

</nav>


<!-- =====================================================
     CONTENIDO PRINCIPAL
     ===================================================== -->

<div class="container-fluid dashboard-container">

    <!-- CABECERA Y NAVEGACIÓN -->

    <div class="d-flex justify-content-between align-items-center flex-wrap gap-3 welcome">

        <div>
            <h1>
                🎓 Gestión de Carreras
            </h1>

            <p class="mb-0">
                Administración de carreras académicas y asignación de horas profesionales requeridas.
            </p>
        </div>

        <div>

            <a href="${pageContext.request.contextPath}/admin/dashboard"
               class="btn btn-outline-secondary">
                ⬅️ Volver al Dashboard
            </a>

        </div>

    </div>


    <!-- =================================================
         TARJETAS DE RESUMEN Y FORMULARIO DE REGISTRO
         ================================================= -->

    <div class="row g-4 mb-2">

        <!-- TARJETA DE MÉTRICA -->

        <div class="col-md-4 col-lg-3">

            <div class="stat-card">

                <div class="icon-box icon-purple">
                    🎓
                </div>

                <div class="stat-title">
                    Carreras Registradas
                </div>

                <div class="stat-number">
                    <%= totalCarreras %>
                </div>

            </div>

        </div>

        <!-- FORMULARIO NUEVA CARRERA -->

        <div class="col-md-8 col-lg-9">

            <div class="stat-card">

                <h6 class="fw-bold mb-3 text-dark">
                    ➕ Registrar Nueva Carrera
                </h6>

                <form method="post" action="${pageContext.request.contextPath}/admin/carreras">

                    <div class="row g-3">

                        <div class="col-md-7">

                            <label class="form-label text-secondary small fw-semibold">
                                Nombre de la carrera
                            </label>

                            <input type="text"
                                   name="nombre"
                                   class="form-control"
                                   placeholder="Ej. Técnico en Ingeniería de Desarrollo de Software"
                                   required>

                        </div>

                        <div class="col-md-3">

                            <label class="form-label text-secondary small fw-semibold">
                                Horas requeridas
                            </label>

                            <input type="number"
                                   name="horas"
                                   class="form-control"
                                   placeholder="Ej. 500"
                                   min="1"
                                   required>

                        </div>

                        <div class="col-md-2 d-flex align-items-end">

                            <button type="submit" class="btn btn-primary w-100">
                                Registrar
                            </button>

                        </div>

                    </div>

                </form>

            </div>

        </div>

    </div>


    <!-- =================================================
         TABLA DE CARRERAS
         ================================================= -->

    <div class="section-card">

        <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-4">

            <h5 class="fw-bold mb-0 text-dark">
                Listado de Carreras
            </h5>

            <!-- BUSCADOR RÁPIDO -->
            <div class="col-md-4 col-12">
                <input type="text" 
                       id="inputBuscar" 
                       class="form-control form-control-sm" 
                       placeholder="🔍 Buscar por nombre de carrera...">
            </div>

        </div>

        <div class="table-responsive">

            <table class="table table-hover align-middle table-custom" id="tablaCarreras">

                <thead>

                    <tr>
                        <th>ID</th>
                        <th>Carrera</th>
                        <th>Horas Requeridas</th>
                        <th class="text-end">Acciones</th>
                    </tr>

                </thead>

                <tbody>

                    <%
                        if (carreras != null && !carreras.isEmpty()) {
                            for (Carrera carrera : carreras) {
                    %>

                    <tr>

                        <td class="fw-bold text-secondary">
                            #<%= carrera.getId() %>
                        </td>

                        <td>
                            <div class="fw-semibold">
                                <%= carrera.getNombre() %>
                            </div>
                        </td>

                        <td>
                            <span class="badge bg-light text-dark border px-2 py-1 fs-6 fw-normal">
                                ⏱️ <strong><%= carrera.getHorasRequeridas() %></strong> hrs
                            </span>
                        </td>

                        <td class="text-end">

                            <a class="btn btn-sm btn-outline-danger"
                               href="${pageContext.request.contextPath}/admin/carreras?accion=eliminar&id=<%= carrera.getId() %>"
                               onclick="return confirm('¿Está seguro de eliminar esta carrera?');"
                               title="Eliminar carrera">
                                🗑️ Eliminar
                            </a>

                        </td>

                    </tr>

                    <%
                            }
                        } else {
                    %>

                    <tr>
                        <td colspan="4" class="text-center py-4 text-muted">
                            🚫 No se encontraron carreras registradas en el sistema.
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


<!-- =====================================================
     FOOTER
     ===================================================== -->

<footer>

    Sistema de Gestión de Prácticas Profesionales<br>

    ITCA-FEPADE © 2026

</footer>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Filtro interactivo para la tabla de carreras
    document.getElementById('inputBuscar').addEventListener('keyup', function() {
        let filtro = this.value.toLowerCase();
        let filas = document.querySelectorAll('#tablaCarreras tbody tr');

        filas.forEach(fila => {
            let texto = fila.textContent.toLowerCase();
            fila.style.display = texto.includes(filtro) ? '' : 'none';
        });
    });
</script>

</body>

</html>