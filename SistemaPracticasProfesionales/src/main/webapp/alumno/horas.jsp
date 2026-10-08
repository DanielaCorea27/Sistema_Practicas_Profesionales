<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="sv.edu.itca.practicas.model.RegistroHora"%>

<%
    List<RegistroHora> registros =
            (List<RegistroHora>)
            request.getAttribute("registros");

    Double totalHoras =
            (Double)
            request.getAttribute("totalHoras");

    Double horasAprobadas =
            (Double)
            request.getAttribute("horasAprobadas");

    Double horasRestantes =
            (Double)
            request.getAttribute("horasRestantes");

    Double porcentaje =
            (Double)
            request.getAttribute("porcentaje");

    Double horasRequeridas =
            (Double)
            request.getAttribute("horasRequeridas");
%>


<!DOCTYPE html>

<html lang="es">


<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Seguimiento de horas
    </title>


    <style>

        * {
            box-sizing: border-box;
        }


        body {

            margin: 0;

            background: #f4f7fb;

            font-family:
                Arial,
                Helvetica,
                sans-serif;

            color: #212529;
        }


        .navbar {

            height: 70px;

            background: #123b6d;

            color: white;

            display: flex;

            align-items: center;

            justify-content: space-between;

            padding: 0 35px;
        }


        .brand {

            font-size: 20px;

            font-weight: bold;
        }


        .nav-link {

            color: white;

            text-decoration: none;

            padding: 9px 15px;

            border-radius: 8px;

            background: rgba(255,255,255,.15);
        }


        .contenedor {

            max-width: 1250px;

            margin: auto;

            padding: 35px 25px;
        }


        .encabezado {

            display: flex;

            justify-content: space-between;

            align-items: center;

            margin-bottom: 30px;
        }


        h1 {

            margin: 0 0 8px 0;

            font-size: 30px;
        }


        .subtitulo {

            margin: 0;

            color: #6c757d;
        }


        .btn-principal {

            background: #0d6efd;

            color: white;

            text-decoration: none;

            padding: 13px 18px;

            border-radius: 10px;

            font-weight: bold;
        }


        .estadisticas {

            display: grid;

            grid-template-columns:
                repeat(4, 1fr);

            gap: 18px;

            margin-bottom: 25px;
        }


        .stat {

            background: white;

            padding: 22px;

            border-radius: 15px;

            box-shadow:
                0 5px 20px
                rgba(0,0,0,.06);
        }


        .stat-icon {

            font-size: 28px;

            margin-bottom: 10px;
        }


        .stat-label {

            color: #6c757d;

            font-size: 14px;

            margin-bottom: 5px;
        }


        .stat-value {

            font-size: 28px;

            font-weight: bold;
        }


        .principal {

            background: white;

            padding: 28px;

            border-radius: 18px;

            box-shadow:
                0 5px 20px
                rgba(0,0,0,.06);

            margin-bottom: 25px;
        }


        .progreso-header {

            display: flex;

            justify-content: space-between;

            margin-bottom: 12px;
        }


        .progreso-porcentaje {

            font-size: 20px;

            font-weight: bold;

            color: #0d6efd;
        }


        .barra {

            width: 100%;

            height: 18px;

            background: #e9ecef;

            border-radius: 20px;

            overflow: hidden;
        }


        .barra-progreso {

            height: 100%;

            background: #0d6efd;

            width: <%= porcentaje %>%;

            transition: width .5s ease;
        }


        .tabla-card {

            background: white;

            padding: 28px;

            border-radius: 18px;

            box-shadow:
                0 5px 20px
                rgba(0,0,0,.06);
        }


        .tabla-titulo {

            margin-top: 0;

            margin-bottom: 20px;
        }


        table {

            width: 100%;

            border-collapse: collapse;
        }


        th {

            background: #f8f9fa;

            text-align: left;

            padding: 14px;
        }


        td {

            padding: 14px;

            border-top:
                1px solid #eeeeee;
        }


        .badge {

            padding: 6px 10px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: bold;
        }


        .aprobado {

            background: #d1e7dd;

            color: #0f5132;
        }


        .pendiente {

            background: #fff3cd;

            color: #664d03;
        }


        .btn-eliminar {

            color: #dc3545;

            text-decoration: none;

            font-weight: bold;
        }


        .vacio {

            text-align: center;

            padding: 45px;

            color: #6c757d;
        }


        @media(max-width: 900px) {

            .estadisticas {

                grid-template-columns:
                    repeat(2, 1fr);
            }
        }


        @media(max-width: 600px) {

            .estadisticas {

                grid-template-columns: 1fr;
            }


            .encabezado {

                flex-direction: column;

                align-items: flex-start;

                gap: 20px;
            }

        }

    </style>

