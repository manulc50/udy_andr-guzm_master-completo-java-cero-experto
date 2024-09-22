package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernateSingleResultWhere {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Ingrese una forma de pago:");
        String pago = s.next();

        EntityManager em = JpaUtil.getEntityManager();

        Query query= em.createQuery("select c from Cliente c where c.formaPago = ?1", Cliente.class);
        query.setParameter(1, pago);
        query.setMaxResults(1); // Si la consulta devuelve más de un registro, la limitamos para que sólo devuelva el primero

        Cliente c = (Cliente)query.getSingleResult();

        System.out.println(c);

        em.close();
    }
}
