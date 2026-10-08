<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Object fechaActual =
            request.getAttribute("fechaActual");

    String error =
            (String) request.getAttribute("error");
%>


<!DOCTYPE html>

<html lang="es">


<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Registrar horas
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

            font-weight: bold;

            font-size: 20px;
        }


        .back {

            color: white;

            text-decoration: none;

            background:
                rgba(255,255,255,.15);

            padding: 9px 15px;

            border-radius: 8px;
        }


        .contenedor {

            max-width: 750px;

            margin: 45px auto;

            padding: 20px;
        }


        .card {

            background: white;

            border-radius: 18px;

            padding: 35px;

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
        }


        input,
        textarea {

            width: 100%;

            padding: 13px;

            border:
                1px solid #ced4da;

            border-radius: 10px;

            font-size: 15px;

            font-family: inherit;
        }


        textarea {

            min-height: 130px;

            resize: vertical;
        }


        input:focus,
        textarea:focus {

            outline: none;

            border-color: #0d6efd;

            box-shadow:
                0 0 0 3px
                rgba(13,110,253,.12);
        }


        .ayuda {

            font-size: 13px;

            color: #6c757d;

            margin-top: 7px;
        }


        .error {

            background: #f8d7da;

            color: #842029;

            padding: 14px;

            border-radius: 10px;

            margin-bottom: 25px;
        }


        .info {

            background: #e7f1ff;

            color: #084298;

            padding: 15px;

            border-radius: 10px;

            margin-bottom: 25px;

            line-height: 1.5;
        }


        .acciones {

            display: flex;

            justify-content: space-between;

            margin-top: 30px;
        }


        .btn {

            border: none;

            text-decoration: none;

            padding: 13px 20px;

            border-radius: 10px;

            font-weight: bold;

            cursor: pointer;

            font-size: 14px;
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


    <div class="brand">

        🎓 ITCA-FEPADE

    </div>


    <a
        class="back"
        href="${pageContext.request.contextPath}/alumno/horas"
    >

        ← Volver

    </a>


</nav>



<main class="contenedor">


    <div class="card">


        <h1>

            ⏱️ Registrar horas

        </h1>


        <p class="subtitulo">

            Registra las horas realizadas durante
            tu práctica profesional.

        </p>



        <div class="info">

            💡 <strong>Importante:</strong>

            Cada registro será enviado como
            <strong>PENDIENTE</strong> para que
            posteriormente el maestro pueda
            revisarlo y aprobarlo.

        </div>



        <%
            if (error != null) {
        %>

            <div class="error">

                ⚠️ <%= error %>

            </div>

        <%
            }
        %>



        <form
            method="POST"
            action="${pageContext.request.contextPath}/alumno/horas"
        >


            <input
                type="hidden"
                name="accion"
                value="guardar"
            >



            <div class="grupo">


                <label for="fecha">

                    Fecha

                </label>


                <input
                    type="date"
                    id="fecha"
                    name="fecha"
                    value="<%= fechaActual %>"
                    required
                >


                <div class="ayuda">

                    Selecciona el día en que realizaste
                    las actividades.

                </div>


            </div>



            <div class="grupo">


                <label for="horas">

                    Horas realizadas

                </label>


                <input
                    type="number"
                    id="horas"
                    name="horas"
                    min="0.5"
                    max="24"
                    step="0.5"
                    placeholder="Ej. 8"
                    required
                >


                <div class="ayuda">

                    Puedes utilizar valores como:
                    4, 6, 7.5, 8, etc.

                </div>


            </div>



            <div class="grupo">


                <label for="actividad">

                    Actividad realizada

                </label>


                <textarea
                    id="actividad"
                    name="actividad"
                    maxlength="500"
                    placeholder="Describe detalladamente las actividades que realizaste durante la jornada..."
                    required
                ></textarea>


                <div class="ayuda">

                    Máximo 500 caracteres.

                </div>


            </div>



            <div class="acciones">


                <a
                    href="${pageContext.request.contextPath}/alumno/horas"
                    class="btn cancelar"
                >

                    Cancelar

                </a>


                <button
                    type="submit"
                    class="btn guardar"
                >

                    💾 Guardar horas

                </button>


            </div>


        </form>


    </div>


</main>


</body>

</html>