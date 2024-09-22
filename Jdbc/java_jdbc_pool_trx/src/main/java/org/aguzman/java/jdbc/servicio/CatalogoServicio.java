package org.aguzman.java.jdbc.servicio;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.repositorio.CategoriaRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.Repositorio;
import org.aguzman.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

// Nota: Las operaciones sobre la base de datos que realizan modificaciones("guardar" y "eliminar") sí requieren transacciones
// Nota: Las operaciones sobre la base de datos que son consultas("listar" y "porId") no requieren transacciones

public class CatalogoServicio implements Servicio {
    private final Repositorio<Producto> productoRepositorio;
    private final Repositorio<Categoria> categoriaRepositorio;

    public CatalogoServicio() {
        this.productoRepositorio = new ProductoRepositorioImpl();
        this.categoriaRepositorio = new CategoriaRepositorioImpl();
    }

    @Override
    public List<Producto> listarProductos() throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            productoRepositorio.setConn(conn);
            return productoRepositorio.listar();
        }
    }

    @Override
    public Producto porProductoId(Long id) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            productoRepositorio.setConn(conn);
            return productoRepositorio.porId(id);
        }
    }

    @Override
    public Producto guardarProducto(Producto producto) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            productoRepositorio.setConn(conn);

            // Para poder realizar transacciones, tenemos que manejar de forma manual los commits que se hacen a la base de datos
            // Para ello, tenemos que establecer la propiedad "autoCommit" de la conexión a false(por defecto tiene el valor true)
            if (conn.getAutoCommit())
                conn.setAutoCommit(false);

            Producto nuevoProducto = null;
            // Bloque try-catch para la transacción
            try {
                nuevoProducto = productoRepositorio.guardar(producto);
                conn.commit(); // Hacemos commit de la transacción
            }
            catch (SQLException e) {
                // Nota: Aquí se debe hacer el rollback en caso de que falle la transacción porque estamos dentro del bloque "try-with_resources" y, por lo tanto, la conexión a la base de datos sigue abierta para permitir hacer ese rollback
                conn.rollback();
                e.printStackTrace();
            }

            return nuevoProducto;
        }
    }

    @Override
    public void eliminarProducto(Long id) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            productoRepositorio.setConn(conn);

            // Para poder realizar transacciones, tenemos que manejar de forma manual los commits que se hacen a la base de datos
            // Para ello, tenemos que establecer la propiedad "autoCommit" de la conexión a false(por defecto tiene el valor true)
            if (conn.getAutoCommit())
                conn.setAutoCommit(false);

            // Bloque try-catch para la transacción
            try {
                productoRepositorio.eliminar(id);
                conn.commit(); // Hacemos commit de la transacción
            }
            catch (SQLException e) {
                // Nota: Aquí se debe hacer el rollback en caso de que falle la transacción porque estamos dentro del bloque "try-with_resources" y, por lo tanto, la conexión a la base de datos sigue abierta para permitir hacer ese rollback
                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Categoria> listarCategorias() throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            categoriaRepositorio.setConn(conn);
            return categoriaRepositorio.listar();
        }
    }

    @Override
    public Categoria porCategoriaId(Long id) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            categoriaRepositorio.setConn(conn);
            return categoriaRepositorio.porId(id);
        }
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            categoriaRepositorio.setConn(conn);

            // Para poder realizar transacciones, tenemos que manejar de forma manual los commits que se hacen a la base de datos
            // Para ello, tenemos que establecer la propiedad "autoCommit" de la conexión a false(por defecto tiene el valor true)
            if (conn.getAutoCommit())
                conn.setAutoCommit(false);

            Categoria nuevaCategoria = null;
            // Bloque try-catch para la transacción
            try {
                nuevaCategoria = categoriaRepositorio.guardar(categoria);
                conn.commit(); // Hacemos commit de la transacción
            }
            catch (SQLException e) {
                // Nota: Aquí se debe hacer el rollback en caso de que falle la transacción porque estamos dentro del bloque "try-with_resources" y, por lo tanto, la conexión a la base de datos sigue abierta para permitir hacer ese rollback
                conn.rollback();
                e.printStackTrace();
            }

            return nuevaCategoria;
        }
    }

    @Override
    public void eliminarCategoria(Long id) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            categoriaRepositorio.setConn(conn);

            // Para poder realizar transacciones, tenemos que manejar de forma manual los commits que se hacen a la base de datos
            // Para ello, tenemos que establecer la propiedad "autoCommit" de la conexión a false(por defecto tiene el valor true)
            if (conn.getAutoCommit())
                conn.setAutoCommit(false);

            // Bloque try-catch para la transacción
            try {
                categoriaRepositorio.eliminar(id);
                conn.commit(); // Hacemos commit de la transacción
            }
            catch (SQLException e) {
                // Nota: Aquí se debe hacer el rollback en caso de que falle la transacción porque estamos dentro del bloque "try-with_resources" y, por lo tanto, la conexión a la base de datos sigue abierta para permitir hacer ese rollback
                conn.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {
        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            productoRepositorio.setConn(conn);
            categoriaRepositorio.setConn(conn);

            // Para poder realizar transacciones, tenemos que manejar de forma manual los commits que se hacen a la base de datos
            // Para ello, tenemos que establecer la propiedad "autoCommit" de la conexión a false(por defecto tiene el valor true)
            if (conn.getAutoCommit())
                conn.setAutoCommit(false);

            // Bloque try-catch para la transacción
            try {
                Categoria nuevaCategoria = categoriaRepositorio.guardar(categoria);
                producto.setCategoria(nuevaCategoria);
                productoRepositorio.guardar(producto);
                conn.commit(); // Hacemos commit de la transacción
            }
            catch (SQLException e) {
                // Nota: Aquí se debe hacer el rollback en caso de que falle la transacción porque estamos dentro del bloque "try-with_resources" y, por lo tanto, la conexión a la base de datos sigue abierta para permitir hacer ese rollback
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
