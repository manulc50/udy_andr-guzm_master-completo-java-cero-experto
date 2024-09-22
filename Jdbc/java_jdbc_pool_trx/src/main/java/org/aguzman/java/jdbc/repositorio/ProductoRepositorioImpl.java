package org.aguzman.java.jdbc.repositorio;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// En este caso, para trabajar con transacciones, debe haber una única conexión para todas las operaciones que se hagan sobre la base de datos
// Nota: Por defecto, cada vez que se ejecuta el método "executeUpdate", de forma automática se hace un "commit" para que queden reflejados los cambios en la base de datos
// Ésto es debido a que por defecto la propiedad "autoCommit" del objeto de la conexión(objeto "Connection") tiene el valor true

public class ProductoRepositorioImpl implements Repositorio<Producto> {

    private Connection conn;

    public ProductoRepositorioImpl() {
    }

    public ProductoRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        // Como estos recursos implementan la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar estos recursos automáticamente
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.*,c.nombre as categoria FROM productos as p " +
                    "inner join categorias c ON(p.categoria_id = c.id)")){

            while(rs.next()) {
                Producto p = crearProducto(rs);
                productos.add(p);
            }

        }

        return productos;
    }

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = conn.prepareStatement("SELECT p.*,c.nombre as categoria FROM productos as p " +
                "inner join categorias c ON(p.categoria_id = c.id) WHERE p.id = ?")) {
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

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
    // Realiza las operaciones de "insert"(si no existe el id del producto) y "update"(si existe el id del producto)
    @Override
    public Producto guardar(Producto producto) throws SQLException {
        String sql = (producto.getId() != null && producto.getId() > 0)
                ? "UPDATE productos SET nombre = ?, precio = ?, categoria_id = ?, sku = ? WHERE id = ?"
                : "INSERT INTO productos(nombre, precio, categoria_id, sku, fecha_registro) VALUES(?, ?, ?, ?, ?)";

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        // "Statement.RETURN_GENERATED_KEYS" es para que, después de ejecutarse la sentencia SQL con el método "executeUpdate", haga una consulta para obtener el id asignado al nuevo productos que se acaba de crear
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // El primer parámetro de entrada, 1, se asocia con la primera interrogación de la sentencia SQL, el segundo parámetro de entrada, 2, se asocia con la segunda interrogación de la sentencia SQL y así sucesivamente
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());
            stmt.setString(4, producto.getSku());

            if(producto.getId() != null && producto.getId() > 0)
                stmt.setLong(5, producto.getId());
            else
                stmt.setDate(5, new Date(producto.getFechaRegistro().getTime()));

            stmt.executeUpdate();

            // Si la operación sobre la base de datos es un "insert", obtenemos el id asignado al nuevo producto
            if(producto.getId() == null) {
                try(ResultSet rs = stmt.getGeneratedKeys()) {
                    // Usamos "if" en vez de "while" porque en este caso la consulta sólo devuelve un registro
                    if(rs.next())
                        producto.setId(rs.getLong(1));
                }
            }
        }

        return producto;
    }

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
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
        // Nota: La propiedad "fechaRegistro" de la clase "Producto" es de tipo "java.util.Date" y este método "getDate" devuelve un objeto de tipo "java.sql.Date", pero extiende de "java.util.Date", así que la asignación es válida
        p.setFechaRegistro(rs.getDate("fecha_registro")); // Otra opción equivalente es usar el método "getDate" pasándole el índice de la columna de la tabla correspondiente(4)
        p.setSku(rs.getString("sku")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(6)
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id")); // Otra opción equivalente es usar el método "getLong" pasándole el índice de la columna de la tabla correspondiente(5)
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }

}
