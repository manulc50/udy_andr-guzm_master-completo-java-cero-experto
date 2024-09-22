package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateEditar {

    public static void main(String[] args) {

        // Genera una ventana visual de diálogo para pedir e introducir el id del cliente a modificar
        Long id = Long.valueOf(JOptionPane.showInputDialog("Ingrese el id del cliente a modificar:"));

        EntityManager em = JpaUtil.getEntityManager();

        Cliente c = em.find(Cliente.class, id);

        // Genera tres ventanas visuales de diálogo para pedir e introducir los datos a modificar del cliente
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:", c.getNombre());
        String apellido = JOptionPane.showInputDialog("Ingrese el apellido:", c.getApellido());
        String pago = JOptionPane.showInputDialog("Ingrese la forma de pago:", c.getFormaPago());

        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setFormaPago(pago);

        try {
            em.getTransaction().begin(); // Empieza una transacción
            em.merge(c); // Usando el método "merge", el cliente actualizado queda almacenado en memoria(contexto de JPA) como si fuera una caché
            em.getTransaction().commit(); // Termina la transacción y hace efectivos los cambios de la transacción en la base de datos
            c = em.find(Cliente.class, c.getId()); // No realiza una consulta SQL a la base de datos, utiliza el cliente almacenado en memoria(contexto de JPA)
            System.out.println(c);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback(); // En caso de producirse algún error durante la transacción, hacemos un rollback
        }
        finally {
            em.close();
        }
    }
}
