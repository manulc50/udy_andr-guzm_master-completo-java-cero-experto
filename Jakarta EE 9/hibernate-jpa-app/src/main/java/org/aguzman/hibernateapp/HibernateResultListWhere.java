package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateResultListWhere {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Ingrese una forma de pago:");
        String pago = s.next();

        EntityManager em = JpaUtil.getEntityManager();

        Query query= em.createQuery("select c from Cliente c where c.formaPago = ?1", Cliente.class);
        query.setParameter(1, pago);

        List<Cliente> clientes = query.getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "c -> System.out.println(c)"

        em.close();
    }
}
