package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.entity.Direccion;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToMany {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            // Transacción para crear un cliente y sus direcciones
            em.getTransaction().begin(); // Empieza una nueva transacción

            Cliente cliente = new Cliente("Cata", "Edu", "mercado pago");

            Direccion d1 = new Direccion("El Verde", 123);
            Direccion d2 = new Direccion("Vasco de Gama", 456);

            // Establece la relación entre el cliente y ámbas direcciones
            cliente.getDirecciones().add(d1);
            cliente.getDirecciones().add(d2);

            // Como en la clase entidad "Cliente" está indicado "cascade = CascadeType.ALL" en la anotación "@OneToMany", de forma automática se persisten en cascada las direcciones en la base de datos cuando se persiste este cliente
            em.persist(cliente);

            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola el cliente y sus direcciones después de la transacción
            System.out.println(cliente);

            // Transacción para eliminar una dirección relacionada con el cliente
            em.getTransaction().begin(); // Empieza una nueva transacción

            // La información del cliente y sus direcciones ya la tenemos previamente en el objeto "cliente" debido a la transacción anterior, pero vamos a suponer que estamos ante una nueva consulta y no disponemos de ese objeto "cliente" con toda esa información
            cliente = em.find(Cliente.class, cliente.getId());

            // Como en la clase entidad "Cliente" está indicado "orphanRemoval = true" en la anotación "@OneToMany", tras eliminar la dirección "d1" de la lista de direcciones del cliente, de forma automática se elimina el objeto dirección "d1" y su registro asociado en la tabla correspondiente de la base de datos porque ese objeto dirección se ha quedado huérfano
            cliente.getDirecciones().remove(d1);

            // Nota: No hace falta invocar al método "merge" del Entity Manager sobre el objeto "cliente" para actualizar su estado porque previamente ya se encuentra dentro del contexto de persistencia y con este commit ya quedan reflejados los nuevos cambios en la base de datos
            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola el cliente y sus direcciones después de la transacción
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
