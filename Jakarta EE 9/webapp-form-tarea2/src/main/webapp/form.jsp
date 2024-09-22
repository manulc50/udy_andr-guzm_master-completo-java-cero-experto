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
        <title>Formulario de productos</title>
        <!-- Cargamos el CSS de Bootstrap -->
        <link href="<%=request.getContextPath() /* Nos da el nombre del proyecto */%>/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h3>Formulario de productos</h3>
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

            <!-- Por defecto, el tipo de petición http que envía un formulario es Get -->
            <!-- Pero aquí estamos indicando que sea de tipo Post -->
            <form action="<%=request.getContextPath() /* Nos da el nombre del proyecto */%>/crear" method="POST">
                <!-- Nota: El atributo "name" es importante indicarlo en los elmentos HTML para que los datos introducidos por
                     el usuario puedan viajar en la url de la petición http hacia el servlet correspondiente que los procese -->
                <!-- Nota: El atribtuo "for" de los elementos "label" sirve para asociar un label con otro elemento HTML a través
                     del id de ese elemento HTML. De esta forma, si hacemos click en el label, el foco se establece en el elemento
                     HTML asociado -->
                <!-- Nota: En el lenguaje "EL"("Expression Language" - ${""}), "param" se usa para propiedades que no son listas y "paramValues" se usa para propiedades que sí son listas -->
                <div class="row mb-3">
                    <label for="nombre" class="col-form-label col-sm-2">Nombre</label>
                    <div class="col-sm-4">
                        <input type="text" name="nombre" id="nombre" class="form-control" value="${param.nombre}">
                    </div>
                    <%
                        // "out" es un objeto de tipo "PrintWritter" que viene incluido dentro del JSP
                        if(errores != null && errores.containsKey("nombre"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("nombre") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="precio" class="col-form-label col-sm-2">Precio</label>
                    <div class="col-sm-4">
                        <input type="text" name="precio" id="precio" class="form-control" value="${param.precio}">
                    </div>
                    <%
                        // "out" es un objeto de tipo "PrintWritter" que viene incluido dentro del JSP
                        if(errores != null && errores.containsKey("precio"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("precio") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="fabricante" class="col-form-label col-sm-2">Fabricante</label>
                    <div class="col-sm-4">
                        <input type="text" name="fabricante" id="fabricante" class="form-control" value="${param.fabricante}">
                    </div>
                    <%
                        // "out" es un objeto de tipo "PrintWritter" que viene incluido dentro del JSP
                        if(errores != null && errores.containsKey("fabricante"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("fabricante") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <label for="categoria" class="col-form-label col-sm-2">Categoría</label>
                    <div class="col-sm-4">
                        <!-- Permite la selección única -->
                        <select name="categoria" id="categoria" class="form-select">
                            <!-- El atributo "selected" nos permite seleccionar por defecto una opción(selección única) -->
                            <option value="">-- seleccionar categoría --</option>
                            <option value="tecnologia" ${param.categoria.equals("tecnologia") ? "selected" : ""}>Tecnología</option>
                            <option value="ropa" ${param.categoria.equals("ropa") ? "selected" : ""}>Ropa</option>
                            <option value="mueble" ${param.categoria.equals("mueble") ? "selected" : ""}>Mueble</option>
                        </select>
                    </div>
                    <%
                        if(errores != null && errores.containsKey("categoria"))
                            out.println("<small class='alert alert-danger col-sm-4'>" + errores.get("categoria") + "</small>");
                    %>
                </div>
                <div class="row mb-3">
                    <div>
                        <input type="submit" value="Enviar" class="btn btn-primary">
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>