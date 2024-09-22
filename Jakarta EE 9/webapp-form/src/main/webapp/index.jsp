<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- Importamos la librería de Java "java.util.Map" para usarla en los "Scriplet" --%>
<%@page import="java.util.Map"%>
<%
    /*
        "Scriplet" - Etiquetas de JSP para añadir código Java
        "request" es un objeto que viene incluido dentro del JSP y hace referencia a la petición http
        El método "getAttribute" devuelve un dato de tipo Object. Por lo tanto, tenemos que hacer un casting al tipo que nos interesa
    */
    Map<String, String> errores = (Map<String, String>)request.getAttribute("errores");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Formulario de usuarios</title>
        <!-- Cargamos el CSS de Bootstrap -->
        <link href="<%=request.getContextPath() /* Nos da el nombre del proyecto */%>/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <h3>Formulario de usuarios</h3>
        <%
            if(errores != null && errores.size() > 0) {
        %>
        <!-- Mostramos una lista con los errores de validación del formulario -->
        <ul class="alert alert-danger mx-5 px-5">
        <% for(String error: errores.values()) { %>
        <li><%=error /* Expresión que imprime por pantalla el contenido de "error"  */%></li>
        <% } %>
        </ul>
        <% } %>

        <div class="px-5">
            <!-- Por defecto, el tipo de petición http que envía un formulario es Get -->
            <!-- Pero aquí estamos indicando que sea de tipo Post -->
            <form action="/webapp-form/registro" method="POST">
                <!-- Nota: El atributo "name" es importante indicarlo en los elmentos HTML para que los datos introducidos por
                     el usuario puedan viajar en la url de la petición http hacia el servlet correspondiente que los procese -->
                <!-- Nota: El atribtuo "for" de los elementos "label" sirve para asociar un label con otro elemento HTML a través
                     del id de ese elemento HTML. De esta forma, si hacemos click en el label, el foco se establece en el elemento
                     HTML asociado -->
                <!-- Nota: En el lenguaje "EL"("Expression Language" - ${""}), "param" se usa para propiedades que no son listas y "paramValues" se usa para propiedades que sí son listas -->
                <div class="row mb-3">
                    <label for="username" class="col-form-label col-sm-2">Usuario</label>
                    <div class="col-sm-4">
                        <input type="text" name="username" id="username" class="form-control" value="${param.username}">
                    </div>
                    <%
                        // "out" es un objeto de tipo "PrintWritter" que viene incluido dentro del JSP
                        if(errores != null && errores.containsKey("username"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("username") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="password" class="col-form-label col-sm-2">Contraseña</label>
                    <div class="col-sm-4">
                        <!-- En este caso no establecemos el valor del atributo "value" por temas de seguridad ya que se trata del campo de la contraseña -->
                        <input type="password" name="password" id="password" class="form-control">
                    </div>
                    <%
                        if(errores != null && errores.containsKey("password"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("password") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="email" class="col-form-label col-sm-2">Email</label>
                    <div class="col-sm-4">
                        <input type="email" name="email" id="email" class="form-control" value="${param.email}">
                    </div>
                    <%
                        if(errores != null && errores.containsKey("email"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("email") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="pais" class="col-form-label col-sm-2">País</label>
                    <div class="col-sm-4">
                        <!-- Permite la selección única -->
                        <select name="pais" id="pais" class="form-select">
                            <!-- El atributo "selected" nos permite seleccionar por defecto una opción(selección única) -->
                            <option value="">-- seleccionar país --</option>
                            <option value="ES" ${param.pais.equals("ES") ? "selected" : ""}>España</option>
                            <option value="MX" ${param.pais.equals("MX") ? "selected" : ""}>México</option>
                            <option value="CL" ${param.pais.equals("CL") ? "selected" : ""}>Chile</option>
                            <option value="AR" ${param.pais.equals("AR") ? "selected" : ""}>Argentina</option>
                            <option value="PE" ${param.pais.equals("PE") ? "selected" : ""}>Perú</option>
                            <option value="CO" ${param.pais.equals("CO") ? "selected" : ""}>Colombia</option>
                            <option value="VE" ${param.pais.equals("VE") ? "selected" : ""}>Venezuela</option>
                        </select>
                    </div>
                    <%
                        if(errores != null && errores.containsKey("pais"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("pais") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="lenguajes" class="col-form-label col-sm-2">Lenguajes de programación</label>
                    <div class="col-sm-4">
                        <!-- Permite la selección múltiple -->
                        <!-- El atributo "selected" nos permite seleccionar por defecto algunas opciones(selección múltiple) -->
                        <!-- Nota: "stream()" convierte una lista en un stream
                                   "anyMatch(...)" devuelve un Optional
                                   "get()" devuelve el valor de ese Optional -->
                        <select name="lenguajes" id="lenguajes" multiple class="form-select">
                            <option value="java" ${paramValues.lenguajes.stream().anyMatch(value -> value.equals("java")).get() ? "selected" : ""}>Java SE</option>
                            <option value="jakartaee" ${paramValues.lenguajes.stream().anyMatch(value -> value.equals("jakartaee")).get() ? "selected" : ""}>Jakarta EE9 </option>
                            <option value="spring" ${paramValues.lenguajes.stream().anyMatch(value -> value.equals("spring")).get() ? "selected" : ""}>Spring Boot</option>
                            <option value="js" ${paramValues.lenguajes.stream().anyMatch(value -> value.equals("js")).get() ? "selected" : ""}>JavaScript</option>
                            <option value="angular" ${paramValues.lenguajes.stream().anyMatch(value -> value.equals("angular")).get() ? "selected" : ""}>Angular</option>
                            <option value="react" ${paramValues.lenguajes.stream().anyMatch(value -> value.equals("react")).get() ? "selected" : ""}>React</option>
                        </select>
                    </div>
                    <%
                        if(errores != null && errores.containsKey("lenguajes"))
                            out.println("<small class='alert alert-danger col-sm-4''>" + errores.get("lenguajes") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <!-- Los checkboxes permiten la selección múltiple -->
                    <!-- Nota: Para agrupar una serie de inputs de tipo "checkbox" en un mismo grupo, tienen que tener el mismo valor
                         establecido en el atributo "name" -->
                    <!-- El atributo "checked" nos permite marcar por defecto checkboxes(selección múltiple) -->
                    <!-- Nota: "stream()" convierte una lista en un stream
                               "anyMatch(...)" devuelve un Optional
                               "get()" devuelve el valor de ese Optional -->
                    <label class="col-form-label col-sm-2">Roles</label>
                    <div class="form-check col-sm-2">
                        <input type="checkbox" name="roles" value="ROLE_ADMIN" class="form-check-input"
                            ${paramValues.roles.stream().anyMatch(value -> value.equals("ROLE_ADMIN")).get() ? "checked" : ""}>
                        <label class="form-check-label">Administrador</label>
                    </div>
                    <div class="form-check col-sm-2">
                        <input type="checkbox" name="roles" value="ROLE_USER" class="form-check-input"
                            ${paramValues.roles.stream().anyMatch(value -> value.equals("ROLE_USER")).get() ? "checked" : ""}>
                        <label class="form-check-label">Usuario</label>
                    </div>
                    <div class="form-check col-sm-2">
                        <input type="checkbox" name="roles" value="ROLE_MODERATOR" class="form-check-input"
                        ${paramValues.roles.stream().anyMatch(value -> value.equals("ROLE_MODERATOR")).get() ? "checked" : ""}>
                        <label class="form-check-label">Moderador</label>
                    </div>
                    <%
                        if(errores != null && errores.containsKey("roles"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("roles") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <!-- Los radiobuttons permiten la selección única -->
                    <!-- Nota: Para agrupar una serie de inputs de tipo "radio" en un mismo grupo, tienen que tener el mismo valor
                         establecido en el atributo "name" -->
                    <label class="col-form-label col-sm-2">Idiomas</label>
                    <div class="form-check col-sm-2">
                        <input type="radio" name="idioma" value="es" class="form-check-input" ${param.idioma.equals("es") ? "checked" : ""}>
                        <label class="form-check-label">Español</label>
                    </div>
                    <div class="form-check col-sm-2">
                        <input type="radio" name="idioma" value="en" class="form-check-input" ${param.idioma.equals("en") ? "checked" : ""}>
                        <label class="form-check-label">Inglés</label>
                    </div>
                    <div class="form-check col-sm-2">
                        <input type="radio" name="idioma" value="fr" class="form-check-input" ${param.idioma.equals("fr") ? "checked" : ""}>
                        <label class="form-check-label">Francés</label>
                    </div>
                    <%
                        if(errores != null && errores.containsKey("idioma"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("idioma") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="habilitar" class="col-form-label col-sm-2">Habilitar</label>
                    <div class="form-check col-sm-2">
                        <input type="checkbox" name="habilitar" id="habilitar" checked class="form-check-input">
                    </div>
                </div>
                <div class="row mb-3">
                    <div>
                        <input type="submit" value="Enviar" class="btn btn-primary">
                    </div>
                </div>
                <!-- Los campos ocultos de un formulario pueden ir en cualquier parte del formulario pero normalmente se
                     colocan al final -->
                <input type="hidden" name="secreto" value="12345">
            </form>
        </div>
    </body>
</html>