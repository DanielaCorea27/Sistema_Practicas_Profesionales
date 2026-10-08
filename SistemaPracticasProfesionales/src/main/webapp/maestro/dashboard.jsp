<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="sv.edu.itca.practicas.model.Usuario" %>

<%
    Usuario usuario =
            (Usuario) session.getAttribute("usuario");

    if (usuario == null) {
        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );
        return;
    }

    String nombre =
            usuario.getNombreCompleto();

    int totalRegistros =
            request.getAttribute("totalRegistros") != null
            ? ((Number) request.getAttribute("totalRegistros")).intValue()
            : 0;

    int pendientes =
            request.getAttribute("pendientes") != null
            ? ((Number) request.getAttribute("pendientes")).intValue()
            : 0;

    int aprobados =
            request.getAttribute("aprobados") != null
            ? ((Number) request.getAttribute("aprobados")).intValue()
            : 0;

    int rechazados =
            request.getAttribute("rechazados") != null
            ? ((Number) request.getAttribute("rechazados")).intValue()
            : 0;

    double horasTotales =
            request.getAttribute("horasTotales") != null
            ? ((Number) request.getAttribute("horasTotales")).doubleValue()
            : 0;

    double horasAprobadas =
            request.getAttribute("horasAprobadas") != null
            ? ((Number) request.getAttribute("horasAprobadas")).doubleValue()
            : 0;

    double horasPendientes =
            request.getAttribute("horasPendientes") != null
            ? ((Number) request.getAttribute("horasPendientes")).doubleValue()
            : 0;
%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta
        name="viewport"
        content="width=device-width, initial-scale=1">

    <title>
        Panel del Maestro | ITCA-FEPADE
    </title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
        rel="stylesheet">

    <style>

        body {
            background: #f5f7fb;
            color: #1e293b;
        }

        .navbar {
            background: #123b6d;
            box-shadow: 0 3px 15px rgba(0,0,0,.12);
        }

        .navbar-brand {
            font-weight: 800;
        }

        .sidebar {
            background: white;
            min-height: calc(100vh - 56px);
            border-right: 1px solid #e5e7eb;
            padding: 25px 15px;
        }

        .sidebar-title {
            font-size: 12px;
            color: #94a3b8;
            text-transform: uppercase;
            font-weight: 700;
            margin: 10px 15px;
        }

        .sidebar a {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 15px;
            border-radius: 10px;
            text-decoration: none;
            color: #475569;
            margin-bottom: 5px;
            transition: .2s;
        }

        .sidebar a:hover,
        .sidebar a.active {
            background: #eaf2fb;
            color: #123b6d;
        }

        .main-content {
            padding: 30px;
        }

        .welcome {
            background:
                linear-gradient(
                    135deg,
                    #123b6d,
                    #1d6fa5
                );
            color: white;
            border-radius: 20px;
            padding: 32px;
            box-shadow: 0 10px 30px rgba(18,59,109,.18);
        }

        .welcome h1 {
            font-weight: 800;
        }

        .stat-card {
            border: none;
            border-radius: 18px;
            background: white;
            box-shadow: 0 5px 20px rgba(15,23,42,.06);
            transition: .2s;
        }

        .stat-card:hover {
            transform: translateY(-3px);
        }

        .stat-icon {
            width: 50px;
            height: 50px;
            border-radius: 13px;
            background: #eaf2fb;
            color: #123b6d;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 23px;
        }

        .stat-value {
            font-size: 30px;
            font-weight: 800;
            color: #123b6d;
        }

        .section-title {
            font-weight: 800;
            color: #123b6d;
        }

        .action-card {
            border: none;
            border-radius: 18px;
            background: white;
            box-shadow: 0 5px 20px rgba(15,23,42,.06);
            height: 100%;
        }

        .action-icon {
            width: 58px;
            height: 58px;
            border-radius: 15px;
            background: #eaf2fb;
            color: #123b6d;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 26px;
            margin-bottom: 18px;
        }

        .action-card h5 {
            font-weight: 700;
        }

        .action-card p {
            color: #64748b;
            min-height: 50px;
        }

        .btn-main {
            background: #123b6d;
            border: none;
            border-radius: 10px;
            padding: 10px 18px;
            font-weight: 600;
        }

        .btn-main:hover {
            background: #0b2545;
        }

        .role-badge {
            background: rgba(255,255,255,.15);
            padding: 9px 14px;
            border-radius: 20px;
        }

        @media (max-width: 991px) {

            .sidebar {
                min-height: auto;
                border-right: 0;
                border-bottom: 1px solid #e5e7eb;
            }

            .main-content {
                padding: 20px;
            }

        }

    </style>

</head>

<body>

<nav class="navbar navbar-dark">

    <div class="container-fluid px-4">

        <a
            class="navbar-brand"
            href="<%=request.getContextPath()%>/maestro/dashboard">

            <i class="bi bi-mortarboard-fill"></i>

            ITCA-FEPADE

        </a>

        <div class="text-white">

            <i class="bi bi-person-circle"></i>

            <%= nombre %>

            <a
                href="<%=request.getContextPath()%>/logout"
                class="btn btn-sm btn-outline-light ms-3">

                Cerrar sesión

            </a>

        </div>

    </div>

</nav>

