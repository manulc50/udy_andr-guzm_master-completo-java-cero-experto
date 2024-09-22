package org.aguzman.apiservlet.webapp.cookie.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.cookie.models.Producto;
import org.aguzman.apiservlet.webapp.cookie.services.LoginService;
import org.aguzman.apiservlet.webapp.cookie.services.LoginServiceImpl;
import org.aguzman.apiservlet.webapp.cookie.services.ProductoService;
import org.aguzman.apiservlet.webapp.cookie.services.ProductoServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


@WebServlet({"/productos.html", "/productos"}) // En este caso, ".html" forma parte del nombre de una de las rutas del servlet. No se trata de una extensión de archivos "HTML"
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService productoService = new ProductoServiceImpl();
        List<Producto> productos = productoService.listar();

        LoginService loginService = new LoginServiceImpl();
        Optional<String> optionalUsername = loginService.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <title>Listado de productos</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <h1>Listado de productos</h1>");
            if(optionalUsername.isPresent()) // Si el usuario está autenticado, mostramos este saludo
                out.println("        <p style='color: blue;'>Hola " + optionalUsername.get() + ", Bienvenido!</p>");
            out.println("        <table>");
            out.println("            <tr>");
            out.println("                <th>id</th>");
            out.println("                <th>nombre</th>");
            out.println("                <th>tipo</th>");
            if(optionalUsername.isPresent())
                out.println("                <th>precio</th>"); // El precio sólo se muestra para usuarios autenticados
            out.println("            </tr>");
            productos.forEach(p -> {
                out.println("            <tr>");
                out.println("                <td>" + p.getId() + "</td>");
                out.println("                <td>" + p.getNombre() + "</td>");
                out.println("                <td>" + p.getTipo() + "</td>");
                if(optionalUsername.isPresent())
                    out.println("                <td>" + p.getPrecio() + "</td>"); // El precio sólo se muestra para usuarios autenticados
                out.println("            </tr>");
            });
            out.println("        </table>");
            out.println("    </body>");
            out.println("</html>");
        }
    }
}
