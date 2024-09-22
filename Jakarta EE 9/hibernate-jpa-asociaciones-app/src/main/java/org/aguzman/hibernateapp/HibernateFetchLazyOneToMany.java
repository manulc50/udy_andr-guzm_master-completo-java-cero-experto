package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateFetchLazyOneToMany {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // Esta consulta no se trae ni las direcciones del cliente ni sus facturas porque esas relaciones son de tipo "OneToMany" que utilizan por defecto el fetch "LAZY"
        // Sin embargo, esta consulta si se trae el detalle del cliente porque esa relación es de tipo "OneToOne" que usa por defecto el fecth "EAGER"
        // Si usamos el atributo "fetch" de la anotación "@OneToMany" de las direcciones con el valor "FetchType.EAGER", esta consulta también se traerá las direcciones del cliente de la base de datos
        Cliente cliente = em.find(Cliente.class, 1L);

        // Si el tipo de fecth es "LAZY", realiza otra consulta a la base de datos para obtener las direcciones del cliente
        System.out.println(cliente.getNombre() + ", direcciones: " + cliente.getDirecciones());

        em.close();

        // Si el tipo de fecth es "LAZY", se produce la excepción "org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: org.aguzman.hibernateapp.entity.Cliente.direcciones, could not initialize proxy - no Session"
        // Ésto es debido a que se intenta obtener las direcciones del cliente de la base de datos pero previamente se cerró la sesión del Entity Manager
        // Si el tipo de fetch es "EAGER", la ejecución es correcta porque las direcciones se obtuvieron previamente de la base de datos justo al mismo tiempo de la consulta por el cliente
        //System.out.println(cliente.getNombre() + ", direcciones: " + cliente.getDirecciones());
    }
}
