package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.entity.Factura;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToManyBidireccional {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            // Transacción para crear un cliente y sus facturas
            em.getTransaction().begin(); // Empieza una nueva transacción

            Cliente cliente = new Cliente("Cata", "Edu", "paypal");

            Factura f1 = new Factura("Compras de supermercado", 5000L);
            Factura f2 = new Factura("Compras de tecnología", 7000L);

            // El método "addFactura" de la clase entidad "Cliente" establece la relación bidireccional entre un cliente y una factura
            cliente.addFactura(f1);
            cliente.addFactura(f2);

            // Como en la clase entidad "Cliente" está indicado "cascade = CascadeType.ALL" en la anotación "@OneToMany", de forma automática se persisten en cascada las facturas en la base de datos cuando se persiste este cliente
            em.persist(cliente);

            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola el cliente y sus facturas después de la transacción
            System.out.println(cliente);

            // Transacción para eliminar una factura del cliente
            em.getTransaction().begin(); // Empieza una nueva transacción

            // Primera forma localizando la factura a eliminar en la base de datos
            //Factura f3 = em.find(Factura.class, 1L);
            // Segunda forma creando una nueva instancia de la clase entidad "Factura" y sobrescribiendo el método "equals" de dicha clase entidad
            Factura f3 = new Factura("Compras de supermercado", 5000L);
            f3.setId(1L);

            // El método "removeFactura" de la clase entidad "Cliente" eliminar la relación bidireccional entre un cliente y una factura
            cliente.removeFactura(f3);

            // Nota: No hace falta invocar al método "merge" del Entity Manager sobre el objeto "cliente" para actualizar su estado porque previamente ya se encuentra dentro del contexto de persistencia y con este commit ya quedan reflejados los nuevos cambios en la base de datos
            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola el cliente y sus facturas después de la transacción
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
