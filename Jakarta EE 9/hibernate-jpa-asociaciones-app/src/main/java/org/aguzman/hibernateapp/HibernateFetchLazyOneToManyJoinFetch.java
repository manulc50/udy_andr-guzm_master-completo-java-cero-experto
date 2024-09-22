package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateFetchLazyOneToManyJoinFetch {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // Consula que usa el lenguaje HQL o JPQL
        // Si por temas de rendimiento queremos optimizar el número de consultas  separadas que se hacen a la base de datos para obtener el cliente, sus direcciones y su detalle en una misma consulta, podemos usar "join fetch" de esta manera
        // La expresión "left join" es equivalente a poner la expresión de la consulta "left outer join"
        // La expresión de la consulta "fetch c.direcciones" es para poblar las direcciones en el objeto "cliente"
        Cliente cliente = em.createQuery("select c from Cliente c left outer join fetch c.direcciones left join fetch c.detalle where c.id = :id", Cliente.class)
                .setParameter("id", 1L)
                .getSingleResult();

        // En este caso, aunque el tipo de fecth es "LAZY", no realiza otra consulta a la base de datos para obtener las direcciones del cliente porque ya se han obtenido mediante la consulta personalizada anterior
        System.out.println(cliente.getNombre() + ", direcciones: " + cliente.getDirecciones());

        System.out.println(cliente.getNombre() + ", detalle: " + cliente.getDetalle());

        em.close();
    }
}
