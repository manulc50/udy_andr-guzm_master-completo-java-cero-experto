package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.entity.Direccion;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToManyFind {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            // Transacción para actualizar un cliente existente con nuevas direcciones
            em.getTransaction().begin(); // Empieza una nueva transacción

            Cliente cliente = em.find(Cliente.class, 2L);

            Direccion d1 = new Direccion("El Verde", 123);
            Direccion d2 = new Direccion("Vasco de Gama", 456);

            // Establece la relación entre el cliente y ámbas direcciones
            cliente.getDirecciones().add(d1);
            cliente.getDirecciones().add(d2);

            // Como en la clase entidad "Cliente" está indicado "cascade = CascadeType.ALL" en la anotación "@OneToMany", de forma automática se persisten en cascada las direcciones en la base de datos cuando se actualice este cliente
            //em.merge(cliente); // No es necesario porque el objeto "cliente" ya se encuentra en el contexto de persistencia, debido a la invocación anterior del método "find", y el commit que hay a continuación ya se encarga de reflejar las actualizaciones en la base de datos

            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola el cliente y sus direcciones después de la transacción
            System.out.println(cliente);

            // Transacción para eliminar una dirección relacionada con el cliente
            em.getTransaction().begin(); // Empieza una nueva transacción

            // Nota: En este caso y en este punto de la aplicación, sólo el objeto "cliente" está dentro del contexto de persistencia pero los objetos de las direcciones no lo están. Ésto es debido a que la invocación anterior al método "merge" sólo actualiza la tabla intermedia de las relaciones entre clientes y sus direcciones, pero no tiene efecto sobre la tabla de las direcciones
            // Por esta razón, tenemos que localizar antes la dirección a eliminar de la base de datos para que esa dirección esté dentro del contexto de peristencia y se pueda eliminar posteriormente
            d1 = em.find(Direccion.class, 1L);

            cliente.getDirecciones().remove(d1);

            // Nota: No hace falta invocar al método "merge" del Entity Manager sobre el objeto "cliente" para actualizar su estado porque ahora, en este punto de la aplicación, tenemos tanto el objeto "cliente" como el objeto "d1" en el contexto de persistencia y, por lo tanto, este commit ya refleja los cambios en la base de datos
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
