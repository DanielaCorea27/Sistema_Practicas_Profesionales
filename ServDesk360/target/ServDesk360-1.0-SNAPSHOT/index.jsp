<%-- 
    Document   : index
    Created on : 29 jul 2026, 6:17:21 p. m.
    Author     : danie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="acciones-formulario">
            <a href="${pageContext.request.contextPath}/registro">
                Crear cuenta temporal
            </a>
                <hr>
            <a href="${pageContext.request.contextPath}/acceso">
                Iniciar acceso
            </a>
        </div>
    </body>
</html>
