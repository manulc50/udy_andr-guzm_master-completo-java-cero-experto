package org.aguzman.ejemplos;

import org.aguzman.ejemplos.modelo.Alumno;

import java.util.*;

// Los ArrayLists y los LinkedList mantienen el orden de inserción de los elementos y permiten elementos duplicados

// Los ArrayLists están implementados usando arrays o arreglos(mejor rendimiento en el acceso a los datos pero peor rendimiento para añadir o eliminar elementos en la parte intermedia de la lista)
// Los LinkedList están implementados usando listas doblemente enlazadas(peor rendimiento en el acceso a los datos pero mejor rendimiento para añadir o eliminar elementos en la parte intermedia de la lista)

// Este ejemplo se puede aplicar a cualquier colección de tipo List(ArrayList o LinkedList)

public class EjemploListComparableComparator {
    public static void main(String[] args) {
        List<Alumno> la = new ArrayList<>();
        //List<Alumno> la = new LinkedList<>();

        la.add(new Alumno("Pato", 5));
        la.add(new Alumno("Cata", 6));
        la.add(new Alumno("Lucy", 4));
        la.add(new Alumno("Jano", 7));
        la.add(new Alumno("Andrés", 3));
        la.add(new Alumno("Zeus", 2));
        la.add(new Alumno("Zeus", 2));

        System.out.println(la);

        // Ordena los elementos del ArrayList en función de la implementación del método "compareTo" de la interfaz "Comparable" por la clase "Alumno"
        Collections.sort(la);

        System.out.println("iteramos usando un for clásico");
        for(int i=0; i < la.size(); i++)
            System.out.println(la.get(i).getNombre());

        // Ordena los elementos del ArrayList a partir de la implementación de esta función lambda que implementa la interfaz funcional "Comparator"
        //Collections.sort(la, (a, b) -> a.getNombre().compareTo(b.getNombre())); // Forma ascendente
        //Collections.sort(la, (a, b) -> b.getNombre().compareTo(a.getNombre())); // Forma descendente
        //la.sort((a, b) -> a.getNombre().compareTo(b.getNombre())); // Otra forma equivalente a la expresión de arriba "Collections.sort(...)"
        // Otra forma equivalente a las expresiones anteriores "la.sort((a, b) -> ..." y "Collections.sort(...)"
        // Esta forma sólo está disponible a partir de la versión 8 de Java
        //la.sort(Comparator.comparing(Alumno::getNota)); // Forma ascendente. Versión simplificada de la expresión "(Alumno a) -> a.getNota()"
        la.sort(Comparator.comparing(Alumno::getNota).reversed()); // Forma descendente. Versión simplificada de la expresión "(Alumno a) -> a.getNota()"

        System.out.println("iteramos usando un foreach");
        for(Alumno a: la)
            System.out.println(a.getNombre());

        System.out.println("iteramos usando while e iterator");
        Iterator<Alumno> it = la.iterator();
        while(it.hasNext()) {
            Alumno a = it.next();
            System.out.println(a.getNombre());
        }

        System.out.println("iteramos usando el método forEach y una expresión lambda");
        la.forEach(System.out::println); // Versión simplificada de la expresión "a -> System.out.println(a)"
    }
}
