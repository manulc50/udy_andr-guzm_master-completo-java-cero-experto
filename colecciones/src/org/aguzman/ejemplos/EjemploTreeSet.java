package org.aguzman.ejemplos;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

// Los TreeSets no mantienen el orden de inserción de los elementos pero sí mantienen el orden natural(por defecto de forma ascendente) de ellos, y tampoco permiten elementos duplicados
// Los TreeSets sólo adminten elementos que implementen la interfaz "Comparable" para que puedan realizar las ordenaciones de sus elementos de forma natural
// Tienen un coste de rendimiento porque, cada vez que se modifican, ya sea por una nueva inserción o por una eliminación, implica volver a ordenar sus elementos de forma natural
// Este coste de rendimiento es una desventaja en comparación con los HashSet, ya que los HashSet son muy rápidos a la hora de insertar y eliminar elementos debido y no realizan ordenamientos
// La ventaja de los TreeSets es que siempre mantienen ordenados sus elementos de forma natural
// Nota: Un TreeSet detecta si un elemento está duplicado a partir de la implementación de la interfaz "Comparable"
// Es decir, si la implementación del método "compareTo" devuelve un 0, significa que el elemento está duplicado

public class EjemploTreeSet {

    public static void main(String[] args) {
        // Esta función lambda implementa el método "compare" de la interfaz funcional "Comparator"
        // Esta función lambda es para que la ordenación natural(por defecto de forma ascendente) de los elementos del TreeSet se haga de forma descendente
        Set<String> ts = new TreeSet<>((a, b) -> b.compareTo(a)); // Otra opción equivalente a usar esta función lambda es usar la expresión "Comparator.reverseOrder()"

        ts.add("uno");
        ts.add("dos");
        ts.add("tres");
        ts.add("tres");
        ts.add("cuatro");
        ts.add("cinco");

        System.out.println("ts = " + ts);

        // El método "reverseOrder" de la clase "Comparator" nos permite realizar la ordenación natural de los elementos del TreeSet de forma descendente en vez de usar la forma por defecto ascendente
        Set<Integer> numeros = new TreeSet<>(Comparator.reverseOrder()); // Otra opción equivalente a usar la expresión "Comparator.reverseOrder()" es usar la función lambda "(a, b) -> b.compareTo(a)"

        numeros.add(1);
        numeros.add(5);
        numeros.add(4);
        numeros.add(2);
        numeros.add(3);
        numeros.add(10);

        System.out.println("numeros = " + numeros);

    }
}
