<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Mi perfil</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;

            font-family: Arial, sans-serif;

            background: #f3f6fa;

            color: #172033;
        }

        .navbar {
            background: #123f78;

            color: white;

            padding: 20px 35px;

            font-weight: bold;
        }

        .contenedor {
            width: 92%;

            max-width: 900px;

            margin: 35px auto;
        }

        .volver {
            display: inline-block;

            margin-bottom: 25px;

            color: #123f78;

            text-decoration: none;

            font-weight: bold;
        }

        .card {
            background: white;

            border-radius: 15px;

            padding: 35px;

            border: 1px solid #e1e7ef;

            box-shadow:
                0 5px 15px rgba(0,0,0,0.05);
        }

        h1 {
            margin-top: 0;
        }

        .dato {
            border-bottom: 1px solid #e5e7eb;

            padding: 18px 0;
        }

        .dato:last-child {
            border-bottom: none;
        }

        .label {
            display: block;

            color: #64748b;

            font-size: 13px;

            margin-bottom: 6px;
        }

        .valor {
            font-size: 17px;

            font-weight: bold;
        }

    </style>

</head>


<body>


<div class="navbar">

    🎓 ITCA-FEPADE

</div>


<main class="contenedor">


    <a href="${pageContext.request.contextPath}/alumno/dashboard.jsp"
       class="volver">

        ← Volver al panel

    </a>


    <section class="card">

        <h1>
            👤 Mi perfil
        </h1>

        <p>
            Información registrada del estudiante.
        </p>


        <div class="dato">

            <span class="label">
                Nombre completo
            </span>

            <span class="valor">
                Andersson Cienfuegos
            </span>

        </div>


        <div class="dato">

            <span class="label">
                Rol
            </span>

            <span class="valor">
                ALUMNO
            </span>

        </div>


        <div class="dato">

            <span class="label">
                Carrera
            </span>

            <span class="valor">
                Ingeniería de Sistemas
            </span>

        </div>


        <div class="dato">

            <span class="label">
                Estado
            </span>

            <span class="valor">
                Activo
            </span>

        </div>


    </section>


</main>


</body>

</html>