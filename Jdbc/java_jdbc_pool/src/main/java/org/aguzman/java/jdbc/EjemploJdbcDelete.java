package org.aguzman.java.jdbc;

import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.Repositorio;


public class EjemploJdbcDelete {

    public static void main(String[] args) {
        Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
        System.out.println("========== listar ==========");
        repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

        System.out.println("========== obtener por id ==========");
        System.out.println(repositorio.porId(2L));

        System.out.println("========== eliminar producto existente ==========");
        repositorio.eliminar(3L);
        System.out.println("Producto eliminado con éxito");
        repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

    }
}
