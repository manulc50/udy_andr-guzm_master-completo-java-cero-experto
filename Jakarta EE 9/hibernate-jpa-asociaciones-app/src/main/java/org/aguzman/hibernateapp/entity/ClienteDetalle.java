package org.aguzman.hibernateapp.entity;


import jakarta.persistence.*;

/* Fetch - Define el cuándo y cómo Hibernate/JPA obtiene los objetos relacionados de un entity de la base de datos
   Dos formas:
     - FetchType.LAZY - Carga a demanda o perezosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "Many"
     - FetchType.EAGER - Carga temprana o ansiosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "One"

    Por temas de rendimiento, se recomienda usar el tipo de fetch "LAZY" siempre que sea posible
*/

@Entity
@Table(name = "clientes_detalles")
public class ClienteDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean prime;

    @Column(name = "puntos_acumulados")
    private Integer puntosAcumuldos;

    // Si la relación entre esta clase entidad "ClienteDetalle" y la clase entidad "Cliente" es unidireccional y aquí se encuentra la anotación "@OneToOne", esta clase entidad "ClienteDetalle" es la dueña de la relación porque es la que tiene la clave foránea de la otra clase entidad "Cliente" debido a la anotación "@OneToOne"
    // Como ahora estamos en una relación bidireccional entre la clase entidad "Cliente" y esta clase entidad "ClienteDetalle", esta clase "ClienteDetalle" es dueña de la relación porque es la que tiene la clave foránea de la otra clase entidad "Cliente" debido a la anotación "@JoinColumn"
    // Por defecto crea una clave foránea con el nombre "cliente_id" ya que por defecto toma el nombre de la propiedad, que es "cliente", y le concatena "_id"
    @OneToOne
    @JoinColumn(name = "cliente_detalle_id") // Personalizamos el nombre de la clave foránea. De esta forma, ya no es "cliente_id", ahora es "cliente_detalle_id"
    private Cliente cliente;

    public ClienteDetalle() {
    }

    public ClienteDetalle(boolean prime, Integer puntosAcumuldos) {
        this.prime = prime;
        this.puntosAcumuldos = puntosAcumuldos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPrime() {
        return prime;
    }

    public void setPrime(boolean prime) {
        this.prime = prime;
    }

    public Integer getPuntosAcumuldos() {
        return puntosAcumuldos;
    }

    public void setPuntosAcumuldos(Integer puntosAcumuldos) {
        this.puntosAcumuldos = puntosAcumuldos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", prime=" + prime +
                ", puntosAcumuldos=" + puntosAcumuldos +
                '}';
    }
}
