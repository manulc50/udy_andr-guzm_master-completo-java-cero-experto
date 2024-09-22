package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Alumno;
import org.aguzman.hibernateapp.entity.Curso;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesManyToManyFind {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin(); // Empieza una nueva transacción

            Alumno a1 = em.find(Alumno.class, 1L);
            Alumno a2 = em.find(Alumno.class, 2L);

            //Curso c1 = new Curso("Curso Java", "Andres");
            //Curso c2 = new Curso("Curso Angular", "Fernando");
            // Ahora los cursos también se obtienen de la base de datos
            Curso c1 = em.find(Curso.class, 1L);
            Curso c2 = em.find(Curso.class, 2L);

            a1.getCursos().add(c1);
            a1.getCursos().add(c2);

            a2.getCursos().add(c1);

            // (Comentario válido sólo para el caso de la creación de nuevos cursos)Como en la clase entidad "Alumno" está indicado "cascade = { CascadeType.PERSIST, CascadeType.MERGE }" en la anotación "@ManyToMany", de forma automática se persisten en cascada los cursos en la base de datos cuando se actualicen estos alumnos
            // No es necesario invocar al método "merge" porque los objetos "a1" y "a2" ya se encuentran en el contexto de persistencia y el commit que hay a continuación ya se encarga de reflejar las actualizaciones en la base de datos
            //em.merge(a1);
            //em.merge(a2);

            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola los alumnos y sus cursos después de la transacción
            System.out.println(a1);
            System.out.println(a2);
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
