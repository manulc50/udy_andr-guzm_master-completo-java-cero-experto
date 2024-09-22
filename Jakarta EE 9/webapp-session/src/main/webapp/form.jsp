<%@page contentType="text/html" pageEncoding="UTF-8"
    import="java.util.*, java.time.format.*, org.aguzman.apiservlet.webapp.session.models.*"%>
<%
    List<Categoria> categorias = (List<Categoria>)request.getAttribute("categorias");
    Map<String, String> errores = (Map<String, String>)request.getAttribute("errores");
    Producto producto = (Producto)request.getAttribute("producto");
    String fechaStr = (producto != null && producto.getFechaRegistro() != null)
        ? producto.getFechaRegistro().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Formulario productos</title>
    </head>
    <body>
        <h1>Formulario productos</h1>
        <form action="<%=request.getContextPath()%>/productos/form" method="post">
            <div>
                <label for="nombre">Nombre</label>
                <div>
                    <input type="text" name="nombre" id="nombre" value="<%=producto != null ? producto.getNombre() : ""%>">
                </div>
                <% if(errores != null && errores.containsKey("nombre")) { %>
                <div style="color: red;"><%=errores.get("nombre")%></div>
                <% } %>
            </div>
            <div>
                <label for="precio">Precio</label>
                <div>
                    <input type="number" name="precio" id="precio" value="<%=producto != null ? producto.getPrecio() : ""%>">
                </div>
                <% if(errores != null && errores.containsKey("precio")) { %>
                <div style="color: red;"><%=errores.get("precio")%></div>
                <% } %>
            </div>
            <div>
                <label for="sku">Sku</label>
                <div>
                    <input type="text" name="sku" id="sku" value="<%=producto != null ? producto.getSku() : ""%>">
                </div>
                <% if(errores != null && errores.containsKey("sku")) { %>
                <div style="color: red;"><%=errores.get("sku")%></div>
                <% } %>
            </div>
            <div>
                <label for="fecha_registro">Fecha Registro</label>
                <div>
                    <input type="date" name="fecha_registro" id="fecha_registro" value="<%=fechaStr%>">
                </div>
                <% if(errores != null && errores.containsKey("fecha_registro")) { %>
                <div style="color: red;"><%=errores.get("fecha_registro")%></div>
                <% } %>
            </div>
            <div>
                <label for="categoria">Categoría</label>
                <div>
                    <select name="categoria" id="categoria">
                        <option value="0">--- seleccionar ---</option>
                        <% for(Categoria categoria: categorias) { %>
                        <option value="<%=categoria.getId()%>"
                            <%=(producto != null && categoria.getId().equals(producto.getCategoria().getId()))
                                ? "selected" : ""%>><%=categoria.getNombre()%></option>
                        <% } %>
                    </select
                </div>
                <% if(errores != null && errores.containsKey("categoria")) { %>
                <div style="color: red;"><%=errores.get("categoria")%></div>
                <% } %>
            </div>
            <div>
                <input type="submit" value="<%=(producto == null || producto.getId() == 0) ? "Crear" : "Editar"%>">
                <!-- Campo oculto para poder saber en el método del servlet que procesa este formulario si se tiene que hacer una actualización de un producto existente o se tiene que persistir un nuevo producto -->
                <input type="hidden" name="id" value="<%=(producto != null) ? producto.getId() : 0%>"
            </div>
        </form>
    </body>
</html>