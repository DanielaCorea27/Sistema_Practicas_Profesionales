<%-- 
    Document   : registro
    Created on : 29 jul 2026, 6:31:25 p. m.
    Author     : danie
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro | ServiceDesk 360</title>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/estilos.css">
    </head>
    <body>
        <main class="contenedor-formulario">
            <h1>Crear cuenta temporal</h1>
            <p>Complete la información para ingresar al caso modelo.</p>
            <div class="alerta error">${mensajeError}</div>
            <form action="${pageContext.request.contextPath}/registro"
                  method="post" autocomplete="off">
                <div class="grupo-campo">
                    <label for="nombre">Nombre completo</label>
                    <input id="nombre" name="nombre" type="text"
                           maxlength="80" required>
                </div>
                <div class="grupo-campo">
                    <label for="correo">Correo</label>
                    <input id="correo" name="correo" type="email"
                           maxlength="100" required>
                </div>
                <div class="grupo-campo">
                    <label for="rol">Rol inicial</label>
                    <select id="rol" name="rol" required>
                        <option value="">Seleccione</option>
                        <option value="SOLICITANTE">Solicitante</option>
                        <option value="TECNICO">Técnico</option>
                    </select>
                </div>
                <div class="grupo-campo">
                    <label for="clave">Contraseña</label>
                    <input id="clave" name="clave" type="password"
                           minlength="8" maxlength="64" required>
                </div>
                <div class="grupo-campo">
                    <label for="confirmarClave">Confirmar contraseña</label>
                    <input id="confirmarClave" name="confirmarClave"
                           type="password" minlength="8" maxlength="64" required>
                </div>
                <div class="acciones-formulario">
                    <button type="submit">Crear cuenta</button>
                    <a href="${pageContext.request.contextPath}/acceso">Ya tengo cuenta</a>
                </div>
            </form>
        </main>
    </body>
</html>