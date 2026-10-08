<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="sv.edu.itca.practicas.model.PracticaInfo"%>

<%
    PracticaInfo practica =
            (PracticaInfo) request.getAttribute("practica");
%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Mi práctica profesional</title>


    <style>

        * {
            box-sizing: border-box;
        }


        body {

            margin: 0;

            font-family:
                Arial,
                Helvetica,
                sans-serif;

            background: #f3f6fa;

            color: #172033;
        }


        /* =========================
           NAVBAR
           ========================= */

        .navbar {

            background: #123f78;

            color: white;

            padding: 18px 35px;

            display: flex;

            justify-content: space-between;

            align-items: center;

            box-shadow:
                0 3px 10px rgba(0,0,0,.15);
        }


        .logo {

            font-weight: bold;

            font-size: 18px;
        }


        .usuario {

            font-size: 14px;
        }


        /* =========================
           CONTENEDOR
           ========================= */

        .contenedor {

            width: 92%;

            max-width: 1200px;

            margin: 35px auto;
        }


        .volver {

            display: inline-block;

            margin-bottom: 25px;

            color: #123f78;

            text-decoration: none;

            font-weight: bold;
        }


        /* =========================
           HEADER
           ========================= */

        .encabezado {

            background: white;

            border-radius: 16px;

            padding: 30px;

            margin-bottom: 25px;

            border:
                1px solid #e1e7ef;

            box-shadow:
                0 5px 15px rgba(0,0,0,.05);
        }


        .encabezado h1 {

            margin:
                0 0 10px;

            font-size: 30px;
        }


        .encabezado p {

            margin: 0;

            color: #64748b;

            line-height: 1.6;
        }


        /* =========================
           ESTADO
           ========================= */

        .estado {

            display: inline-block;

            margin-top: 20px;

            padding:
                9px 18px;

            border-radius: 25px;

            background: #d1e7dd;

            color: #0f5132;

            font-weight: bold;

            font-size: 13px;
        }


        /* =========================
           GRID
           ========================= */

        .grid {

            display: grid;

            grid-template-columns:
                repeat(2, 1fr);

            gap: 22px;
        }


        /* =========================
           CARD
           ========================= */

        .card {

            background: white;

            border:
                1px solid #e1e7ef;

            border-radius: 16px;

            padding: 28px;

            box-shadow:
                0 5px 15px rgba(0,0,0,.05);
        }


        .card h2 {

            margin-top: 0;

            margin-bottom: 22px;

            font-size: 20px;
        }


        /* =========================
           DATOS
           ========================= */

        .dato {

            padding:
                15px 0;

            border-bottom:
                1px solid #e5e7eb;
        }


        .dato:last-child {

            border-bottom: none;
        }


        .etiqueta {

            display: block;

            color: #64748b;

            font-size: 13px;

            margin-bottom: 6px;
        }


        .valor {

            font-size: 16px;

            font-weight: bold;

            color: #172033;
        }


        /* =========================
           HORAS
           ========================= */

        .horas {

            text-align: center;

            padding: 25px;

            background: #f8fafc;

            border-radius: 12px;
        }


        .horas-numero {

            font-size: 42px;

            font-weight: bold;

            color: #123f78;
        }


        .horas-texto {

            color: #64748b;

            margin-top: 5px;
        }


        /* =========================
           RESPONSIVE
           ========================= */

        @media(max-width: 800px) {

            .grid {

                grid-template-columns:
                    1fr;
            }

            .navbar {

                padding:
                    18px;
            }

        }

    </style>

</head>


<body>


<!-- =====================================================
     NAVBAR
     ===================================================== -->

<header class="navbar">

    <div class="logo">

        🎓 ITCA-FEPADE

    </div>


    <div class="usuario">

        👨‍🎓 Andersson Cienfuegos

    </div>

</header>



<!-- =====================================================
     CONTENIDO
     ===================================================== -->