<div class="container-fluid">

    <div class="row">

        <aside class="col-lg-2 sidebar">

            <div class="sidebar-title">
                Gestión académica
            </div>

            <a
                href="<%=request.getContextPath()%>/maestro/dashboard"
                class="active">

                <i class="bi bi-grid-1x2-fill"></i>

                Inicio

            </a>

            <a
                href="<%=request.getContextPath()%>/maestro/horas">

                <i class="bi bi-clock-history"></i>

                Registros de horas

            </a>

            <a
                href="<%=request.getContextPath()%>/maestro/evidencias">

                <i class="bi bi-paperclip"></i>

                Evidencias

            </a>

            <div class="sidebar-title mt-4">
                Información
            </div>

            <a
                href="<%=request.getContextPath()%>/reporte/general">

                <i class="bi bi-bar-chart-line"></i>

                Reportes

            </a>

        </aside>

        <main class="col-lg-10 main-content">

            <div class="welcome mb-4">

                <div class="row align-items-center">

                    <div class="col-md-8">

                        <p class="mb-2 opacity-75">
                            Panel docente
                        </p>

                        <h1 class="mb-2">
                            Bienvenido, <%= nombre %> 👋
                        </h1>

                        <p class="mb-0 opacity-75">
                            Administra y supervisa el seguimiento
                            de las prácticas profesionales.
                        </p>

                    </div>

                    <div class="col-md-4 text-md-end mt-3 mt-md-0">

                        <span class="role-badge">

                            <i class="bi bi-person-badge"></i>

                            Maestro

                        </span>

                    </div>

                </div>

            </div>

            <div class="row g-4 mb-4">

                <div class="col-md-6 col-xl-3">

                    <div class="card stat-card h-100">

                        <div class="card-body p-4">

                            <div class="d-flex justify-content-between">

                                <div>

                                    <p class="text-muted mb-1">
                                        Registros
                                    </p>

                                    <div class="stat-value">
                                        <%= totalRegistros %>
                                    </div>

                                </div>

                                <div class="stat-icon">
                                    <i class="bi bi-list-check"></i>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-6 col-xl-3">

                    <div class="card stat-card h-100">

                        <div class="card-body p-4">

                            <div class="d-flex justify-content-between">

                                <div>

                                    <p class="text-muted mb-1">
                                        Pendientes
                                    </p>

                                    <div class="stat-value">
                                        <%= pendientes %>
                                    </div>

                                </div>

                                <div class="stat-icon">
                                    <i class="bi bi-hourglass-split"></i>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-6 col-xl-3">

                    <div class="card stat-card h-100">

                        <div class="card-body p-4">

                            <div class="d-flex justify-content-between">

                                <div>

                                    <p class="text-muted mb-1">
                                        Aprobados
                                    </p>

                                    <div class="stat-value">
                                        <%= aprobados %>
                                    </div>

                                </div>

                                <div class="stat-icon">
                                    <i class="bi bi-check-circle"></i>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-6 col-xl-3">

                    <div class="card stat-card h-100">

                        <div class="card-body p-4">

                            <div class="d-flex justify-content-between">

                                <div>

                                    <p class="text-muted mb-1">
                                        Rechazados
                                    </p>

                                    <div class="stat-value">
                                        <%= rechazados %>
                                    </div>

                                </div>

                                <div class="stat-icon">
                                    <i class="bi bi-x-circle"></i>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <div class="row g-4 mb-4">

                <div class="col-md-4">

                    <div class="card stat-card">

                        <div class="card-body p-4">

                            <p class="text-muted mb-1">
                                Horas registradas
                            </p>

                            <div class="stat-value">
                                <%= String.format("%.1f", horasTotales) %>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="card stat-card">

                        <div class="card-body p-4">

                            <p class="text-muted mb-1">
                                Horas aprobadas
                            </p>

                            <div class="stat-value">
                                <%= String.format("%.1f", horasAprobadas) %>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="card stat-card">

                        <div class="card-body p-4">

                            <p class="text-muted mb-1">
                                Horas pendientes
                            </p>

                            <div class="stat-value">
                                <%= String.format("%.1f", horasPendientes) %>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <h4 class="section-title mb-3">
                Gestión de prácticas
            </h4>

            <div class="row g-4">

                <div class="col-md-6">

                    <div class="card action-card">

                        <div class="card-body p-4">

                            <div class="action-icon">
                                <i class="bi bi-clock-history"></i>
                            </div>

                            <h5>
                                Registros de horas
                            </h5>

                            <p>
                                Revisa los registros enviados
                                por los estudiantes y aprueba
                                o rechaza sus horas.
                            </p>

                            <a
                                href="<%=request.getContextPath()%>/maestro/horas"
                                class="btn btn-primary btn-main">

                                Revisar registros

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-6">

                    <div class="card action-card">

                        <div class="card-body p-4">

                            <div class="action-icon">
                                <i class="bi bi-paperclip"></i>
                            </div>

                            <h5>
                                Evidencias
                            </h5>

                            <p>
                                Consulta las evidencias enviadas
                                por los estudiantes y gestiona
                                su estado.
                            </p>

                            <a
                                href="<%=request.getContextPath()%>/maestro/evidencias"
                                class="btn btn-primary btn-main">

                                Revisar evidencias

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-6">

                    <div class="card action-card">

                        <div class="card-body p-4">

                            <div class="action-icon">
                                <i class="bi bi-bar-chart-line"></i>
                            </div>

                            <h5>
                                Reportes generales
                            </h5>

                            <p>
                                Consulta estadísticas generales
                                de las prácticas profesionales.
                            </p>

                            <a
                                href="<%=request.getContextPath()%>/reporte/general"
                                class="btn btn-primary btn-main">

                                Ver reportes

                            </a>

                        </div>

                    </div>

                </div>

            </div>

        </main>

    </div>

</div>

</body>

</html>