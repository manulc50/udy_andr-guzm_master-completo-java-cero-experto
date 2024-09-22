package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernatePorId2 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Ingrese un id:");
        Long id = s.nextLong();

        EntityManager em = JpaUtil.getEntityManager();

        Cliente c = em.find(Cliente.class, id); // Usando el método "find", el cliente obtenido se guarda en memoria(contexto de JPA) como si fuera una caché
        System.out.println(c);

        Cliente c2 = em.find(Cliente.class, id); // No realiza otra consulta SQL, utiliza el cliente almacenado en la memoria(contexto de JPA)
        System.out.println(c2);

        em.close();
    }
}
