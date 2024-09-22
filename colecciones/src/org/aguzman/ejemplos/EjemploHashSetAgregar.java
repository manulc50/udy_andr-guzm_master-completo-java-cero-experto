package org.aguzman.ejemplos;

import java.util.HashSet;
import java.util.Set;


// Los HashSets no mantienen el orden natural de los elementos ni tampoco el orden de su inserción, y no permiten elementos duplicados
// Nota: Un HashSet detecta si un elemento está duplicado a partir de la implementación de los métodos "hashCode" e "equals" de la clase de esos elementos
// Es decir, si dos elementos del HashSet tienen el mismo "hashCode", a continaución se invoca al método "equals" sobre ellos, y si dicho método dice que son iguales,
// el elemento no se inserta en ese HashSet

public class EjemploHashSetAgregar {

    public static void main(String[] args) {
        Set<String> hs = new HashSet<>();

        // true
        System.out.println(hs.add("uno"));
        System.out.println(hs.add("dos"));
        System.out.println(hs.add("tres"));
        System.out.println(hs.add("cuatro"));
        System.out.println(hs.add("cinco"));

        System.out.println(hs);

        //Collection.sort(hs); // Error: No se puede ordenar sets. Sólo se puede ordenar listas

        // false porque es un elemento duplicado
        boolean b = hs.add("tres");
        System.out.println("Permite elementos duplicados?: " + b);

        System.out.println(hs);

        Set<Integer> numeros = new HashSet<>();

        numeros.add(1);
        numeros.add(5);
        numeros.add(4);
        numeros.add(2);
        numeros.add(3);
        numeros.add(10);
        numeros.add(3);

        System.out.println("numeros = " + numeros);

    }
}
