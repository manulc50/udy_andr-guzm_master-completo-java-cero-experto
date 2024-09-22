package org.aguzman.apiservlet.webapp.session.repositories;

import org.aguzman.apiservlet.webapp.session.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Para trabajar con transacciones, debe haber una única conexión para todas las operaciones que se hagan sobre la base de datos desde una petición http
// Nota: Por defecto, cada vez que se ejecuta el método "executeUpdate", de forma automática se hace un "commit" para que queden reflejados los cambios en la base de datos
// Ésto es debido a que por defecto la propiedad "autoCommit" del objeto de la conexión(objeto "Connection") tiene el valor true

public class CategoriaRepositoryJdbcImpl implements Repository<Categoria> {

    private final Connection conn;

    public CategoriaRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();

        // Como estos recursos implementan la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar estos recursos automáticamente
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")){

            while(rs.next()) {
                Categoria c = crearCategoria(rs);
                categorias.add(c);
            }

        }

        return categorias;
    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categorias WHERE id = ?")) {
            stmt.setLong(1, id); // El primer y único parámetro de entrada, 1, se asocia con la primera interrogación de la consulta SQL

            // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
            try(ResultSet rs = stmt.executeQuery()) {
                // Usamos "if" en vez de "while" porque en este caso la consulta sólo devuelve un registro
                if (rs.next())
                    categoria = crearCategoria(rs);
            }
        }

        return categoria;
    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    @Override
    public void guardar(Categoria categoria) throws SQLException {

    }

    // Como estamos manejando transacciones, en lugar de capturar aquí las excepciones, las lanzamos para que sean capturadas por el "catch" de la transacción
    @Override
    public void eliminar(Long id) throws SQLException {

    }

    // Mapea un registro de la tabla "categorias" con objeto de tipo "Categoria"
    private Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId(rs.getLong("id")); // Otra opción equivalente es usar el método "getLong" pasándole el índice de la columna de la tabla correspondiente(1)
        c.setNombre(rs.getString("nombre")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(2)
        return c;
    }
}
