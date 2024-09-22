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
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    // Como esta clase entidad "Alumno" es la clase principal que vincula otra clase entidad secundaria "Curso", es aquí donde tenemos que especificar el atributo "cascade" en la anotación "@ManyToMany"
    // Con "cascade = { CascadeType.PERSIST, CascadeType.MERGE }" indicamos que las operaciones de insertar y de actualizar que se hagan sobre un objeto de este clase entidad "Cliente", también se hagan sobre los objetos de clase entidad relacionada "Curso" de forma automática
    // No incluimos la operación "REMOVE" en el atributo "cascade" porque, al ser la relación "ManyToMany", no tiene sentido eliminar un curso de forma automática ya que puede estar asociado a otros alumnos
    // La anotación "@ManyToMany" crea una tabla intermedia en la base de datos(En este caso, por defecto con el nombre "alumnos_cursos") para relacionar, en este caso, los alumnos y los cursos a través de sus ids como claves foráneas
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // Usando esta anotación junto con la anotación "@ManyToMany", podemos personalizar el nombre de la tabla intermedia que se crea de forma automática y los nombres de las claves foráneas que se usan en esta tabla para mantener las relaciones
    @JoinTable(name = "tbl_alumnos_cursos" , joinColumns = @JoinColumn(name = "id_alumno"),
            inverseJoinColumns = @JoinColumn(name = "id_curso"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_alumno", "id_curso"})) // Añadimos esta restricción que hace que las parejas de ids de alumnos y cursos sean únicas en esta tabla
    private List<Curso> cursos;

    public Alumno() {
        this.cursos = new ArrayList<>();
    }

    public Alumno(String nombre, String apellido) {
        this(); // Invoca al constructor vacío para inicializar la lista de cursos
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    // Establece la relación bidireccional entre un alumno y un curso
    public void addCurso(Curso curso) {
        this.getCursos().add(curso);
        curso.getAlumnos().add(this);
    }

    // Elimina la relación bidireccional entre un alumno y un curso
    public void removeCurso(Curso curso) {
        this.getCursos().remove(curso);
        curso.getAlumnos().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(id, alumno.id);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cursos=" + cursos +
                '}';
    }
}
