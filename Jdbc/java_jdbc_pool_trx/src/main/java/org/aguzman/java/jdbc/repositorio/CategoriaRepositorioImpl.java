package org.aguzman.java.jdbc.repositorio;

import org.aguzman.java.jdbc.modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// En este caso, para trabajar con transacciones, debe haber una única conexión para todas las operaciones que se hagan sobre la base de datos
// Nota: Por defecto, cada vez que se ejecuta el método "executeUpdate", de forma automática se hace un "commit" para que queden reflejados los cambios en la base de datos
// Ésto es debido a que por defecto la propiedad "autoCommit" del objeto de la conexión(objeto "Connection") tiene el valor true

public class CategoriaRepositorioImpl implements Repositorio<Categoria> {

    private Connection conn;

    public CategoriaRepositorioImpl() {
    }

    public CategoriaRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();

        // Como estos recursos implementan la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar estos recursos automáticamente
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")) {

            while(rs.next())
                categorias.add(crearCategoria(rs));

        }

        return categorias;
    }

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categorias WHERE id = ?")) {
            stmt.setLong(1, id); // El primer y único parámetro de entrada, 1, se asocia con la primera interrogación de la consulta SQL

            // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
            try(ResultSet rs = stmt.executeQuery()) {
                // Usamos "if" en vez de "while" porque en este caso la consulta sólo devuelve un registro
                if(rs.next())
                    categoria = crearCategoria(rs);
            }
        }

        return categoria;
    }

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
    // Realiza las operaciones de "insert"(si no existe el id de la categoría) y "update"(si existe el id de la categoría)
    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
        String sql = (categoria.getId() != null && categoria.getId() > 0)
                ? "UPDATE categorias SET nombre = ? WHERE id = ?"
                : "INSERT INTO categorias(nombre) VALUES(?)";

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        // "Statement.RETURN_GENERATED_KEYS" es para que, después de ejecutarse la sentencia SQL con el método "executeUpdate", haga una consulta para obtener el id asignado a la nueva categoría que se acaba de crear
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // El primer parámetro de entrada, 1, se asocia con la primera interrogación de la sentencia SQL, el segundo parámetro de entrada, 2, se asocia con la segunda interrogación de la sentencia SQL y así sucesivamente
            stmt.setString(1, categoria.getNombre());

            if(categoria.getId() != null && categoria.getId() > 0)
                stmt.setLong(2, categoria.getId());

            stmt.executeUpdate();

            // Si la operación sobre la base de datos es un "insert", obtenemos el id asignado a la nueva categoría
            if(categoria.getId() == null) {
                try(ResultSet rs = stmt.getGeneratedKeys()) {
                    // Usamos "if" en vez de "while" porque en este caso la consulta sólo devuelve un registro
                    if(rs.next())
                        categoria.setId(rs.getLong(1));
                }
            }
        }

        return categoria;
    }

    // Como en este caso estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción dentro de cada método del sevicio "CatalogoServicio"
    @Override
    public void eliminar(Long id) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id = ?")) {
            // El primer y único parámetro de entrada, 1, se asocia con la primera interrogación de la sentencia SQL
            stmt.setLong(1, id);

            stmt.executeUpdate();
        }
    }

    // Mapea un registro de la tabla "categorias" con objeto de tipo "Categoria"
    private Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id")); // Otra opción equivalente es usar el método "getLong" pasándole el índice de la columna de la tabla correspondiente(1)
        categoria.setNombre(rs.getString("nombre")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(2)

        return categoria;
    }
}
