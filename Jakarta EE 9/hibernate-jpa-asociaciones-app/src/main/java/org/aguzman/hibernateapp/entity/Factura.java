package org.aguzman.hibernateapp.entity;


import jakarta.persistence.*;

import java.util.Objects;

/* Fetch - Define el cuándo y cómo Hibernate/JPA obtiene los objetos relacionados de un entity de la base de datos
   Dos formas:
     - FetchType.LAZY - Carga a demanda o perezosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "Many"
     - FetchType.EAGER - Carga temprana o ansiosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "One"

    Por temas de rendimiento, se recomienda usar el tipo de fetch "LAZY" siempre que sea posible
*/

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private Long total;

    // Como estamos en una relación bidireccional entre la clase entidad "Cliente" y esta clase entidad "Factura", esta clase "Factura" es dueña de la relación porque es la que tiene la clave foránea de la otra clase entidad "Cliente" debido a la anotación "@JoinColumn"
    // Por defecto crea una clave foránea con el nombre "cliente_id" ya que por defecto toma el nombre de la propiedad, que es "cliente", y le concatena "_id"
    @ManyToOne
    @JoinColumn(name = "id_cliente") // Personalizamos el nombre de la clave foránea. De esta forma, ya no es "cliente_id", ahora es "id_cliente"
    private Cliente cliente;

    public Factura() {
    }

    public Factura(String descripcion, Long total) {
        this.descripcion = descripcion;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(id, factura.id) && Objects.equals(descripcion, factura.descripcion) && Objects.equals(total, factura.total);
    }

    @Override
    public String toString() {
        // Como estamos en una relación bidireccional entre esta clase entidad "Factura" y la clase entidad "Cliente", sólo mostramos los datos del cliente en el clase entidad "Cliente" para no entrar en un bucle infinito con el método "toString" de la clase entidad "Factura"
        return "{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", total=" + total +
                //", cliente=" + cliente +
                '}';
    }
}
