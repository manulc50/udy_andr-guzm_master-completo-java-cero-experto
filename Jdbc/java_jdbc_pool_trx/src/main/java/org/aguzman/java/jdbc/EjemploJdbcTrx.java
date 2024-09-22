package org.aguzman.java.jdbc;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.servicio.CatalogoServicio;
import org.aguzman.java.jdbc.servicio.Servicio;

import java.sql.SQLException;
import java.util.Date;

// Ejemplo de manejo de transacciones
// Una transacción es un conjunto de operaciones que modifican tablas de la base de datos donde se garantiza que si todas estas operaciones tienen éxito, todos los cambios quedan reflejados en la base de datos,
// pero si alguna de estas operaciones falla, ningún cambio queda reflejado en la base de datos

public class EjemploJdbcTrx {

    public static void main(String[] args) throws SQLException {
        Servicio servicio = new CatalogoServicio();

        System.out.println("========== listar ==========");
        servicio.listarProductos().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

        System.out.println("========== insertar nuevo producto con nueva categoría ==========");
        Categoria categoria = new Categoria();
        categoria.setNombre("Iluminación");
        Producto producto = new Producto("Lámpara led escritorio", 900, new Date(), "abcdefgh12");
        // Persiste el producto y la categoría y les asigna los nuevos ids generados
        servicio.guardarProductoConCategoria(producto, categoria);
        System.out.println("Producto guardado con éxito: " + producto.getId());
        servicio.listarProductos().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

    }
}
