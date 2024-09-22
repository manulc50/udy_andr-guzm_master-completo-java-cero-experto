package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateCrear {

    public static void main(String args[]) {

        // Genera tres ventanas visuales de diálogo para pedir e introducir el nombre, apellido y la forma de pago del cliente
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
        String apellido = JOptionPane.showInputDialog("Ingrese el apellido:");
        String pago = JOptionPane.showInputDialog("Ingrese la forma de pago:");

        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setFormaPago(pago);

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin(); // Empieza una transacción
            em.persist(c); // Usando el método "persist", el cliente persistido queda almacenado en memoria(contexto de JPA) como si fuera una caché
            em.getTransaction().commit(); // Termina la transacción y hace efectivos los cambios de la transacción en la base de datos
            System.out.println("El id del cliente registrado es: " + c.getId());
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
