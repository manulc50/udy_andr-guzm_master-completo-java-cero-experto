package org.aguzman.ejemplos.modelo;

// Esta clase debe implementar la interfaz "Comparable" para poder usar instancias suyas como elementos de un TreeSet

import java.util.Objects;

public class Alumno implements Comparable<Alumno> {
    private String nombre;
    //private int nota;
    private Integer nota;

    public Alumno() {

    }

    public Alumno(String nombre, Integer nota) {
        this.nombre = nombre;
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return nombre + ", nota=" + nota;
    }

    // Realiza la comparación de alumnos
    @Override
    public int compareTo(Alumno a) {
        // Usando sus nombres
        /*if(this.nombre == null)
            return 0;

        return this.nombre.compareTo(a.getNombre());*/

        // Usando sus notas(Si el tipo de la nota es un primitivo(int))
        /*if(this.nota == a.getNota())
            return 0;
        else if(this.nota > a.getNota())
            return 1;
        else
            return -1;*/

        // Usando sus notas(Si el tipo de la nota es un wrapper(Integer))
        if(this.nota == null)
            return 0;
        return this.nota.compareTo(a.getNota());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(nombre, alumno.nombre) && Objects.equals(nota, alumno.nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, nota);
    }
}
