<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="sv.edu.itca.practicas.model.Empresa"%>

<%
    Empresa empresa =
            (Empresa) request.getAttribute("empresa");

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
                ? "Editar empresa"
                : "Nueva empresa"
        %>
    </title>

    <style>

        body {
            margin: 0;
            background: #f5f7fb;
            font-family: Arial, sans-serif;
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
            max-width: 850px;

            margin: 40px auto;

            padding: 20px;
        }

        .card {
            background: white;

            padding: 35px;

            border-radius: 18px;

            box-shadow:
                0 5px 20px rgba(0,0,0,.06);
        }

        h1 {
            margin-top: 0;
        }

        .subtitulo {
            color: #6c757d;

            margin-bottom: 30px;
        }

        .fila {
            display: grid;

            grid-template-columns:
                repeat(2, 1fr);

            gap: 20px;
        }

        .grupo {
            margin-bottom: 22px;
        }

        .grupo-completo {
            grid-column: 1 / -1;
        }

        label {
            display: block;

            margin-bottom: 8px;

            font-weight: bold;

            color: #343a40;
        }

        .control {
            width: 100%;

            box-sizing: border-box;

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

        .error {
            background: #f8d7da;

            color: #842029;

            padding: 13px;

            border-radius: 10px;

            margin-bottom: 25px;
        }

        .switch {
            display: flex;

            align-items: center;

            gap: 10px;
        }

        .switch input {
            width: 20px;
            height: 20px;
        }

        .acciones {
            display: flex;

            justify-content: space-between;

            margin-top: 25px;
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

        @media (max-width: 700px) {

            .fila {
                grid-template-columns: 1fr;
            }

            .grupo-completo {
                grid-column: auto;
            }

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
                    ? "✏️ Editar empresa"
                    : "➕ Nueva empresa"
            %>

        </h1>

        <p class="subtitulo">

            Registra la información de la empresa
            donde podrán realizarse prácticas profesionales.

        </p>

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
            action="${pageContext.request.contextPath}/admin/empresas"
        >

            <input
                type="hidden"
                name="accion"
                value="guardar"
            >

            <input
                type="hidden"
                name="id"
                value="<%= empresa.getId() %>"
            >

            <div class="fila">

                <div class="grupo grupo-completo">

                    <label for="nombre">
                        Nombre de la empresa
                    </label>

                    <input
                        type="text"
                        id="nombre"
                        name="nombre"
                        class="control"
                        maxlength="150"
                        placeholder="Ej. Empresa XYZ"
                        value="<%= empresa.getNombre() %>"
                        required
                    >

                </div>

                <div class="grupo grupo-completo">

                    <label for="direccion">
                        Dirección
                    </label>

                    <input
                        type="text"
                        id="direccion"
                        name="direccion"
                        class="control"
                        maxlength="250"
                        placeholder="Dirección de la empresa"
                        value="<%= empresa.getDireccion() %>"
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
                        placeholder="0000-0000"
                        value="<%= empresa.getTelefono() %>"
                        required
                    >

                </div>

                <div class="grupo">

                    <label for="correo">
                        Correo electrónico
                    </label>

                    <input
                        type="email"
                        id="correo"
                        name="correo"
                        class="control"
                        maxlength="150"
                        placeholder="contacto@empresa.com"
                        value="<%= empresa.getCorreo() %>"
                        required
                    >

                </div>

                <div class="grupo grupo-completo">

                    <label for="contacto">
                        Persona de contacto
                    </label>

                    <input
                        type="text"
                        id="contacto"
                        name="contacto"
                        class="control"
                        maxlength="150"
                        placeholder="Nombre del encargado"
                        value="<%= empresa.getContacto() %>"
                        required
                    >

                </div>

                <div class="grupo grupo-completo">

                    <label>
                        Estado
                    </label>

                    <div class="switch">

                        <input
                            type="checkbox"
                            id="activa"
                            name="activa"
                            value="true"
                            <%= empresa.isActiva()
                                    ? "checked"
                                    : "" %>
                        >

                        <label
                            for="activa"
                            style="margin:0;"
                        >
                            Empresa activa
                        </label>

                    </div>

                </div>

            </div>

            <div class="acciones">

                <a
                    href="${pageContext.request.contextPath}/admin/empresas"
                    class="btn cancelar"
                >
                    ← Cancelar
                </a>

                <button
                    type="submit"
                    class="btn guardar"
                >
                    💾 Guardar empresa
                </button>

            </div>

        </form>

    </div>

</main>

</body>

</html>