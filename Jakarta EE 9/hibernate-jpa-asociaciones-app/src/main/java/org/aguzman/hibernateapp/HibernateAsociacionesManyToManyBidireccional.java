package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Alumno;
import org.aguzman.hibernateapp.entity.Curso;
import org.aguzman.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesManyToManyBidireccional {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            // Transacción para crear 2 alumnos y 2 cursos y asociarlos
            em.getTransaction().begin(); // Empieza una nueva transacción

            Alumno a1 = new Alumno("Cata", "Edu");
            Alumno a2 = new Alumno("Jano", "Fernan");

            Curso c1 = new Curso("Curso Java", "Andres");
            Curso c2 = new Curso("Curso Angular", "Fernando");

            // El método "addCurso" de la clase entidad "Alumno" establece la relación bidireccional entre un alumno y un curso
            a1.addCurso(c1);
            a1.addCurso(c2);

            a2.addCurso(c1);

            // Como en la clase entidad "Alumno" está indicado "cascade = { CascadeType.PERSIST, CascadeType.MERGE }" en la anotación "@ManyToMany", de forma automática se persisten en cascada los cursos en la base de datos cuando se persistan estos alumnos
            em.persist(a1);
            em.persist(a2);

            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola los alumnos y sus cursos después de la transacción
            System.out.println(a1);
            System.out.println(a2);

            // Transacción para eliminar un curso de un alumno
            em.getTransaction().begin(); // Empieza una nueva transacción

            // Primera forma localizando el curso a eliminar en la base de datos
            //Curso c3 = em.find(Curso.class, 3L);
            // Segunda forma creando una nueva instancia de la clase entidad "Curso" y sobrescribiendo el método "equals" de dicha clase entidad y de la clase entidad "Alumno"
            Curso c3 = new Curso("Curso Java", "Andres"); // El título del curso y el nombre del profesor no influye para realizar la eliminación de ese curso del alumno. La propiedad del curso que importa es el id ya que es la propiedad que se usa en el método "equals"
            c3.setId(3L);

            // El método "removeCurso" de la clase entidad "Alumno" elimina la relación bidireccional entre un alumno y un curso
            a1.removeCurso(c3);

            // No es necesario invocar al método "merge" porque el objeto "a1" ya se encuentra en el contexto de persistencia y el commit que hay a continuación ya se encarga de reflejar las actualizaciones en la base de datos
            //em.merge(a1);

            em.getTransaction().commit(); // Hace commit de la transacción, reflejando los cambios en la base de datos, y finaliza la transacción

            // Mostramos por consola el alumno "a1" y sus cursos después de la transacción
            System.out.println(a1);
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
