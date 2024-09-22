package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.entity.ClienteDetalle;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToOneBidireccionalFind {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin(); // Empieza una nueva transacción

            Cliente cliente = em.find(Cliente.class, 2L);

            ClienteDetalle clienteDetalle = new ClienteDetalle(true, 5000);

            // El método "addDetalle" de la clase entidad "Cliente" establece la relación bidireccional entre un cliente y un detalle
            cliente.addDetalle(clienteDetalle);

            // Como en la clase entidad "Cliente" está indicado "cascade = CascadeType.ALL" en la anotación "@OneToOne", de forma automática se persiste en cascada el detalle en la base de datos cuando se actualice este cliente
            //em.merge(cliente); // No es necesario porque el objeto "cliente" ya se encuentra en el contexto de persistencia y el commit que hay a continuación ya se encarga de reflejar las actualizaciones en la base de datos

            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola el cliente y su detalle después de la transacción
            System.out.println(cliente);

        }
        catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback(); // En caso de producirse algún error durante la transacción, hacemos un rollback
        }
        finally {
            em.close();
        }
    }
}