<main class="contenedor">


    <a href="${pageContext.request.contextPath}/alumno/dashboard.jsp"
       class="volver">

        ← Volver al panel

    </a>



    <%
        if (practica != null) {
    %>


    <!-- =================================================
         ENCABEZADO
         ================================================= -->

    <section class="encabezado">

        <h1>

            📋 Mi Práctica Profesional

        </h1>


        <p>

            Consulta la información relacionada con tu
            práctica profesional, empresa y seguimiento académico.

        </p>


        <span class="estado">

            <%= practica.getEstado() %>

        </span>

    </section>



    <!-- =================================================
         INFORMACIÓN
         ================================================= -->

    <section class="grid">


        <!-- =========================
             INFORMACIÓN DEL ALUMNO
             ========================= -->

        <div class="card">

            <h2>

                👨‍🎓 Información del estudiante

            </h2>


            <div class="dato">

                <span class="etiqueta">

                    Nombre completo

                </span>


                <span class="valor">

                    <%= practica.getAlumno() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    Carrera

                </span>


                <span class="valor">

                    <%= practica.getCarrera() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    ID del alumno

                </span>


                <span class="valor">

                    <%= practica.getAlumnoId() %>

                </span>

            </div>

        </div>



        <!-- =========================
             HORAS
             ========================= -->

        <div class="card">

            <h2>

                ⏱️ Horas de práctica

            </h2>


            <div class="horas">

                <div class="horas-numero">

                    <%= practica.getHorasRequeridas() %>

                </div>


                <div class="horas-texto">

                    Horas profesionales requeridas

                </div>

            </div>

        </div>



        <!-- =========================
             EMPRESA
             ========================= -->

        <div class="card">

            <h2>

                🏢 Empresa

            </h2>


            <div class="dato">

                <span class="etiqueta">

                    Empresa

                </span>


                <span class="valor">

                    <%= practica.getEmpresa() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    Área de trabajo

                </span>


                <span class="valor">

                    <%= practica.getArea() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    Dirección

                </span>


                <span class="valor">

                    <%= practica.getDireccionEmpresa() %>

                </span>

            </div>

        </div>



        <!-- =========================
             MAESTRO
             ========================= -->

        <div class="card">

            <h2>

                👨‍🏫 Maestro encargado

            </h2>


            <div class="dato">

                <span class="etiqueta">

                    Nombre

                </span>


                <span class="valor">

                    <%= practica.getMaestro() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    Correo

                </span>


                <span class="valor">

                    <%= practica.getCorreoMaestro() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    Teléfono

                </span>


                <span class="valor">

                    <%= practica.getTelefonoMaestro() %>

                </span>

            </div>

        </div>



        <!-- =========================
             FECHAS
             ========================= -->

        <div class="card">

            <h2>

                📅 Fechas de la práctica

            </h2>


            <div class="dato">

                <span class="etiqueta">

                    Fecha de inicio

                </span>


                <span class="valor">

                    <%= practica.getFechaInicio() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    Fecha de finalización

                </span>


                <span class="valor">

                    <%= practica.getFechaFin() %>

                </span>

            </div>

        </div>



        <!-- =========================
             ESTADO
             ========================= -->

        <div class="card">

            <h2>

                📊 Estado de la práctica

            </h2>


            <div class="dato">

                <span class="etiqueta">

                    Estado actual

                </span>


                <span class="valor">

                    <%= practica.getEstado() %>

                </span>

            </div>


            <div class="dato">

                <span class="etiqueta">

                    Horas requeridas

                </span>


                <span class="valor">

                    <%= practica.getHorasRequeridas() %> horas

                </span>

            </div>

        </div>


    </section>


    <%
        } else {
    %>


    <!-- =================================================
         SIN INFORMACIÓN
         ================================================= -->

    <section class="encabezado">

        <h1>

            ⚠️ Práctica no encontrada

        </h1>


        <p>

            <%= request.getAttribute("mensaje") != null
                    ? request.getAttribute("mensaje")
                    : "No existe información de práctica para este alumno." %>

        </p>

    </section>


    <%
        }
    %>


</main>


</body>

</html>