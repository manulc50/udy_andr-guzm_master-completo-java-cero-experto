package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.headers.models.Producto;
import org.aguzman.apiservlet.webapp.headers.services.ProductoService;
import org.aguzman.apiservlet.webapp.headers.services.ProductoServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/buscar-producto")
public class BuscarProductoServlet extends HttpServlet {

    // Procesa el formulario de búsqueda de un producto
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("producto");

        ProductoService productoService = new ProductoServiceImpl();

        Optional<Producto> encontrado = productoService.buscarProducto(nombre);

        if (encontrado.isPresent()) {
            Producto p = encontrado.get();

            resp.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Producto encontrado</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Producto encontrado</h1>");
                out.println("        <h3>Producto encontrado: " + p.getNombre() + ", precio: $" + p.getPrecio() + "</h3>");
                out.println("    </body>");
                out.println("</html>");
            }
        } else {
            // El mensaje de error es opcional. Si no se especifica, se muestra uno por defecto
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Lo sentimos, no se encontró el producto"); // Código 404
        }
    }
}
