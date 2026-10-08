<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="sv.edu.itca.practicas.model.Alumno"%>

<%
    Alumno alumno =
            (Alumno) request.getAttribute("alumno");

    String modo =
            (String) request.getAttribute("modo");

    String error =
            (String) request.getAttribute("error");

    boolean editar =
            "editar".equals(modo);
%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>

        <%= editar
                ? "Editar alumno"
                : "Nuevo alumno"
        %>

    </title>


    <style>

        * {
            box-sizing: border-box;
        }


        body {

            margin: 0;

            background: #f5f7fb;

            font-family:
                Arial,
                Helvetica,
                sans-serif;
        }


        .navbar {

            height: 70px;

            background: #123b6d;

            color: white;

            display: flex;

            align-items: center;

            justify-content: space-between;

            padding: 0 30px;
        }


        .navbar-brand {

            font-size: 20px;

            font-weight: bold;
        }


        .btn-salir {

            background: #dc3545;

            color: white;

            text-decoration: none;

            padding: 9px 15px;

            border-radius: 8px;
        }


        .contenedor {

            max-width: 750px;

            margin: 40px auto;

            padding: 20px;
        }


        .card {

            background: white;

            padding: 35px;

            border-radius: 18px;

            box-shadow:
                0 5px 20px
                rgba(0,0,0,.06);
        }


        h1 {

            margin-top: 0;
        }


        .subtitulo {

            color: #6c757d;

            margin-bottom: 30px;
        }


        .grupo {

            margin-bottom: 22px;
        }


        label {

            display: block;

            margin-bottom: 8px;

            font-weight: bold;

            color: #343a40;
        }


        .control {

            width: 100%;

            padding: 13px;

            border:
                1px solid #ced4da;

            border-radius: 10px;

            font-size: 15px;

            outline: none;
        }


        .control:focus {

            border-color: #0d6efd;

            box-shadow:
                0 0 0 3px
                rgba(13,110,253,.12);
        }


        .info {

            background: #e7f1ff;

            border-left:
                4px solid #0d6efd;

            padding: 15px;

            border-radius: 8px;

            margin-bottom: 25px;

            color: #084298;
        }


        .error {

            background: #f8d7da;

            color: #842029;

            padding: 13px;

            border-radius: 10px;

            margin-bottom: 25px;
        }


        .acciones {

            display: flex;

            justify-content: space-between;

            margin-top: 30px;
        }


        .btn {

            padding: 12px 20px;

            border-radius: 10px;

            border: none;

            text-decoration: none;

            font-weight: bold;

            cursor: pointer;
        }


        .cancelar {

            background: #6c757d;

            color: white;
        }


        .guardar {

            background: #0d6efd;

            color: white;
        }

    </style>

</head>


<body>


<nav class="navbar">


    <div class="navbar-brand">

        🎓 ITCA-FEPADE

    </div>


    <a
        class="btn-salir"
        href="${pageContext.request.contextPath}/logout"
    >

        Salir

    </a>


</nav>



<main class="contenedor">


    <div class="card">


        <h1>

            <%= editar
                    ? "✏️ Editar alumno"
                    : "➕ Nuevo alumno"
            %>

        </h1>


        <p class="subtitulo">

            Registra la información académica
            y de contacto del estudiante.

        </p>



        <div class="info">

            💡 <strong>Importante:</strong>

            La carrera determinará posteriormente
            la cantidad de horas profesionales
            que deberá completar el estudiante.

        </div>



        <%
            if (error != null) {
        %>


            <div class="error">

                ⚠️

                <%= error %>

            </div>


        <%
            }
        %>



        <form
            method="POST"
            action="${pageContext.request.contextPath}/admin/alumnos"
        >


            <input
                type="hidden"
                name="accion"
                value="guardar"
            >


            <input
                type="hidden"
                name="id"
                value="<%= alumno.getId() %>"
            >



            <div class="grupo">


                <label for="carnet">

                    Carnet del estudiante

                </label>


                <input
                    type="text"
                    id="carnet"
                    name="carnet"
                    class="control"
                    maxlength="30"
                    placeholder="Ej. 20240001"
                    value="<%= alumno.getCarnet() %>"
                    required
                >


            </div>



            <div class="grupo">


                <label for="telefono">

                    Teléfono

                </label>


                <input
                    type="text"
                    id="telefono"
                    name="telefono"
                    class="control"
                    maxlength="30"
                    placeholder="Ej. 7000-0000"
                    value="<%= alumno.getTelefono() %>"
                    required
                >


            </div>



            <div class="info">

                👤 El usuario, nombre completo y carrera
                se vincularán desde sus respectivos módulos.

            </div>



            <div class="acciones">


                <a
                    href="${pageContext.request.contextPath}/admin/alumnos"
                    class="btn cancelar"
                >

                    ← Cancelar

                </a>


                <button
                    type="submit"
                    class="btn guardar"
                >

                    💾 Guardar alumno

                </button>


            </div>


        </form>


    </div>


</main>


</body>

</html>