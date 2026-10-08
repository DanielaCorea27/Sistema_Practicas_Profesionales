<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>

<%@page import="sv.edu.itca.practicas.model.RegistroHora"%>


<%
    List<RegistroHora> registros =
            (List<RegistroHora>)
            request.getAttribute("registros");

    Integer pendientes =
            (Integer)
            request.getAttribute("pendientes");

    if (pendientes == null) {
        pendientes = 0;
    }
%>


<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Revisión de horas</title>


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

            font-size: 18px;

            font-weight: bold;
        }


        .usuario {

            font-size: 14px;
        }


        /* =========================
           CONTENEDOR
           ========================= */

        .contenedor {

            width: 94%;

            max-width: 1450px;

            margin: 35px auto;
        }


        .volver {

            display: inline-block;

            color: #123f78;

            text-decoration: none;

            font-weight: bold;

            margin-bottom: 25px;
        }


        /* =========================
           ENCABEZADO
           ========================= */

        .encabezado {

            background: white;

            padding: 30px;

            border-radius: 16px;

            border:
                1px solid #e1e7ef;

            margin-bottom: 25px;

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
        }


        /* =========================
           ESTADÍSTICAS
           ========================= */

        .estadisticas {

            display: grid;

            grid-template-columns:
                repeat(3, 1fr);

            gap: 20px;

            margin-bottom: 25px;
        }


        .estadistica {

            background: white;

            padding: 25px;

            border-radius: 15px;

            border:
                1px solid #e1e7ef;

            box-shadow:
                0 5px 15px rgba(0,0,0,.05);
        }


        .estadistica-titulo {

            color: #64748b;

            font-size: 14px;

            margin-bottom: 10px;
        }


        .estadistica-numero {

            font-size: 32px;

            font-weight: bold;
        }


        /* =========================
           TABLA
           ========================= */

        .tabla-contenedor {

            background: white;

            border-radius: 16px;

            padding: 25px;

            border:
                1px solid #e1e7ef;

            box-shadow:
                0 5px 15px rgba(0,0,0,.05);

            overflow-x: auto;
        }


        table {

            width: 100%;

            border-collapse: collapse;

            min-width: 1000px;
        }


        th {

            background: #123f78;

            color: white;

            padding: 14px;

            text-align: left;

            font-size: 13px;
        }


        td {

            padding: 15px;

            border-bottom:
                1px solid #e5e7eb;

            vertical-align: top;

            font-size: 14px;
        }


        tr:hover {

            background: #f8fafc;
        }


        /* =========================
           ESTADOS
           ========================= */

        .estado {

            display: inline-block;

            padding:
                7px 12px;

            border-radius: 20px;

            font-size: 12px;

            font-weight: bold;
        }


        .pendiente {

            background: #fff3cd;

            color: #856404;
        }


        .aprobado {

            background: #d1e7dd;

            color: #0f5132;
        }


        .rechazado {

            background: #f8d7da;

            color: #842029;
        }


        /* =========================
           BOTONES
           ========================= */

        .acciones {

            display: flex;

            flex-direction: column;

            gap: 10px;

            min-width: 220px;
        }


        .acciones textarea {

            width: 100%;

            min-height: 70px;

            resize: vertical;

            border:
                1px solid #cbd5e1;

            border-radius: 8px;

            padding: 9px;

            font-family: Arial;
        }


        .botones {

            display: flex;

            gap: 8px;
        }


        .btn {

            border: none;

            padding:
                9px 14px;

            border-radius: 7px;

            font-weight: bold;

            cursor: pointer;

            color: white;
        }


        .btn-aprobar {

            background: #198754;
        }


        .btn-aprobar:hover {

            background: #157347;
        }


        .btn-rechazar {

            background: #dc3545;
        }


        .btn-rechazar:hover {

            background: #bb2d3b;
        }


        .observacion {

            color: #64748b;

            font-size: 13px;

            max-width: 250px;
        }


        /* =========================
           MENSAJES
           ========================= */

        .mensaje {

            background: #d1e7dd;

            color: #0f5132;

            padding: 15px;

            border-radius: 8px;

            margin-bottom: 20px;

            font-weight: bold;
        }


        .error {

            background: #f8d7da;

            color: #842029;

            padding: 15px;

            border-radius: 8px;

            margin-bottom: 20px;

            font-weight: bold;
        }


        /* =========================
           RESPONSIVE
           ========================= */

        @media(max-width: 800px) {

            .estadisticas {

                grid-template-columns:
                    1fr;
            }

            .navbar {

                padding: 18px;
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

        👨‍🏫 Panel del Maestro

    </div>

</header>



<!-- =====================================================
     CONTENIDO
     ===================================================== -->

<main class="contenedor">


    <a href="${pageContext.request.contextPath}/maestro/dashboard.jsp"
       class="volver">

        ← Volver al panel del maestro

    </a>



    <section class="encabezado">

        <h1>

            ⏱️ Revisión de horas

        </h1>


        <p>

            Revisa las horas registradas por los alumnos
            y determina si las actividades realizadas
            cumplen con los requisitos de la práctica.

        </p>

    </section>



    <!-- =================================================
         MENSAJES
         ================================================= -->

    <%
        String mensaje =
                request.getParameter("mensaje");

        String error =
                request.getParameter("error");


        if ("ok".equals(mensaje)) {
    %>

        <div class="mensaje">

            ✅ La operación se realizó correctamente.

        </div>

    <%
        }


        if (error != null) {
    %>

        <div class="error">

            ❌ No fue posible realizar la operación.

        </div>

    <%
        }
    %>



    <!-- =================================================
         ESTADÍSTICAS
         ================================================= -->

    <section class="estadisticas">


        <div class="estadistica">

            <div class="estadistica-titulo">

                📋 Registros revisados

            </div>

            <div class="estadistica-numero">

                <%= registros != null
                        ? registros.size()
                        : 0 %>

            </div>

        </div>



        <div class="estadistica">

            <div class="estadistica-titulo">

                ⏳ Pendientes de revisión

            </div>

            <div class="estadistica-numero">

                <%= pendientes %>

            </div>

        </div>



        <div class="estadistica">

            <div class="estadistica-titulo">

                👨‍🎓 Alumno

            </div>

            <div class="estadistica-numero"
                 style="font-size:22px;">

                Andersson Cienfuegos

            </div>

        </div>


    </section>



    <!-- =================================================
         TABLA
         ================================================= -->

    <section class="tabla-contenedor">


        <h2>

            Registros de horas

        </h2>


        <br>


        <table>


            <thead>

                <tr>

                    <th>
                        ID
                    </th>

                    <th>
                        Alumno
                    </th>

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
                        Observación
                    </th>

                    <th>
                        Acción
                    </th>

                </tr>

            </thead>


            <tbody>


            <%
                if (registros != null
                        && !registros.isEmpty()) {

                    for (RegistroHora registro
                            : registros) {


                        String claseEstado =
                                "pendiente";


                        if ("APROBADO".equals(
                                registro.getEstado())) {

                            claseEstado =
                                    "aprobado";

                        } else if ("RECHAZADO".equals(
                                registro.getEstado())) {

                            claseEstado =
                                    "rechazado";
                        }
            %>


                <tr>


                    <!-- ID -->

                    <td>

                        <%= registro.getId() %>

                    </td>



                    <!-- ALUMNO -->

                    <td>

                        <strong>
                            Andersson Cienfuegos
                        </strong>

                    </td>



                    <!-- FECHA -->

                    <td>

                        <%= registro.getFecha() %>

                    </td>



                    <!-- HORAS -->

                    <td>

                        <strong>

                            <%= registro.getHoras() %> h

                        </strong>

                    </td>



                    <!-- ACTIVIDAD -->

                    <td>

                        <%= registro.getActividad() %>

                    </td>



                    <!-- ESTADO -->

                    <td>

                        <span class="estado <%= claseEstado %>">

                            <%= registro.getEstado() %>

                        </span>

                    </td>



                    <!-- OBSERVACIÓN -->

                    <td>

                        <div class="observacion">

                            <%
                                if (registro.getObservacion()
                                        != null
                                        && !registro.getObservacion()
                                                .trim()
                                                .isEmpty()) {
                            %>

                                <%= registro.getObservacion() %>

                            <%
                                } else {
                            %>

                                Sin observaciones

                            <%
                                }
                            %>

                        </div>

                    </td>



                    <!-- ACCIONES -->

                    <td>


                    <%
                        if ("PENDIENTE".equals(
                                registro.getEstado())) {
                    %>


                        <div class="acciones">


                            <!-- =========================
                                 APROBAR
                                 ========================= -->

                            <form method="post"
                                  action="${pageContext.request.contextPath}/maestro/horas">

                                <input type="hidden"
                                       name="accion"
                                       value="aprobar">


                                <input type="hidden"
                                       name="id"
                                       value="<%= registro.getId() %>">


                                <textarea
                                    name="observacion"
                                    placeholder="Observación opcional..."></textarea>


                                <div class="botones">

                                    <button
                                        type="submit"
                                        class="btn btn-aprobar">

                                        ✅ Aprobar

                                    </button>

                                </div>

                            </form>



                            <!-- =========================
                                 RECHAZAR
                                 ========================= -->

                            <form method="post"
                                  action="${pageContext.request.contextPath}/maestro/horas">

                                <input type="hidden"
                                       name="accion"
                                       value="rechazar">


                                <input type="hidden"
                                       name="id"
                                       value="<%= registro.getId() %>">


                                <textarea
                                    name="observacion"
                                    placeholder="Motivo del rechazo..."></textarea>


                                <div class="botones">

                                    <button
                                        type="submit"
                                        class="btn btn-rechazar">

                                        ❌ Rechazar

                                    </button>

                                </div>

                            </form>


                        </div>


                    <%
                        } else {
                    %>


                        <span class="observacion">

                            Registro ya procesado.

                        </span>


                    <%
                        }
                    %>


                    </td>


                </tr>


            <%
                    }

                } else {
            %>


                <tr>

                    <td colspan="8"
                        style="text-align:center; padding:40px;">

                        No existen registros de horas.

                    </td>

                </tr>


            <%
                }
            %>


            </tbody>


        </table>


    </section>


</main>


</body>

</html>