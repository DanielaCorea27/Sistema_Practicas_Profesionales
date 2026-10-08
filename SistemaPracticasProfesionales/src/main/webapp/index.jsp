<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="sv.edu.itca.practicas.model.Rol" %>
<%@ page import="sv.edu.itca.practicas.model.Usuario" %>
<%
    String ctx = request.getContextPath();

    // Si ya inició sesión, el botón principal lleva a su panel.
    Usuario u = (Usuario) session.getAttribute("usuario");
    String panel = null;

    if (u != null && u.getRol() != null) {

        switch (u.getRol()) {
            case ADMINISTRADOR: panel = ctx + "/admin/dashboard.jsp"; break;
            case MAESTRO:       panel = ctx + "/maestro/dashboard";   break;
            case ALUMNO:        panel = ctx + "/alumno/dashboard";    break;
            case EMPRESA:       panel = ctx + "/empresa/dashboard";   break;
        }
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sistema de Pasantías | ITCA-FEPADE</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">

    <style>
        body { font-family: Arial, sans-serif; background: #f4f7fb; }

        .hero {
            background: linear-gradient(135deg, #0b2545 0%, #123b6d 50%, #1d6fa5 100%);
            color: #fff;
            padding: 90px 0 110px;
        }

        .hero h1 { font-weight: 800; font-size: 46px; }
        .hero p.lead { color: rgba(255,255,255,.85); max-width: 640px; }

        .hero-icon {
            width: 84px; height: 84px; border-radius: 22px;
            background: rgba(255,255,255,.12);
            display: flex; align-items: center; justify-content: center;
            font-size: 42px; margin-bottom: 24px;
        }

        .btn-hero {
            border-radius: 14px; padding: 13px 28px; font-weight: 700;
        }

        .horas-box {
            background: rgba(255,255,255,.10);
            border-radius: 18px; padding: 22px 26px;
        }

        .horas-box .num { font-size: 40px; font-weight: 800; line-height: 1; }

        .rol-card {
            border: 0; border-radius: 18px; height: 100%;
            box-shadow: 0 8px 25px rgba(18, 59, 109, .10);
        }

        .rol-icon {
            width: 54px; height: 54px; border-radius: 16px; color: #fff;
            display: flex; align-items: center; justify-content: center;
            font-size: 26px; margin-bottom: 14px;
        }

        .seccion-titulo { color: #123b6d; font-weight: 800; }

        footer { color: #6c7a89; }
    </style>
</head>
<body>

<section class="hero">
    <div class="container-xl">
        <div class="row align-items-center g-5">

            <div class="col-lg-7">

                <div class="hero-icon"><i class="bi bi-mortarboard-fill"></i></div>

                <h1>Sistema de Pasantías</h1>
                <h4 class="mb-3">ITCA-FEPADE</h4>

                <p class="lead mb-4">
                    Plataforma para gestionar y dar seguimiento a las pasantías
                    profesionales de los estudiantes: oportunidades, postulaciones,
                    horas realizadas, evidencias y evaluaciones.
                </p>

                <div class="d-flex flex-wrap gap-3">

                    <% if (panel != null) { %>

                        <a href="<%= panel %>" class="btn btn-light btn-hero text-primary">
                            <i class="bi bi-speedometer2"></i> Ir a mi panel
                        </a>

                    <% } else { %>

                        <a href="<%= ctx %>/login.jsp" class="btn btn-light btn-hero text-primary">
                            <i class="bi bi-box-arrow-in-right"></i> Iniciar sesión
                        </a>

                        <a href="<%= ctx %>/registro" class="btn btn-outline-light btn-hero">
                            <i class="bi bi-person-plus"></i> Crear cuenta
                        </a>

                    <% } %>

                </div>
            </div>

            <div class="col-lg-5">
                <div class="horas-box">
                    <div class="small text-uppercase mb-3" style="opacity:.75;">
                        Horas de pasantía obligatorias
                    </div>
                    <div class="row text-center g-3">
                        <div class="col-6">
                            <div class="num">320</div>
                            <div class="small">Técnicos</div>
                        </div>
                        <div class="col-6">
                            <div class="num">640</div>
                            <div class="small">Ingenierías</div>
                        </div>
                    </div>
                    <div class="small mt-3" style="opacity:.8;">
                        Se pueden completar en una o en dos empresas.
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>

<section class="py-5" style="margin-top:-50px;">
    <div class="container-xl">

        <div class="row g-4">

            <div class="col-md-6 col-xl-3">
                <div class="card rol-card"><div class="card-body p-4">
                    <div class="rol-icon" style="background:#2f9e44;"><i class="bi bi-mortarboard"></i></div>
                    <h5 class="fw-bold">Estudiantes</h5>
                    <p class="text-muted mb-0">
                        Consultan oportunidades, se postulan, registran sus actividades
                        y ven su avance de horas.
                    </p>
                </div></div>
            </div>

            <div class="col-md-6 col-xl-3">
                <div class="card rol-card"><div class="card-body p-4">
                    <div class="rol-icon" style="background:#1d6fa5;"><i class="bi bi-building"></i></div>
                    <h5 class="fw-bold">Empresas</h5>
                    <p class="text-muted mb-0">
                        Publican oportunidades, aceptan estudiantes, dan seguimiento
                        y realizan la evaluación final.
                    </p>
                </div></div>
            </div>

            <div class="col-md-6 col-xl-3">
                <div class="card rol-card"><div class="card-body p-4">
                    <div class="rol-icon" style="background:#f08c00;"><i class="bi bi-person-check"></i></div>
                    <h5 class="fw-bold">Tutores ITCA</h5>
                    <p class="text-muted mb-0">
                        Aprueban oportunidades, revisan las actividades y supervisan
                        académicamente cada pasantía.
                    </p>
                </div></div>
            </div>

            <div class="col-md-6 col-xl-3">
                <div class="card rol-card"><div class="card-body p-4">
                    <div class="rol-icon" style="background:#7048e8;"><i class="bi bi-gear"></i></div>
                    <h5 class="fw-bold">Administración</h5>
                    <p class="text-muted mb-0">
                        Gestiona usuarios, carreras, empresas y preguntas de evaluación,
                        y consulta los reportes.
                    </p>
                </div></div>
            </div>

        </div>

        <h3 class="seccion-titulo text-center mt-5 mb-4">¿Cómo funciona?</h3>

        <div class="row g-3 text-center">
            <div class="col-6 col-md"><div class="fw-bold text-primary">1. Publica</div><div class="small text-muted">La empresa publica una oportunidad</div></div>
            <div class="col-6 col-md"><div class="fw-bold text-primary">2. Aprueba</div><div class="small text-muted">El tutor la revisa</div></div>
            <div class="col-6 col-md"><div class="fw-bold text-primary">3. Postula</div><div class="small text-muted">El estudiante se postula</div></div>
            <div class="col-6 col-md"><div class="fw-bold text-primary">4. Practica</div><div class="small text-muted">Registra actividades y horas</div></div>
            <div class="col-12 col-md"><div class="fw-bold text-primary">5. Evalúa</div><div class="small text-muted">Empresa y tutor evalúan al final</div></div>
        </div>

    </div>
</section>

<footer class="text-center pb-4 small">
    ITCA-FEPADE &middot; Sistema de Pasantías
</footer>

</body>
</html>
