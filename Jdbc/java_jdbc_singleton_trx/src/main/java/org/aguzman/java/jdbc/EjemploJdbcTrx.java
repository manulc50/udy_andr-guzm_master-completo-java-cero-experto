package org.aguzman.java.jdbc;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.Repositorio;
import org.aguzman.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

// Ejemplo de manejo de transacciones
// Una transacción es un conjunto de operaciones que modifican tablas de la base de datos donde se garantiza que si todas estas operaciones tienen éxito, todos los cambios quedan reflejados en la base de datos,
// pero si alguna de estas operaciones falla, ningún cambio queda reflejado en la base de datos


public class EjemploJdbcTrx {

    public static void main(String[] args) {

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        // Conexión con la base de datos
        try (Connection conn = ConexionBaseDatos.getInstance()) {
            // Para poder realizar transacciones, tenemos que manejar de forma manual los commits que se hacen a la base de datos
            // Para ello, tenemos que establecer la propiedad "autoCommit" de la conexión a false(por defecto tiene el valor true)
            if(conn.getAutoCommit())
                conn.setAutoCommit(false);
            // Bloque try-catch para la transacción
            try {
                Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
                System.out.println("========== listar ==========");
                repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

                System.out.println("========== obtener por id ==========");
                System.out.println(repositorio.porId(2L));

                System.out.println("========== insertar nuevo producto ==========");
                Producto producto = new Producto("Teclado IBM mecánico", 1550, new Date(), "abcde12345");
                Categoria categoria = new Categoria();
                categoria.setId(3L);
                producto.setCategoria(categoria);
                repositorio.guardar(producto);
                System.out.println("Producto guardado con éxito");

                System.out.println("========== actualizar producto existente ==========");
                producto = new Producto();
                producto.setId(6L);
                producto.setNombre("Teclado Corsair K95 mecánico");
                producto.setPrecio(1000);
                producto.setSku("abcdef1234");
                categoria = new Categoria();
                categoria.setId(2L);
                producto.setCategoria(categoria);
                repositorio.guardar(producto);
                System.out.println("Producto actualizado con éxito");
                repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

                conn.commit(); // Hacemos commit de la transacción
            }
            catch(SQLException e) {
                // Nota: Aquí sí podemos hacer el rollback en caso de que falle la transacción porque estamos dentro del bloque "try-with_resources" y, por lo tanto, la conexión a la base de datos sigue abierta para permitir hacer ese rollback
                conn.rollback();
                e.printStackTrace();
            }
        }
        catch (SQLException e) {
            // Nota: Aquí no podemos hacer el rollback en caso de que falle la transacción porque de forma automática se cierra la conexión a la base de datos al final del bloque "try-with-resources"
            e.printStackTrace();
        }
    }
}
