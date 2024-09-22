package org.aguzman.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.session.models.Producto;
import org.aguzman.apiservlet.webapp.session.services.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;


@WebServlet({"/productos.html", "/productos"}) // En este caso, ".html" forma parte del nombre de una de las rutas del servlet. No se trata de una extensión de archivos "HTML"
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Esta conexión a la base de datos se añade a la petición http desde el filtro "ConexionFilter"
        Connection conn = (Connection)req.getAttribute("conn");
        ProductoService productoService = new ProductoServiceJdbcImpl(conn);
        List<Producto> productos = productoService.listarProductos();

        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> optionalUsername = loginService.getUsername(req);

        // Creamos estos atributos para usarlos en la vista "/listar.jsp"
        req.setAttribute("productos", productos);
        req.setAttribute("optionalUsername", optionalUsername);

        // Carga la vista "/listar.jsp"
        getServletContext().getRequestDispatcher("/listar.jsp").forward(req, resp);
    }
}
