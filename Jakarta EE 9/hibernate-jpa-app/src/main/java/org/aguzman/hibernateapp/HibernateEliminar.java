package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernateEliminar {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Ingrese el id del cliente a eliminar:");
        Long id = s.nextLong();

        EntityManager em = JpaUtil.getEntityManager();

        Cliente c = em.find(Cliente.class, id); // El objeto "c", con los datos del cliente, está dentro del contexto de JPA

        try {
            em.getTransaction().begin(); // Empieza una transacción
            em.remove(c); // No se puede eliminar cualquier objeto, el objeto tiene que estar en el contexto de JPA para poder eliminarlo. El objeto "c", con los datos del cliente, está dentro del contexto de JPA porque antes se ha obtenido mediante el método "find"
            em.getTransaction().commit(); // Termina la transacción y hace efectivos los cambios de la transacción en la base de datos
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
