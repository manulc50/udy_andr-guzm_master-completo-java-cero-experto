package org.aguzman.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aguzman.apiservlet.webapp.session.models.Carro;
import org.aguzman.apiservlet.webapp.session.models.ItemCarro;
import org.aguzman.apiservlet.webapp.session.models.Producto;
import org.aguzman.apiservlet.webapp.session.services.ProductoService;
import org.aguzman.apiservlet.webapp.session.services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/carro/agregar")
public class AgregarCarroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        // Esta conexión a la base de datos se añade a la petición http desde el filtro "ConexionFilter"
        Connection conn = (Connection)req.getAttribute("conn");
        ProductoService productoService = new ProductoServiceJdbcImpl(conn);
        Optional<Producto> optionalProducto = productoService.porProductoId(id);

        if(optionalProducto.isPresent()) {
            ItemCarro item = new ItemCarro(1, optionalProducto.get());

            HttpSession session = req.getSession();
            Carro carro = (Carro)session.getAttribute("carro");

            // Se comenta porque ahora el atributo de sesión para el carro se crea en el listener "AplicacionListener"
            /*if(carro == null) {
                carro = new Carro();
                session.setAttribute("carro", carro);
            } */
            carro.addItem(item);
        }

        resp.sendRedirect(req.getContextPath() + "/carro/ver");
    }
}
