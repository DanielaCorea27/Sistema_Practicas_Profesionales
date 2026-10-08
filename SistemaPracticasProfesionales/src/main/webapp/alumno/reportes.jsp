<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Reportes</title>

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

            max-width: 1100px;

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

        .tabla {
            width: 100%;

            border-collapse: collapse;

            margin-top: 25px;
        }

        .tabla th,
        .tabla td {
            padding: 15px;

            border-bottom: 1px solid #e5e7eb;

            text-align: left;
        }

        .tabla th {
            background: #f8fafc;
        }

        .numero {
            font-weight: bold;

            font-size: 18px;
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
            📊 Reporte de práctica
        </h1>

        <p>
            Resumen actual de las horas de práctica profesional.
        </p>


        <table class="tabla">

            <thead>

                <tr>

                    <th>
                        Concepto
                    </th>

                    <th>
                        Cantidad
                    </th>

                </tr>

            </thead>


            <tbody>

                <tr>

                    <td>
                        Horas requeridas
                    </td>

                    <td class="numero">
                        640
                    </td>

                </tr>


                <tr>

                    <td>
                        Horas aprobadas
                    </td>

                    <td class="numero">
                        0
                    </td>

                </tr>


                <tr>

                    <td>
                        Horas restantes
                    </td>

                    <td class="numero">
                        640
                    </td>

                </tr>


                <tr>

                    <td>
                        Porcentaje de avance
                    </td>

                    <td class="numero">
                        0%
                    </td>

                </tr>

            </tbody>

        </table>


    </section>


</main>


</body>

</html>