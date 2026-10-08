<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sv.edu.itca.practicas.model.Usuario"%>
<%@page import="sv.edu.itca.practicas.service.UsuarioAdminService"%>
<%@page import="sv.edu.itca.practicas.model.UsuarioAdmin"%>
<%@page import="java.util.List"%>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    UsuarioAdminService usuarioService =
            new UsuarioAdminService();

    List<UsuarioAdmin> usuarios =
            usuarioService.listarTodos();

    int totalUsuarios =
            usuarioService.contarTodos();

    int usuariosActivos =
            usuarioService.contarActivos();

    int alumnos = 0;
    int maestros = 0;

    for (UsuarioAdmin u : usuarios) {

        if ("ALUMNO".equalsIgnoreCase(u.getRol())) {
            alumnos++;
        }

        if ("MAESTRO".equalsIgnoreCase(u.getRol())) {
            maestros++;
        }
    }
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Dashboard Administrador</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilos.css">

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            background: #f5f7fb;
            font-family: Arial, Helvetica, sans-serif;
        }

        .navbar {
            min-height: 70px;
            background: #123b6d;
            color: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
        }

        .navbar-brand {
            font-size: 21px;
            font-weight: bold;
        }

        .usuario {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .logout {
            color: white;
            background: #dc3545;
            padding: 9px 15px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
        }

        .logout:hover {
            background: #bb2d3b;
        }

        .dashboard {
            padding: 35px;
            max-width: 1400px;
            margin: auto;
        }

        .bienvenida {
            margin-bottom: 30px;
        }

        .bienvenida h1 {
            margin: 0 0 8px 0;
            color: #123b6d;
        }

        .bienvenida p {
            color: #6c757d;
            font-size: 16px;
        }

        .estadisticas {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(230px, 1fr));
            gap: 20px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 16px;
            box-shadow: 0 4px 15px rgba(0,0,0,.06);
            transition: all .2s ease;
            text-decoration: none;
            color: inherit;
            display: block;
            border: 1px solid #eef1f5;
        }

        .stat-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 25px rgba(0,0,0,.10);
        }

        .stat-icon {
            font-size: 34px;
            margin-bottom: 12px;
        }

        .stat-number {
            font-size: 34px;
            font-weight: bold;
            color: #123b6d;
            margin: 8px 0;
        }

        .stat-title {
            color: #6c757d;
            margin: 0;
        }

        .stat-card h3 {
            margin: 5px 0;
            color: #212529;
        }

        .acciones {
            margin-top: 35px;
        }

        .acciones h2 {
            color: #123b6d;
            margin-bottom: 20px;
        }

        .menu-grid {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(250px, 1fr));
            gap: 18px;
        }

        .menu-card {
            background: white;
            border-radius: 15px;
            padding: 22px;
            text-decoration: none;
            color: #212529;
            box-shadow: 0 4px 15px rgba(0,0,0,.05);
            border: 1px solid #eef1f5;
            transition: .2s;
        }

        .menu-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 7px 20px rgba(0,0,0,.09);
        }

        .menu-card .icon {
            font-size: 30px;
            margin-bottom: 10px;
        }

        .menu-card h3 {
            margin: 5px 0;
            color: #123b6d;
        }

        .menu-card p {
            margin: 0;
            color: #6c757d;
        }

        .badge-activos {
            display: inline-block;
            margin-top: 8px;
            background: #d1e7dd;
            color: #0f5132;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: bold;
        }

    </style>

</head>

<body>

<nav class="navbar">

    <div class="navbar-brand">
        🎓 ITCA-FEPADE
    </div>

    <div class="usuario">

        👑

        <span>
            <%= usuario != null
                    ? usuario.getNombreCompleto()
                    : "Administrador" %>
        </span>

        <a class="logout"
           href="${pageContext.request.contextPath}/logout">
            Salir
        </a>

    </div>

</nav>


<main class="dashboard">

    <div class="bienvenida">

        <h1>
            Panel de Administración
        </h1>

        <p>

            Bienvenido,

            <strong>
                <%= usuario != null
                        ? usuario.getNombreCompleto()
                        : "Administrador" %>
            </strong>.

            Desde aquí puedes administrar el sistema.

        </p>

    </div>


    <div class="estadisticas">


        <a href="${pageContext.request.contextPath}/admin/usuarios"
           class="stat-card">

            <div class="stat-icon">
                👥
            </div>

            <div class="stat-number">
                <%= totalUsuarios %>
            </div>

            <h3>
                Usuarios registrados
            </h3>

            <p class="stat-title">
                Administrar usuarios del sistema
            </p>

            <span class="badge-activos">
                <%= usuariosActivos %> activos
            </span>

        </a>


        <a href="${pageContext.request.contextPath}/admin/alumnos"
           class="stat-card">

            <div class="stat-icon">
                👨‍🎓
            </div>

            <div class="stat-number">
                <%= alumnos %>
            </div>

            <h3>
                Alumnos
            </h3>

            <p class="stat-title">
                Alumnos registrados
            </p>

        </a>


        <a href="${pageContext.request.contextPath}/admin/maestros"
           class="stat-card">

            <div class="stat-icon">
                👨‍🏫
            </div>

            <div class="stat-number">
                <%= maestros %>
            </div>

            <h3>
                Maestros
            </h3>

            <p class="stat-title">
                Personal docente
            </p>

        </a>


        <a href="${pageContext.request.contextPath}/admin/empresas"
           class="stat-card">

            <div class="stat-icon">
                🏢
            </div>

            <h3>
                Empresas
            </h3>

            <p class="stat-title">
                Administrar empresas para prácticas profesionales.
            </p>

        </a>


        <a href="${pageContext.request.contextPath}/reporte/general"
           class="stat-card">

            <div class="stat-icon">
                📊
            </div>

            <h3>
                Reportes
            </h3>

            <p class="stat-title">
                Consultar estadísticas generales de las prácticas profesionales.
            </p>

        </a>

    </div>


    <div class="acciones">

        <h2>
            Acciones administrativas
        </h2>

        <div class="menu-grid">


            <a href="${pageContext.request.contextPath}/admin/usuarios"
               class="menu-card">

                <div class="icon">
                    👥
                </div>

                <h3>
                    Gestión de usuarios
                </h3>

                <p>
                    Crear usuarios y activar o desactivar cuentas.
                </p>

            </a>


            <a href="${pageContext.request.contextPath}/maestro/horas"
               class="menu-card">

                <div class="icon">
                    ⏱️
                </div>

                <h3>
                    Registros de horas
                </h3>

                <p>
                    Revisar registros de prácticas profesionales.
                </p>

            </a>


            <a href="${pageContext.request.contextPath}/maestro/evidencias"
               class="menu-card">

                <div class="icon">
                    📎
                </div>

                <h3>
                    Evidencias
                </h3>

                <p>
                    Revisar evidencias enviadas por los alumnos.
                </p>

            </a>


            <a href="${pageContext.request.contextPath}/admin/empresas"
               class="menu-card">

                <div class="icon">
                    🏢
                </div>

                <h3>
                    Empresas
                </h3>

                <p>
                    Administrar empresas disponibles.
                </p>

            </a>


            <a href="${pageContext.request.contextPath}/reporte/general"
               class="menu-card">

                <div class="icon">
                    📊
                </div>

                <h3>
                    Reportes
                </h3>

                <p>
                    Consultar estadísticas generales de las prácticas profesionales.
                </p>

            </a>

        </div>

    </div>

</main>

</body>

</html>