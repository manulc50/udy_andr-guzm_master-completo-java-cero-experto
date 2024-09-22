package org.aguzman.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.session.models.Categoria;
import org.aguzman.apiservlet.webapp.session.models.Producto;
import org.aguzman.apiservlet.webapp.session.services.ProductoService;
import org.aguzman.apiservlet.webapp.session.services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Esta conexión a la base de datos se añade a la petición http desde el filtro "ConexionFilter"
        Connection conn = (Connection)req.getAttribute("conn");
        ProductoService productoService = new ProductoServiceJdbcImpl(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        }
        catch (NumberFormatException e) {
            id = 0;
        }

        // Si existe un id mayor que 0, significa que estamos editanto un producto y tenemos que ir a buscarlo a la base de datos
        // En caso contrario, significa que estamos creando un nuevo producto
        if(id > 0) {
            Optional<Producto> optionalProducto = productoService.porProductoId(id);
            // Creamos un atributo de la petición http llamado "producto" con los datos del producto a editar para mostrarlos en los campos del formulario
            optionalProducto.ifPresent(producto -> req.setAttribute("producto", producto));
        }

        // Creamos un atributo de la petición http llamado "categorias" con las categorías obtenidas de la base de datos para mostrarlas en el formulario de la vista
        req.setAttribute("categorias", productoService.listarCategorias());

        // Carga la vista "/form.jsp"
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    // Procesa el formulario
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Esta conexión a la base de datos se añade a la petición http desde el filtro "ConexionFilter"
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService productoService = new ProductoServiceJdbcImpl(conn);

        String nombre = req.getParameter("nombre");

        Integer precio;
        // Controlamos el caso de que el usuario haya dejando en blanco el campo del precio
        try {
            precio = Integer.valueOf(req.getParameter("precio"));
        }
        catch(NumberFormatException e) {
            precio = null;
        }
        String sku = req.getParameter("sku");
        String fechaStr = req.getParameter("fecha_registro"); // La fecha se obtiene en el formato "yyyy-MM-dd"
        long categoriaId = Long.parseLong(req.getParameter("categoria"));

        Map<String, String> errores = new HashMap<>();

        if(nombre == null || nombre.isBlank())
            errores.put("nombre", "El nombre es requerido");

        if(sku == null || sku.isBlank())
            errores.put("sku", "El sku es requerido");
        else if(sku.length() > 10)
            errores.put("sku", "El sku debe tener como máximo 10 caracteres");

        if(fechaStr == null || fechaStr.isBlank())
            errores.put("fecha_registro", "La fecha es requerida");

        if(precio == null)
            errores.put("precio", "El precio es requerido");

        if(categoriaId == 0L)
            errores.put("categoria", "La categoría es requerida");

        // Controlamos el caso de que el usuario haya dejando en blanco el campo de la fecha
        LocalDate fecha;
        try {
            // Creamos una fecha de tipo LocalDate a partir de la fecha en formato string obtenida anteriormente de los parámetros de la petición http
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        catch(DateTimeParseException e) {
            fecha = null;
        }

        // Obtenemos de la petición http el parámetro "id" que se corresponde con el campo oculto "id" del formulario
        // Si este parámetro tiene un valor mayor a 0, significa que es el id del producto a editar
        // Y si tiene el valor 0, significa que se trata de un nuevo producto a peristir en la base de datos
        long id = Long.parseLong(req.getParameter("id"));

        // Establecemos aquí los datos del producto para que, en caso de que falle la validación de los campos del formulario, se muestre en ese formulario los últimos datos introducidos por el usuario
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setSku(sku);
        producto.setFechaRegistro(fecha);
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        producto.setCategoria(categoria);

        if(errores.isEmpty()) {
            productoService.guardarProducto(producto);

            // Redirige al servlet asociado a la ruta "/productos", es decir, redirige a este servlet con una nueva petición http de tipo get
            resp.sendRedirect(req.getContextPath() + "/productos");
        }
        else {
            // Creamos un atributo de la petición http llamado "producto" con los últimos datos erróneos del producto introducidos por el usuario para mostrarlos en los campos del formulario de la vista
            req.setAttribute("producto", producto);
            // Creamos un atributo de la petición http llamado "errores" con los errores de validación del formulario para poder mostrarlos después en la vista
            req.setAttribute("errores", errores);
            doGet(req, resp); // No realiza una petición http de tip Get. Simplemente invoca al método "doGet" que hay arriba
        }
    }
}
