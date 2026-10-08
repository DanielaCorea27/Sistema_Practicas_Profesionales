<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Observaciones</title>

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

            max-width: 1000px;

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

            padding: 30px;

            border-radius: 15px;

            border: 1px solid #e1e7ef;

            margin-bottom: 20px;
        }

        .observacion {
            background: #f8fafc;

            border-left: 5px solid #123f78;

            padding: 20px;

            margin-top: 20px;

            border-radius: 8px;
        }

        .fecha {
            color: #64748b;

            font-size: 13px;

            margin-bottom: 10px;
        }

    </style>

</head>


<body>


<header class="navbar">

    🎓 ITCA-FEPADE

</header>


<main class="contenedor">


    <a href="${pageContext.request.contextPath}/alumno/dashboard.jsp"
       class="volver">

        ← Volver al panel

    </a>


    <section class="card">

        <h1>
            📝 Observaciones
        </h1>

        <p>
            Comentarios realizados durante el seguimiento
            de tu práctica profesional.
        </p>


        <div class="observacion">

            <div class="fecha">
                Sin observaciones registradas
            </div>

            <p>
                El maestro encargado todavía no ha agregado
                observaciones a tu práctica.
            </p>

        </div>


    </section>


</main>


</body>

</html>