package org.aguzman.apiservlet.webapp.session.repositories;

import org.aguzman.apiservlet.webapp.session.models.Categoria;
import org.aguzman.apiservlet.webapp.session.models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Para trabajar con transacciones, debe haber una única conexión para todas las operaciones que se hagan sobre la base de datos desde una petición http
// Nota: Por defecto, cada vez que se ejecuta el método "executeUpdate", de forma automática se hace un "commit" para que queden reflejados los cambios en la base de datos
// Ésto es debido a que por defecto la propiedad "autoCommit" del objeto de la conexión(objeto "Connection") tiene el valor true

public class ProductoRepositoryJdbcImpl implements Repository<Producto> {

    private final Connection conn;

    public ProductoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        // Como estos recursos implementan la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar estos recursos automáticamente
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.*,c.nombre as categoria FROM productos as p " +
                    "INNER JOIN categorias c ON(p.categoria_id = c.id) ORDER BY p.id")){

            while(rs.next()) {
                Producto p = crearProducto(rs);
                productos.add(p);
            }

        }

        return productos;
    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = conn.prepareStatement("SELECT p.*,c.nombre as categoria FROM productos as p " +
                "INNER JOIN categorias c ON(p.categoria_id = c.id) WHERE p.id = ?")) {
            stmt.setLong(1, id); // El primer y único parámetro de entrada, 1, se asocia con la primera interrogación de la consulta SQL

            // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
            try(ResultSet rs = stmt.executeQuery()) {
                // Usamos "if" en vez de "while" porque en este caso la consulta sólo devuelve un registro
                if (rs.next())
                    producto = crearProducto(rs);
            }
        }

        return producto;
    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    // Realiza las operaciones de "insert"(si no existe el id del producto) y "update"(si existe el id del producto)
    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql = (producto.getId() != null && producto.getId() > 0)
                ? "UPDATE productos SET nombre = ?, precio = ?, categoria_id = ?, sku = ? WHERE id = ?"
                : "INSERT INTO productos(nombre, precio, categoria_id, sku, fecha_registro) VALUES(?, ?, ?, ?, ?)";

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            // El primer parámetro de entrada, 1, se asocia con la primera interrogación de la sentencia SQL, el segundo parámetro de entrada, 2, se asocia con la segunda interrogación de la sentencia SQL y así sucesivamente
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());
            stmt.setString(4, producto.getSku());

            if(producto.getId() != null && producto.getId() > 0)
                stmt.setLong(5, producto.getId());
            else
                stmt.setDate(5, Date.valueOf(producto.getFechaRegistro()));

            stmt.executeUpdate();
        }
    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    @Override
    public void eliminar(Long id) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE id = ?")) {
            stmt.setLong(1, id); // El primer y único parámetro de entrada, 1, se asocia con la primera interrogación de la sentencia SQL
            stmt.executeUpdate();
        }
    }

    // Mapea un registro de la tabla "productos" con objeto de tipo "Producto"
    private Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id")); // Otra opción equivalente es usar el método "getLong" pasándole el índice de la columna de la tabla correspondiente(1)
        p.setNombre(rs.getString("nombre")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(2)
        p.setPrecio(rs.getInt("precio")); // Otra opción equivalente es usar el método "getInt" pasándole el índice de la columna de la tabla correspondiente(3)
        p.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate()); // Otra opción equivalente es usar el método "getDate" pasándole el índice de la columna de la tabla correspondiente(4)
        p.setSku(rs.getString("sku")); // Otra opción equivalente es usar el método "getInt" pasándole el índice de la columna de la tabla correspondiente(6)
        Categoria categoria = new Categoria(rs.getString("categoria")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(7)
        categoria.setId(rs.getLong("categoria_id")); // Otra opción equivalente es usar el método "getInt" pasándole el índice de la columna de la tabla correspondiente(5)
        p.setCategoria(categoria);

        return p;
    }
}
