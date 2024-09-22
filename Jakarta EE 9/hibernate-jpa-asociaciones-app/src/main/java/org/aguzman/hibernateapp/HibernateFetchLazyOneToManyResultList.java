package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateFetchLazyOneToManyResultList {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // Consula que usa el lenguaje HQL o JPQL
        // Si por temas de rendimiento queremos optimizar el número de consultas  separadas que se hacen a la base de datos para obtener cada cliente, sus direcciones y sus detalles en una misma consulta, podemos usar "join fetch" de esta manera
        // La expresión "left join" es equivalente a poner la expresión de la consulta "left outer join"
        // La expresión de la consulta "fetch c.direcciones" es para poblar las direcciones en cada objeto Cliente de la lista "clientes"
        List<Cliente> clientes = em.createQuery("select distinct c from Cliente c left outer join fetch c.direcciones left join fetch c.detalle", Cliente.class)
                .getResultList();

        // Si en una consulta intentamos traer más de una lista de objetos relacionados con la entity principal al mismo tiempo, obtenemos una excepción de este tipo que se produce para evitar problemas de rendimiento:
        // org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags: [org.aguzman.hibernateapp.entity.Cliente.direcciones, org.aguzman.hibernateapp.entity.Cliente.facturas]
        // El término "bag" de esta excepción se refiere a una bolsa o lista de objetos relacionados con la entity principal
        // En este caso, no podemos traer en una misma consulta las direcciones y las facturas de cada cliente al mismo tiempo
        // Si se desea traer las direcciones y las facturas de cada cliente, hay que hacerlo en consultas separadas
        /*List<Cliente> clientes = em.createQuery("select distinct c from Cliente c left outer join fetch c.direcciones left outer join fetch c.facturas left join fetch c.detalle", Cliente.class)
                .getResultList();*/

        // En este caso, aunque el tipo de fecth es "LAZY", no realiza otra consulta a la base de datos para obtener las direcciones de cada cliente porque ya se han obtenido mediante la consulta personalizada anterior
        clientes.forEach(c -> System.out.println(c.getNombre() + ", direcciones: " + c.getDirecciones()));

        em.close();
    }
}
