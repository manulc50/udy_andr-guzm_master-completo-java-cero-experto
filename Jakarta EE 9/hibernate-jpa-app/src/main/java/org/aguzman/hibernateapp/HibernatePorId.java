package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernatePorId {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Ingrese un id:");
        Long id = s.nextLong();

        EntityManager em = JpaUtil.getEntityManager();

        Query query= em.createQuery("select c from Cliente c where c.id = ?1", Cliente.class);
        query.setParameter(1, id);

        Cliente c = (Cliente)query.getSingleResult();

        System.out.println(c);

        em.close();
    }
}
