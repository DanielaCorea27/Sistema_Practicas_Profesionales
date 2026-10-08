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

    double horasRequeridas = 640;

    double horasAprobadas = 0;

    double horasTotales = 0;

    Object aprobadasObj =
            request.getAttribute("horasAprobadas");

    Object totalesObj =
            request.getAttribute("horasTotales");

    if (aprobadasObj instanceof Number) {
        horasAprobadas =
                ((Number) aprobadasObj).doubleValue();
    }

    if (totalesObj instanceof Number) {
        horasTotales =
                ((Number) totalesObj).doubleValue();
    }

    double horasRestantes =
            Math.max(
                    horasRequeridas - horasAprobadas,
                    0
            );

    double porcentaje =
            horasRequeridas > 0
            ? (horasAprobadas / horasRequeridas) * 100
            : 0;

    if (porcentaje > 100) {
        porcentaje = 100;
    }
%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta
        name="viewport"
        content="width=device-width, initial-scale=1">

    <title>
        Panel del Alumno | ITCA-FEPADE
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
            letter-spacing: .3px;
        }

        .sidebar {
            background: #ffffff;
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
            border: 0;
            border-radius: 18px;
            background: white;
            box-shadow: 0 5px 20px rgba(15,23,42,.06);
            transition: .2s;
        }

        .stat-card:hover {
            transform: translateY(-3px);
        }

        .stat-icon {
            width: 48px;
            height: 48px;
            border-radius: 12px;
            background: #eaf2fb;
            color: #123b6d;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 22px;
        }

        .stat-value {
            font-size: 28px;
            font-weight: 800;
            color: #123b6d;
        }

        .progress {
            height: 15px;
            border-radius: 20px;
        }

        .progress-bar {
            background: #123b6d;
        }

        .section-title {
            font-weight: 800;
            color: #123b6d;
        }

        .action-card {
            border: 0;
            border-radius: 18px;
            background: white;
            box-shadow: 0 5px 20px rgba(15,23,42,.06);
            height: 100%;
        }

        .action-icon {
            width: 55px;
            height: 55px;
            border-radius: 15px;
            background: #eaf2fb;
            color: #123b6d;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 25px;
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

        .profile-mini {
            color: white;
            font-size: 14px;
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
            href="<%=request.getContextPath()%>/alumno/dashboard">

            <i class="bi bi-mortarboard-fill"></i>

            ITCA-FEPADE

        </a>

        <div class="profile-mini">

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
                Mi cuenta
            </div>

            <a
                href="<%=request.getContextPath()%>/alumno/dashboard"
                class="active">

                <i class="bi bi-grid-1x2-fill"></i>

                Inicio

            </a>

            <a
                href="<%=request.getContextPath()%>/alumno/horas">

                <i class="bi bi-clock-history"></i>

                Mis horas

            </a>

            <a
                href="<%=request.getContextPath()%>/alumno/evidencias">

                <i class="bi bi-paperclip"></i>

                Evidencias

            </a>

            <a
                href="<%=request.getContextPath()%>/alumno/practica">

                <i class="bi bi-building"></i>

                Mi práctica

            </a>

            <div class="sidebar-title mt-4">
                Reportes
            </div>

            <a
                href="<%=request.getContextPath()%>/reporte/alumno">

                <i class="bi bi-bar-chart-line"></i>

                Mi reporte

            </a>

        </aside>

        <main class="col-lg-10 main-content">

            <div class="welcome mb-4">

                <div class="row align-items-center">

                    <div class="col-md-8">

                        <p class="mb-2 opacity-75">
                            Panel del estudiante
                        </p>

                        <h1 class="mb-2">
                            Hola, <%= nombre %> 👋
                        </h1>

                        <p class="mb-0 opacity-75">
                            Consulta y administra el progreso
                            de tus prácticas profesionales.
                        </p>

                    </div>

                    <div class="col-md-4 text-md-end mt-3 mt-md-0">

                        <span class="badge bg-light text-dark p-3">

                            <i class="bi bi-mortarboard"></i>

                            Ingeniería en Desarrollo de Software

                        </span>

                    </div>

                </div>

            </div>

            <div class="row g-4 mb-4">

                <div class="col-md-4">

                    <div class="card stat-card">

                        <div class="card-body p-4">

                            <div class="d-flex justify-content-between">

                                <div>

                                    <p class="text-muted mb-1">
                                        Horas requeridas
                                    </p>

                                    <div class="stat-value">
                                        <%= String.format("%.0f", horasRequeridas) %>
                                    </div>

                                </div>

                                <div class="stat-icon">
                                    <i class="bi bi-hourglass-split"></i>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="card stat-card">

                        <div class="card-body p-4">

                            <div class="d-flex justify-content-between">

                                <div>

                                    <p class="text-muted mb-1">
                                        Horas aprobadas
                                    </p>

                                    <div class="stat-value">
                                        <%= String.format("%.1f", horasAprobadas) %>
                                    </div>

                                </div>

                                <div class="stat-icon">
                                    <i class="bi bi-check-circle"></i>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="card stat-card">

                        <div class="card-body p-4">

                            <div class="d-flex justify-content-between">

                                <div>

                                    <p class="text-muted mb-1">
                                        Horas restantes
                                    </p>

                                    <div class="stat-value">
                                        <%= String.format("%.1f", horasRestantes) %>
                                    </div>

                                </div>

                                <div class="stat-icon">
                                    <i class="bi bi-hourglass"></i>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <div class="card stat-card mb-4">

                <div class="card-body p-4">

                    <div class="d-flex justify-content-between mb-3">

                        <div>

                            <h5 class="section-title mb-1">
                                Progreso de la práctica
                            </h5>

                            <small class="text-muted">
                                Avance de horas profesionales
                            </small>

                        </div>

                        <strong class="text-primary">
                            <%= String.format("%.1f", porcentaje) %>%
                        </strong>

                    </div>

                    <div class="progress">

                        <div
                            class="progress-bar"
                            style="width: <%= porcentaje %>%">

                        </div>

                    </div>

                    <div class="d-flex justify-content-between mt-2">

                        <small class="text-muted">
                            <%= String.format("%.1f", horasAprobadas) %>
                            horas aprobadas
                        </small>

                        <small class="text-muted">
                            <%= String.format("%.0f", horasRequeridas) %>
                            horas requeridas
                        </small>

                    </div>

                </div>

            </div>

            <h4 class="section-title mb-3">
                Accesos rápidos
            </h4>

            <div class="row g-4">

                <div class="col-md-6 col-xl-3">

                    <div class="card action-card">

                        <div class="card-body p-4">

                            <div class="action-icon">
                                <i class="bi bi-clock-history"></i>
                            </div>

                            <h5>
                                Mis horas
                            </h5>

                            <p>
                                Registra y consulta las horas
                                realizadas durante tu práctica.
                            </p>

                            <a
                                href="<%=request.getContextPath()%>/alumno/horas"
                                class="btn btn-primary btn-main">

                                Registrar horas

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-6 col-xl-3">

                    <div class="card action-card">

                        <div class="card-body p-4">

                            <div class="action-icon">
                                <i class="bi bi-paperclip"></i>
                            </div>

                            <h5>
                                Evidencias
                            </h5>

                            <p>
                                Consulta y gestiona las evidencias
                                de tu práctica profesional.
                            </p>

                            <a
                                href="<%=request.getContextPath()%>/alumno/evidencias"
                                class="btn btn-primary btn-main">

                                Ver evidencias

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-6 col-xl-3">

                    <div class="card action-card">

                        <div class="card-body p-4">

                            <div class="action-icon">
                                <i class="bi bi-building"></i>
                            </div>

                            <h5>
                                Mi práctica
                            </h5>

                            <p>
                                Consulta la empresa y los datos
                                de tu práctica profesional.
                            </p>

                            <a
                                href="<%=request.getContextPath()%>/alumno/practica"
                                class="btn btn-primary btn-main">

                                Ver práctica

                            </a>

                        </div>

                    </div>

                </div>

                <div class="col-md-6 col-xl-3">

                    <div class="card action-card">

                        <div class="card-body p-4">

                            <div class="action-icon">
                                <i class="bi bi-bar-chart-line"></i>
                            </div>

                            <h5>
                                Mi reporte
                            </h5>

                            <p>
                                Consulta el resumen de horas,
                                progreso y estado de tu práctica.
                            </p>

                            <a
                                href="<%=request.getContextPath()%>/reporte/alumno"
                                class="btn btn-primary btn-main">

                                Ver reporte

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