package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.entity.Factura;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateFetchManyToOneCriteria {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // Consula que usa la API de Criteria
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Factura> query = cb.createQuery(Factura.class);

        // from Factura f
        Root<Factura> factura = query.from(Factura.class);

        // Si por temas de rendimiento queremos optimizar el número de consultas  separadas que se hacen a la base de datos para obtener cada factura, su cliente asociado y el detalle de cada cliente en una misma consulta, podemos usar "join fetch" de esta manera

        // left outer join fetch f.cliente
        // Usamos el método "fetch" en vez del método "join" para poblar, en este caso, cada objecto Cliente en cada objeto Factura
        Join<Factura, Cliente> cliente = (Join)factura.fetch("cliente", JoinType.LEFT);

        // letf outer foin fetch c.detalle
        // Usamos el método "fetch" en vez del método "join" para poblar, en este caso, cada objecto ClienteDetalle en cada objeto Cliente
        cliente.fetch("detalle", JoinType.LEFT);

        // select f
        // where c.id = 1L
        query.select(factura).where(cb.equal(cliente.get("id"), 1L));

        List<Factura> facturas = em.createQuery(query).getResultList();

        facturas.forEach(f -> System.out.println(f.getDescripcion() + ", cliente: " + f.getCliente().getNombre()));

        em.close();
    }
}
