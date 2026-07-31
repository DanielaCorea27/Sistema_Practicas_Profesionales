<%-- 
    Document   : panel
    Created on : 29 jul 2026, 6:34:24 p. m.
    Author     : danie
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Panel | ServiceDesk 360</title>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/estilos.css">
    </head>
    <body>
        <main class="panel-usuario">
            <h1>Bienvenido, ${sessionScope.usuarioNombre}</h1>
            <p>Correo: ${sessionScope.usuarioCorreo}</p>
            <p>Rol: ${sessionScope.usuarioRolDescripcion}</p>
            <div class="tarjetas-panel">
                <section class="tarjeta-panel">
                    <h2>Solicitudes</h2>
                    <p>Módulo que se desarrollará progresivamente.</p>
                </section>
                <section class="tarjeta-panel">
                    <h2>Equipos</h2>
                    <p>Registro y seguimiento de activos tecnológicos.</p>
                </section>
                <section class="tarjeta-panel">
                    <h2>Reportes</h2>
                    <p>Se incorporarán durante la Unidad 5.</p>
                </section>
            </div>
            <form action="${pageContext.request.contextPath}/cerrar-sesion"
                  method="post" style="margin-top: 24px;">
                <button type="submit">Cerrar sesión</button>
            </form>
        </main>
    </body>
</html>
