package org.aguzman.ejemplos;

import org.aguzman.ejemplos.modelo.Alumno;

import static java.util.Comparator.comparing;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

// Los TreeSets no mantienen el orden de inserción de los elementos pero sí mantienen el orden natural(por defecto de forma ascendente) de ellos, y tampoco permiten elementos duplicados
// Los TreeSets sólo adminten elementos que implementen la interfaz "Comparable" para que puedan realizar las ordenaciones de sus elementos de forma natural
// Tienen un coste de rendimiento porque, cada vez que se modifican, ya sea por una nueva inserción o por una eliminación, implica volver a ordenar sus elementos de forma natural
// Este coste de rendimiento es una desventaja en comparación con los HashSet, ya que los HashSet son muy rápidos a la hora de insertar y eliminar elementos debido y no realizan ordenamientos
// La ventaja de los TreeSets es que siempre mantienen ordenados sus elementos de forma natural
// Nota: Un TreeSet detecta si un elemento está duplicado a partir de la implementación de la interfaz "Comparable"
// Es decir, si la implementación del método "compareTo" devuelve un 0, significa que el elemento está duplicado

public class EjemploTreeSetComparable {
    public static void main(String[] args) {
        // En vez de usar la comparación por defecto implementada en la clase "Alumno", usamos la implementación indicada aquí en el constructor del TreeSet
        //Set<Alumno> sa = new TreeSet<>((a, b) -> a.getNombre().compareTo(b.getNombre())); // De forma ascendente
        //Set<Alumno> sa = new TreeSet<>((a, b) -> b.getNombre().compareTo(a.getNombre())); // De forma descendente
        // Esta forma sólo está disponible a partir de la versión 8 de Java
        //Set<Alumno> sa = new TreeSet<>(comparing(Alumno::getNombre)); // Forma ascendente. Versión simplificada de la expresión "(Alumno a) -> a.getNombre()"
        Set<Alumno> sa = new TreeSet<>(comparing(Alumno::getNombre).reversed()); // Forma descendente. Versión simplificada de la expresión "(Alumno a) -> a.getNombre()"

        sa.add(new Alumno("Pato", 5));
        sa.add(new Alumno("Cata", 6));
        sa.add(new Alumno("Lucy", 4));
        sa.add(new Alumno("Jano", 7));
        sa.add(new Alumno("Andrés", 3));
        sa.add(new Alumno("Zeus", 2));
        // Nota: Un TreeSet detecta si un elemento está duplicado a partir de la implementación de la interfaz "Comparable"
        // Es decir, si la implementación del método "compareTo" devuelve un 0, significa que el elemento está duplicado
        sa.add(new Alumno("Zeus", 2)); // No se inserta por tratarse de un elemento duplicados

        System.out.println(sa);

        System.out.println("iteramos usando un foreach");
        for(Alumno a: sa)
            System.out.println(a.getNombre());

        System.out.println("iteramos usando while e iterator");
        Iterator<Alumno> it = sa.iterator();
        while(it.hasNext()) {
            Alumno a = it.next();
            System.out.println(a.getNombre());
        }

        System.out.println("iteramos usando el método forEach y una expresión lambda");
        sa.forEach(System.out::println); // Versión simplificada de la expresión "a -> System.out.println(a)"
    }
}
