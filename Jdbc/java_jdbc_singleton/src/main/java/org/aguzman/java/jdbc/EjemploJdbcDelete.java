package org.aguzman.java.jdbc;

import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.Repositorio;
import org.aguzman.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJdbcDelete {

    public static void main(String[] args) {

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        // Conexión con la base de datos
        try (Connection conn = ConexionBaseDatos.getInstance()) {
            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("========== listar ==========");
            repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

            System.out.println("========== obtener por id ==========");
            System.out.println(repositorio.porId(2L));

            System.out.println("========== eliminar producto existente ==========");
            repositorio.eliminar(15L);
            System.out.println("Producto eliminado con éxito");
            repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
