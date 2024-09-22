package org.mlorenzo.java.jdbc.repositorio;

import org.mlorenzo.java.jdbc.modelo.Usuario;
import org.mlorenzo.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// En este caso, para cada operación sobre la base de datos, se usa la misma conexión
// En este caso, el método privado "getConnection" devuelve siempre la misma instancia de la conexión(es un singleton)

public class UsuarioRepositorioImpl implements Repositorio<Usuario> {

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        // Como estos recursos implementan la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar estos recursos automáticamente
        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")){

            while(rs.next()) {
                Usuario c = crearUsuario(rs);
                usuarios.add(c);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public Usuario porId(Long id) {
        Usuario usuario = null;

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM usuarios WHERE id = ?")) {
            stmt.setLong(1, id); // El primer y único parámetro de entrada, 1, se asocia con la primera interrogación de la consulta SQL

            // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
            try(ResultSet rs = stmt.executeQuery()) {
                // Usamos "if" en vez de "while" porque en este caso la consulta sólo devuelve un registro
                if (rs.next())
                    usuario = crearUsuario(rs);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // Realiza las operaciones de "insert"(si no existe el id del usuario) y "update"(si existe el id del usuario)
    @Override
    public void guardar(Usuario usuario) {
        String sql = (usuario.getId() != null && usuario.getId() > 0)
                ? "UPDATE usuarios SET username = ?, password = ?, email = ? WHERE id = ?"
                : "INSERT INTO usuarios(username, password, email) VALUES(?, ?, ?)";

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            // El primer parámetro de entrada, 1, se asocia con la primera interrogación de la sentencia SQL, el segundo parámetro de entrada, 2, se asocia con la segunda interrogación de la sentencia SQL y así sucesivamente
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());

            if(usuario.getId() != null && usuario.getId() > 0)
                stmt.setLong(4, usuario.getId());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
            stmt.setLong(1, id); // El primer y único parámetro de entrada, 1, se asocia con la primera interrogación de la sentencia SQL
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mapea un registro de la tabla "usuarios" con objeto de tipo "Usuario"
    private Usuario crearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id")); // Otra opción equivalente es usar el método "getLong" pasándole el índice de la columna de la tabla correspondiente(1)
        u.setUsername(rs.getString("username")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(2)
        u.setPassword(rs.getString("password")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(3)
        u.setEmail(rs.getString("email")); // Otra opción equivalente es usar el método "getString" pasándole el índice de la columna de la tabla correspondiente(4)
        return u;
    }

    // Método que devuelve un singleton de la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }
}
