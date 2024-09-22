<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*, org.aguzman.apiservlet.webapp.session.models.*"%>
<%
List<Producto> productos = (List<Producto>)request.getAttribute("productos");
Optional<String> optionalUsername = (Optional<String>)request.getAttribute("optionalUsername");
// Obtenemos el valor del atributo "mensaje" de la petición http que se establece en el listener "AplicacionListener"
String mensajeRequest = (String)request.getAttribute("mensaje");
// Obtenemos el valor del atributo global "mensaje" de la aplicación que se establece en el listener "AplicacionListener"
String mensajeApp = (String)getServletContext().getAttribute("mensaje");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Listado de productos</title>
    </head>
    <body>
        <h1>Listado de productos</h1>
        <% if(optionalUsername.isPresent()) { %>
        <p>Hola <%=optionalUsername.get()%></p>
        <p><a href="<%=request.getContextPath()%>/productos/form">crear [+]</a></p>
        <% } %>
        <table>
            <tr>
                <th>id</th>
                <th>nombre</th>
                <th>categoría</th>
                <% if(optionalUsername.isPresent()) { %>
                <th>precio</th>
                <th>agregar</th>
                <th>editar</th>
                <th>eliminar</th>
                <% } %>
            </tr>
            <% for(Producto p: productos) { %>
            <tr>
                <td><%=p.getId()%></td>
                <td><%=p.getNombre()%></td>
                <td><%=p.getCategoria().getNombre()%></td>
                <% if(optionalUsername.isPresent()) { %>
                <td><%=p.getPrecio()%></td>
                <td><a href="<%=request.getContextPath()%>/carro/agregar?id=<%=p.getId()%>">agregar al carro</a></td>
                <td><a href="<%=request.getContextPath()%>/productos/form?id=<%=p.getId()%>">editar</a></td>
                <td><a onclick="return confirm('¿Estás seguro que deseas eliminar?')"
                    href="<%=request.getContextPath()%>/productos/eliminar?id=<%=p.getId()%>">eliminar</a></td>
                <% } %>
            </tr>
            <% } %>
        </table>
        <p><%=mensajeApp%></p>
        <p><%=mensajeRequest%></p>
    </body>
</html>