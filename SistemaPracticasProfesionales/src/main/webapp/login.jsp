<%-- 
    Document   : sss
    Created on : 7 oct 2026, 8:08:12 p. m.
    Author     : danie
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta
        name="viewport"
        content="width=device-width, initial-scale=1">

    <title>
        Iniciar sesión | Prácticas ITCA-FEPADE
    </title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
        rel="stylesheet">

    <style>

        body {
            min-height: 100vh;
            margin: 0;
            background:
                linear-gradient(
                    135deg,
                    #0b2545 0%,
                    #123b6d 50%,
                    #1d6fa5 100%
                );
            font-family: Arial, sans-serif;
        }

        .login-wrapper {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 30px;
        }

        .login-card {
            width: 100%;
            max-width: 950px;
            border: 0;
            border-radius: 24px;
            overflow: hidden;
            box-shadow:
                0 25px 70px rgba(0,0,0,.30);
        }

        .brand-panel {
            background:
                linear-gradient(
                    145deg,
                    #123b6d,
                    #0b2545
                );
            color: white;
            padding: 55px 45px;
            min-height: 600px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .brand-icon {
            width: 80px;
            height: 80px;
            border-radius: 20px;
            background: rgba(255,255,255,.12);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 38px;
            margin-bottom: 25px;
        }

        .brand-panel h1 {
            font-weight: 800;
            font-size: 38px;
        }

        .brand-panel p {
            color: rgba(255,255,255,.80);
            line-height: 1.7;
        }

        .feature {
            display: flex;
            gap: 12px;
            margin-top: 22px;
        }

        .feature i {
            color: #74c0fc;
            font-size: 20px;
        }

        .form-panel {
            background: white;
            padding: 50px;
            min-height: 600px;
        }

        .form-title {
            font-weight: 800;
            color: #123b6d;
        }

        .form-control {
            padding: 13px 15px;
            border-radius: 12px;
        }

        .btn-login {
            padding: 13px;
            border-radius: 12px;
            background: #123b6d;
            border: none;
            font-weight: 700;
        }

        .btn-login:hover {
            background: #0b2545;
        }

        .demo-box {
            background: #f4f7fb;
            border-radius: 15px;
            padding: 18px;
            margin-top: 25px;
        }

        .demo-title {
            color: #123b6d;
            font-weight: 700;
        }

        .account {
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 10px;
            padding: 10px 12px;
            margin-top: 8px;
            font-size: 13px;
        }

        @media (max-width: 768px) {

            .brand-panel {
                min-height: auto;
                padding: 35px;
            }

            .form-panel {
                min-height: auto;
                padding: 35px;
            }

        }

    </style>

</head>

<body>

<div class="login-wrapper">

    <div class="card login-card">

        <div class="row g-0">

            <div class="col-lg-6">

                <div class="brand-panel">

                    <div class="brand-icon">
                        <i class="bi bi-mortarboard-fill"></i>
                    </div>

                    <h1>
                        ITCA-FEPADE
                    </h1>

                    <h4 class="mb-3">
                        Sistema de Prácticas Profesionales
                    </h4>

                    <p>
                        Plataforma para el seguimiento,
                        control y administración de las
                        prácticas profesionales de los
                        estudiantes.
                    </p>

                    <div class="feature">

                        <i class="bi bi-check-circle-fill"></i>

                        <span>
                            Registro y seguimiento de horas.
                        </span>

                    </div>

                    <div class="feature">

                        <i class="bi bi-check-circle-fill"></i>

                        <span>
                            Gestión de evidencias.
                        </span>

                    </div>

                    <div class="feature">

                        <i class="bi bi-check-circle-fill"></i>

                        <span>
                            Control para alumnos y maestros.
                        </span>

                    </div>

                    <div class="feature">

                        <i class="bi bi-check-circle-fill"></i>

                        <span>
                            Reportes del progreso profesional.
                        </span>

                    </div>

                </div>

            </div>

            <div class="col-lg-6">

                <div class="form-panel">

                    <h2 class="form-title mb-2">
                        Bienvenido
                    </h2>

                    <p class="text-muted mb-4">
                        Inicia sesión para continuar.
                    </p>

                    <% if (request.getAttribute("error") != null) { %>

                        <div class="alert alert-danger">

                            <i class="bi bi-exclamation-triangle"></i>

                            <%= request.getAttribute("error") %>

                        </div>

                    <% } %>

                    <% if ("ok".equals(request.getParameter("registro"))) { %>

                        <div class="alert alert-success">

                            <i class="bi bi-check-circle"></i>

                            Cuenta creada correctamente. Ya puedes iniciar sesión.

                        </div>

                    <% } %>

                    <form
                        action="<%=request.getContextPath()%>/login"
                        method="post">

                        <div class="mb-3">

                            <label class="form-label fw-semibold">
                                Correo electrónico
                            </label>

                            <div class="input-group">

                                <span class="input-group-text">
                                    <i class="bi bi-envelope"></i>
                                </span>

                                <input
                                    type="email"
                                    name="correo"
                                    class="form-control"
                                    placeholder="correo@itca.edu.sv"
                                    required>

                            </div>

                        </div>

                        <div class="mb-4">

                            <label class="form-label fw-semibold">
                                Contraseña
                            </label>

                            <div class="input-group">

                                <span class="input-group-text">
                                    <i class="bi bi-lock"></i>
                                </span>

                                <input
                                    type="password"
                                    name="password"
                                    class="form-control"
                                    placeholder="Contraseña"
                                    required>

                            </div>

                        </div>

                        <button
                            type="submit"
                            class="btn btn-primary btn-login w-100">

                            <i class="bi bi-box-arrow-in-right"></i>

                            Iniciar sesión

                        </button>

                    </form>

                    <div class="text-center mt-3">
                        <span class="text-muted">¿No tienes cuenta?</span>
                        <a href="<%=request.getContextPath()%>/registro"
                           class="fw-semibold text-decoration-none">Crear cuenta</a>
                        &middot;
                        <a href="<%=request.getContextPath()%>/index.jsp"
                           class="text-decoration-none">Inicio</a>
                    </div>

                    <div class="demo-box">

                        <div class="demo-title mb-2">
                            <i class="bi bi-info-circle"></i>
                            Cuentas temporales
                        </div>

                        <div class="account">
                            <strong>Administrador:</strong><br>
                            admin@itca.edu.sv<br>
                            Contraseña: 123456
                        </div>

                        <div class="account">
                            <strong>Maestro:</strong><br>
                            maestro@itca.edu.sv<br>
                            Contraseña: 123456
                        </div>

                        <div class="account">
                            <strong>Alumno:</strong><br>
                            andersson@itca.edu.sv<br>
                            Contraseña: 123456
                        </div>

                        <div class="account">
                            <strong>Empresa:</strong><br>
                            telus@itca.edu.sv<br>
                            Contraseña: 123456
                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

</body>

</html>
