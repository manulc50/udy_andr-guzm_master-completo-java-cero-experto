package org.aguzman.ejemplos;

import org.aguzman.ejemplos.modelo.Alumno;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// Los HashSets no mantienen el orden natural de los elementos ni tampoco el orden de su inserción, y no permiten elementos duplicados
// Nota: Un HashSet detecta si un elemento está duplicado a partir de la implementación de los métodos "hashCode" e "equals" de la clase de esos elementos
// Es decir, si dos elementos del HashSet tienen el mismo "hashCode", a continaución se invoca al método "equals" sobre ellos, y si dicho método dice que son iguales,
// el elemento no se inserta en ese HashSet

public class EjemploHashSetUnicidad {
    public static void main(String[] args) {
        Set<Alumno> sa = new HashSet<>();

        sa.add(new Alumno("Pato", 5));
        sa.add(new Alumno("Cata", 6));
        sa.add(new Alumno("Lucy", 4));
        sa.add(new Alumno("Jano", 7));
        sa.add(new Alumno("Andrés", 3));
        // Nota: Un HashSet detecta si un elemento está duplicado a partir de la implementación de los métodos "hashCode" e "equals" de la clase de esos elementos
        // Es decir, si dos elementos del HashSet tienen el mismo "hashCode", a continaución se invoca al método "equals" sobre ellos, y si dicho método dice que son iguales,
        // el elemento no se inserta en ese HashSet
        sa.add(new Alumno("Zeus", 2));
        sa.add(new Alumno("Zeus", 2));

        System.out.println(sa);

        // Nota: En las colecciones de tipo Set, no es posible usar un bucle for clásico("for(int i=0; i < sa.size(); i++) { ... }") para iterarlas porque dichas colecciones no están implementadas con arrays o arreglos

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
