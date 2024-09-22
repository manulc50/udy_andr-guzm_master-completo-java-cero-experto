package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateFetchOneToManyCriteria {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // Consula que usa la API de Criteria
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);

        // from Cliente c
        Root<Cliente> cliente = query.from(Cliente.class);

        // Si por temas de rendimiento queremos optimizar el número de consultas  separadas que se hacen a la base de datos para obtener el cliente, sus direcciones y su detalle en una misma consulta, podemos usar "join fetch" de esta manera

        // left outer join fetch f.direcciones
        // Usamos el método "fetch" en vez del método "join" para poblar, en este caso, las direcciones en el objeto "cliente"
        cliente.fetch("direcciones", JoinType.LEFT);

        // left outer join fetch f.detalle
        // Usamos el método "fetch" en vez del método "join" para poblar, en este caso, cada objecto ClienteDetalle en cada objeto Cliente
        cliente.fetch("detalle", JoinType.LEFT);

        // select distinct c
        query.select(cliente).distinct(true);

        List<Cliente> clientes = em.createQuery(query).getResultList();

        // En este caso, aunque el tipo de fecth es "LAZY" para las direcciones del cliente, no realiza otra consulta a la base de datos para obtenerlas porque ya se han obtenido mediante la consulta personalizada anterior
        clientes.forEach(c -> System.out.println(c.getNombre() + ", detalle: " + c.getDetalle() + ", direcciones: " + c.getDirecciones()));

        em.close();
    }
}