</head>


<body>


<nav class="navbar">


    <div class="brand">

        🎓 ITCA-FEPADE

    </div>


    <a
        href="${pageContext.request.contextPath}/alumno/dashboard.jsp"
        class="nav-link"
    >

        ← Mi panel

    </a>


</nav>



<main class="contenedor">


    <div class="encabezado">


        <div>

            <h1>

                ⏱️ Seguimiento de horas

            </h1>


            <p class="subtitulo">

                Control de horas de tu práctica profesional.

            </p>

        </div>


        <a
            href="${pageContext.request.contextPath}/alumno/horas?accion=nuevo"
            class="btn-principal"
        >

            ➕ Registrar horas

        </a>


    </div>



    <section class="estadisticas">


        <div class="stat">

            <div class="stat-icon">
                🎯
            </div>

            <div class="stat-label">
                Horas requeridas
            </div>

            <div class="stat-value">

                <%= horasRequeridas %> h

            </div>

        </div>



        <div class="stat">

            <div class="stat-icon">
                ✅
            </div>

            <div class="stat-label">
                Horas aprobadas
            </div>

            <div class="stat-value">

                <%= horasAprobadas %> h

            </div>

        </div>



        <div class="stat">

            <div class="stat-icon">
                📝
            </div>

            <div class="stat-label">
                Horas registradas
            </div>

            <div class="stat-value">

                <%= totalHoras %> h

            </div>

        </div>



        <div class="stat">

            <div class="stat-icon">
                ⏳
            </div>

            <div class="stat-label">
                Horas restantes
            </div>

            <div class="stat-value">

                <%= horasRestantes %> h

            </div>

        </div>


    </section>



    <section class="principal">


        <div class="progreso-header">

            <strong>
                Progreso de la práctica
            </strong>


            <span class="progreso-porcentaje">

                <%= String.format(
                        "%.2f",
                        porcentaje
                    ) %>%

            </span>

        </div>


        <div class="barra">

            <div class="barra-progreso"></div>

        </div>


        <p>

            Has completado

            <strong>
                <%= horasAprobadas %>
            </strong>

            de

            <strong>
                <%= horasRequeridas %>
            </strong>

            horas requeridas.

        </p>


    </section>



    <section class="tabla-card">


        <h2 class="tabla-titulo">

            📋 Historial de horas

        </h2>



        <%
            if (registros == null
                    || registros.isEmpty()) {
        %>


            <div class="vacio">

                <h3>
                    Todavía no tienes registros.
                </h3>

                <p>
                    Registra las horas trabajadas
                    para comenzar tu seguimiento.
                </p>

            </div>


        <%
            } else {
        %>


        <table>


            <thead>

                <tr>

                    <th>
                        Fecha
                    </th>

                    <th>
                        Horas
                    </th>

                    <th>
                        Actividad
                    </th>

                    <th>
                        Estado
                    </th>

                    <th>
                        Acción
                    </th>

                </tr>

            </thead>


            <tbody>


            <%
                for (RegistroHora registro
                        : registros) {
            %>


                <tr>


                    <td>

                        <%= registro.getFecha() %>

                    </td>


                    <td>

                        <strong>

                            <%= registro.getHoras() %> h

                        </strong>

                    </td>


                    <td>

                        <%= registro.getActividad() %>

                    </td>


                    <td>


                        <%
                            if ("APROBADO".equals(
                                    registro.getEstado())) {
                        %>

                            <span
                                class="badge aprobado"
                            >

                                ✓ Aprobado

                            </span>

                        <%
                            } else {
                        %>

                            <span
                                class="badge pendiente"
                            >

                                ⏳ Pendiente

                            </span>

                        <%
                            }
                        %>


                    </td>


                    <td>

                        <%
                            if (!"APROBADO".equals(
                                    registro.getEstado())) {
                        %>


                            <a
                                class="btn-eliminar"
                                href="${pageContext.request.contextPath}/alumno/horas?accion=eliminar&id=<%= registro.getId() %>"
                                onclick="return confirm('¿Desea eliminar este registro?');"
                            >

                                🗑️ Eliminar

                            </a>


                        <%
                            } else {
                        %>

                            —

                        <%
                            }
                        %>

                    </td>


                </tr>


            <%
                }
            %>


            </tbody>


        </table>


        <%
            }
        %>


    </section>


</main>


</body>

</html>