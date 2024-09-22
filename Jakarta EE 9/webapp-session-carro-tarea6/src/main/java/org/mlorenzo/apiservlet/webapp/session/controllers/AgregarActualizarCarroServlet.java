package org.mlorenzo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mlorenzo.apiservlet.webapp.session.models.Carro;
import org.mlorenzo.apiservlet.webapp.session.models.ItemCarro;
import org.mlorenzo.apiservlet.webapp.session.models.Producto;
import org.mlorenzo.apiservlet.webapp.session.services.ProductoService;
import org.mlorenzo.apiservlet.webapp.session.services.ProductoServiceImpl;

import java.io.IOException;
import java.util.Optional;


@WebServlet({"/agregar-carro", "/actualizar-carro"})
public class AgregarActualizarCarroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        ProductoService productoService = new ProductoServiceImpl();
        Optional<Producto> optionalProducto = productoService.porId(id);

        if(optionalProducto.isPresent()) {
            ItemCarro item = new ItemCarro(1, optionalProducto.get());

            HttpSession session = req.getSession();
            Carro carro = (Carro)session.getAttribute("carro");
            if(carro == null) {
                carro = new Carro();
                // Ahora, el objeto "carro" es un atributo de sesión y todas las modificaciones que se hagan sobre este objeto, quedarán también reflejados en este atributo de sesión de forma automática
                session.setAttribute("carro", carro);
            }
            carro.addItem(item);
        }

        resp.sendRedirect(req.getContextPath() + "/carro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Carro carro = (Carro)session.getAttribute("carro");

        // 2 formas de eliminar los ítems de productos
        // Primera forma
        /*
        List<ItemCarro> itemsAEliminar = carro.getItems().stream()
                .filter(i -> {
                    final String eliminar = req.getParameter("eliminar_" + i.getProducto().getId());
                    return eliminar != null && eliminar.equals("on");
                })
                .collect(Collectors.toList());

        carro.getItems().removeAll(itemsAEliminar);*/

        // Segunda forma
        carro.getItems().removeIf(i -> {
            final String eliminar = req.getParameter("eliminar_" + i.getProducto().getId());
            return eliminar != null && eliminar.equals("on");
        });

        carro.getItems().forEach(i -> {
            final int cantidad = Integer.parseInt(req.getParameter("cantidad_" + i.getProducto().getId()));
            i.setCantidad(cantidad);
        });

        resp.sendRedirect(req.getContextPath() + "/carro.jsp");
    }
}
