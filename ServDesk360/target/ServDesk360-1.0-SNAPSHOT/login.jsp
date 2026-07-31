<%-- 
    Document   : login.jsp
    Created on : 29 jul 2026, 6:33:20 p. m.
    Author     : danie
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Acceso | ServiceDesk 360</title>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/estilos.css">
    </head>
    <body>
        <main class="contenedor-formulario">
            <h1>Iniciar acceso</h1>
            <p>Ingrese con la cuenta temporal creada en esta práctica.</p>
            <div class="alerta exito">${mensajeExito}</div>
            <div class="alerta error">${mensajeError}</div>
            <form action="${pageContext.request.contextPath}/acceso" method="post">
                <div class="grupo-campo">
                    <label for="correo">Correo</label>
                    <input id="correo" name="correo" type="email"
                           maxlength="100" value="${ultimoUsuario}" required>
                </div>
                <div class="grupo-campo">
                    <label for="clave">Contraseña</label>
                    <input id="clave" name="clave" type="password"
                           maxlength="64" required>
                </div>
                <label class="fila-opciones">
                    <input type="checkbox" name="recordar" value="si">
                    Recordar únicamente mi correo en este navegador
                </label>
                <div class="acciones-formulario">
                    <button type="submit">Ingresar</button>
                    <a href="${pageContext.request.contextPath}/registro">Crear cuenta</a>
                </div>
            </form>
        </main>
    </body>
</html>