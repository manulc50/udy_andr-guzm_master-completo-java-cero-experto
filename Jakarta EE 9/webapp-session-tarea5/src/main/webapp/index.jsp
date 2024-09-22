<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nombre = (String)session.getAttribute("nombre");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Tarea 5: Sessión HTTP</title>
    </head>
    <body>
        <h3>Tarea 5: Sessión HTTP</h3>
        <p>Hola <%=nombre != null && !nombre.isBlank() ? nombre : "anónimo"%>, bienvenido a la tarea 5.</p>
        <form action="<%=request.getContextPath()%>/guardar-session" method="post">
            <div>
                <label for="nombre">Ingresa tu nombre de sesión:</label>
                <div>
                    <input type="text" name="nombre" id="nombre">
                </div>
            <div>
            <div>
                <input type="submit" value="Enviar">
            </div>
        </form>
    </body>
</html>