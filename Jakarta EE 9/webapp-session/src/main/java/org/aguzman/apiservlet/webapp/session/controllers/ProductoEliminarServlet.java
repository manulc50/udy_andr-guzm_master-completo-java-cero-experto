package org.aguzman.apiservlet.webapp.session.controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.session.models.Producto;
import org.aguzman.apiservlet.webapp.session.services.ProductoService;
import org.aguzman.apiservlet.webapp.session.services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/productos/eliminar")
public class ProductoEliminarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Esta conexión a la base de datos se añade a la petición http desde el filtro "ConexionFilter"
        Connection conn = (Connection)req.getAttribute("conn");
        ProductoService productoService = new ProductoServiceJdbcImpl(conn);

        long id = Long.parseLong(req.getParameter("id"));

        Optional<Producto> oProducto = productoService.porProductoId(id);
        if(oProducto.isPresent()) {
            productoService.eliminarProducto(id);
            // Redirige al servlet asociado a la ruta "/productos", es decir, redirige a este servlet con una nueva petición http de tipo get
            resp.sendRedirect(req.getContextPath() + "/productos");
        }
        else {
            // Envía una vista de error con el código de respuesta 404(NOT_FOUND)
            // El mensaje de error es opcional. Si no se especifica, se muestra uno por defecto
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("No existe el producto con id %d en la base de datos",id));
        }

    }
}
