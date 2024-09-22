package org.aguzman.hibernateapp.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* Fetch - Define el cuándo y cómo Hibernate/JPA obtiene los objetos relacionados de un entity de la base de datos
   Dos formas:
     - FetchType.LAZY - Carga a demanda o perezosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "Many"
     - FetchType.EAGER - Carga temprana o ansiosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "One"

    Por temas de rendimiento, se recomienda usar el tipo de fetch "LAZY" siempre que sea posible
*/

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String profesor;

    // Como estamos en una relación bidireccional entre esta clase entidad "Curso" y la clase entidad "Alumno", tenemos que indicar en esta anotación "@ManyToMany" el atributo "mappedBy" indicando el nombre de la propiedad de la clase entidad "Alumno" que establece la relación con esta clase entidad "Curso"
    @ManyToMany(mappedBy = "cursos")
    private List<Alumno> alumnos;

    public Curso() {
        this.alumnos = new ArrayList<>();
    }

    public Curso(String titulo, String profesor) {
        this(); // Invoca al constructor vacío para inicializar la lista de alumnos
        this.titulo = titulo;
        this.profesor = profesor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return Objects.equals(id, curso.id);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", profesor='" + profesor + '\'' +
                '}';
    }
}
