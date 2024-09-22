package org.aguzman.ejemplos;

import org.aguzman.ejemplos.modelo.Alumno;

import java.util.*;

// Los ArrayLists y los LinkedList mantienen el orden de inserción de los elementos y permiten elementos duplicados

// Los ArrayLists están implementados usando arrays o arreglos(mejor rendimiento en el acceso a los datos pero peor rendimiento para añadir o eliminar elementos en la parte intermedia de la lista)
// Los LinkedList están implementados usando listas doblemente enlazadas(peor rendimiento en el acceso a los datos pero mejor rendimiento para añadir o eliminar elementos en la parte intermedia de la lista)

// Este ejemplo se puede aplicar a cualquier colección de tipo List(ArrayList o LinkedList)

public class EjemploList {
    public static void main(String[] args) {
        List<Alumno> la = new ArrayList<>();
        //List<Alumno> la = new LinkedList<>();

        System.out.println(la + ", size= " + la.size());
        System.out.println("está vacía?: " + la.isEmpty());

        la.add(new Alumno("Pato", 5));
        la.add(new Alumno("Cata", 6));
        la.add(new Alumno("Lucy", 4));
        la.add(2, new Alumno("Jano", 7)); // Se inserta en la posición de "Lucy" y "Lucy" se desplaza a la posición 3
        la.add(new Alumno("Andrés", 3));
        la.add(new Alumno("Zeus", 2));

        la.set(3, new Alumno("Zeus", 2)); // Establece este alumno en la posición 3 de la lista. "Lucy" ya no existe

        System.out.println(la + ", size= " + la.size());

        // Este método utiliza por debajo el método "equals" de la clase "Alumno" para poder saber qué elemento tiene que eliminar
        //la.remove(new Alumno("Jano", 7)); // Elimina a "Jano" de la lista usando un objeto "Alumno"
        la.remove(2); // Elimina a "Jano" de la lista usando su índice

        System.out.println(la + ", size= " + la.size());

        // Este método utiliza por debajo el método "equals" de la clase "Alumno" para poder comparar elementos
        boolean b = la.contains(new Alumno("Jano", 7)); // False porque se eliminó previamente de la lista
        System.out.println("la lista tiene a \"Jano\"(nota 7)?: " + b);

        b = la.contains(new Alumno("Cata", 3)); // False porque en la lista no hay ningún objeto de tipo "Alumno" con nombre "Cata" y nota 3
        System.out.println("la lista tiene a \"Cata\"(nota 3)?: " + b);

        b = la.contains(new Alumno("Cata", 6)); // True porque en la lista hay un objeto de tipo "Alumno" con nombre "Cata" y nota 6
        System.out.println("la lista tiene a \"Cata\"(nota 6)?: " + b);

        // Pasamos de una lista a un array
        Object[] a = la.toArray();

        for(int i=0; i < a.length; i++)
            System.out.println("Desde el arreglo = " + a[i]);

    }
}
