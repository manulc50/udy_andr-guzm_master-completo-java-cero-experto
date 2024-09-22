package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateCriteriaBusquedaDinamica {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Filtro para el nombre:");
        String nombre = s.nextLine();

        System.out.println("Filtro para el apellido:");
        String apellido = s.nextLine();

        System.out.println("Filtro para la forma de pago:");
        String formaPago = s.nextLine();

        /* Uso del API Criteria - Alternativa a los lenguaje HQL o JPQL(son similiares) de forma programática y dinámica.
           Es decir, cuando tenemos que construir las consultas de forma de dinámica usando lógica como bloques
           if-else, etc..., es mucho mejor usar la API Criteria en lugar de  los lenguajes HQL o JPQL */

        EntityManager em = JpaUtil.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);

        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.nombre = :nombre and c.apellido = :apellido and c.formaPago = formaPago"

        // from Cliente c
        Root<Cliente> from = query.from(Cliente.class);

        List<Predicate> condiciones = new ArrayList<>();

        if(nombre != null && !nombre.isEmpty())
            condiciones.add(cb.equal(from.get("nombre"), nombre));

        if(apellido != null && !apellido.isEmpty())
            condiciones.add(cb.equal(from.get("apellido"), apellido));

        if(formaPago != null && !formaPago.equals(""))
            condiciones.add(cb.equal(from.get("formaPago"), formaPago));

        // select c
        // where c.nombre = :nombre and c.apellido = :apellido and c.formaPago = formaPago
        query.select(from).where(cb.and(condiciones.toArray(new Predicate[condiciones.size()])));

        List<Cliente> clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        em.close();
    }

}
