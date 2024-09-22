package org.aguzman.apiservlet.webapp.headers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.headers.models.Producto;
import org.aguzman.apiservlet.webapp.headers.services.ProductoService;
import org.aguzman.apiservlet.webapp.headers.services.ProductoServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/productos.json") // En este caso, ".json" forma parte del nombre de la ruta del servlet. No es una extensión de un archivo JSON
public class ProductoJsonServlet extends HttpServlet {

    // Sirve un json con una lista de productos
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService productoService = new ProductoServiceImpl();
        List<Producto> productos = productoService.listar();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(productos); // Crea un objeto JSON en formato String con el contenido de la lista "productos"

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    // Procesa la información del json, que viaja en el cuerpo o body de la petición http, para convertirlo en un objeto de tipo Producto y, a continuación, sirve un documento HTML con los datos de ese objeto
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream jsonStream = req.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        Producto producto = mapper.readValue(jsonStream, Producto.class);

        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <title>Detalle del producto desde json</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <h1>Detalle del producto desde json</h1>");
            out.println("        <ul>");
            out.println("            <li>Id: " + producto.getId() + "</li>");
            out.println("            <li>Nombre: " + producto.getNombre() + "</li>");
            out.println("            <li>Tipo: " + producto.getTipo() + "</li>");
            out.println("            <li>Precio: " + producto.getPrecio() + "</li>");
            out.println("        </ul>");
            out.println("    </body>");
            out.println("</html>");
        }
    }
}
