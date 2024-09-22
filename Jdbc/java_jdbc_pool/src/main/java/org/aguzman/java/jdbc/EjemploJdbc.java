package org.aguzman.java.jdbc;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.Repositorio;

import java.util.Date;

public class EjemploJdbc {

    public static void main(String[] args) {
        Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
        System.out.println("========== listar ==========");
        repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"

        System.out.println("========== obtener por id ==========");
        System.out.println(repositorio.porId(2L));

        System.out.println("========== insertar nuevo producto ==========");
        Producto producto = new Producto("Notebook Omen HP", 2900, new Date());
        Categoria categoria = new Categoria();
        categoria.setId(3L);
        producto.setCategoria(categoria);
        repositorio.guardar(producto);
        System.out.println("Producto guardado con éxito");
        repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "p -> System.out.println(p)"
    }
}
