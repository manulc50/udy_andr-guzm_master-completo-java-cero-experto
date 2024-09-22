package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Alumno;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateFetchManyToMany {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // Consula que usa el lenguaje HQL o JPQL
        // Si por temas de rendimiento queremos optimizar el número de consultas  separadas que se hacen a la base de datos para obtener cada alumno y sus cursos en una misma consulta, podemos usar "join fetch" de esta manera
        // La expresión "left join" es equivalente a poner la expresión de la consulta "left outer join"
        // La expresión de la consulta "fetch c.cursos" es para poblar los cursos en cada objeto Alumno de la lista "alumnos"
        List<Alumno> alumnos = em.createQuery("select distinct(a) from Alumno a left outer join fetch a.cursos", Alumno.class)
                .getResultList();

        // En este caso, aunque el tipo de fecth es "LAZY", no realiza otra consulta a la base de datos para obtener los cursos de cada alumno porque ya se han obtenido mediante la consulta personalizada anterior
        alumnos.forEach(a -> System.out.println(a.getNombre() + ", cursos: " + a.getCursos()));

        em.close();
    }
}
