package org.aguzman.ejemplos;

import java.util.HashSet;
import java.util.Set;

// Los HashSets no mantienen el orden natural de los elementos ni tampoco el orden de su inserción, y no permiten elementos duplicados
// Nota: Un HashSet detecta si un elemento está duplicado a partir de la implementación de los métodos "hashCode" e "equals" de la clase de esos elementos
// Es decir, si dos elementos del HashSet tienen el mismo "hashCode", a continaución se invoca al método "equals" sobre ellos, y si dicho método dice que son iguales,
// el elemento no se inserta en ese HashSet

public class EjemploHashSetBuscarDuplicado2 {

    public static void main(String[] args) {
        String[] peces = {"Corvina", "Lenguado", "Pejerrey", "Corvina", "Robalo", "Atún", "Lenguado"};
        Set<String> unicos = new HashSet<>();
        Set<String> duplicados = new HashSet<>();

        for(String pez: peces){
            if(!unicos.add(pez))
                duplicados.add(pez);
        }

        unicos.removeAll(duplicados);

        System.out.println("Unicos: " + unicos);
        System.out.println("Duplicados: " + duplicados);
    }
}
