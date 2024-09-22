package org.aguzman.hibernateapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/* Fetch - Define el cuándo y cómo Hibernate/JPA obtiene los objetos relacionados de un entity de la base de datos
   Dos formas:
     - FetchType.LAZY - Carga a demanda o perezosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "Many"
     - FetchType.EAGER - Carga temprana o ansiosa. Es el tipo de fetch que se usa por defecto en las anotaciones que acaban en "One"

    Por temas de rendimiento, se recomienda usar el tipo de fetch "LAZY" siempre que sea posible
*/

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(name = "forma_pago")
    private String formaPago;

    // Como esta clase entidad "Cliente" es la clase principal que vincula otra clase entidad secundaria "Dirección", es aquí donde tenemos que especificar los atributos "cascade" y "orphanRemoval" en la anotación "@OneToMany"
    // Con "cascade = CascadeType.ALL" indicamos que todas las operaciones(insertar, actualizar o eliminar) que se hagan sobre un objeto de este clase entidad "Cliente", también se hagan sobre los objetos de clase entidad relacionada "Dirección" de forma automática
    // Con "orphanRemoval = true" se elimina de forma automática aquellos objetos de la clase relacionada "Dirección" que queden desvinculados de un objeto de esta clase entidad "Cliente", es decir, se eliminan de forma automática para que no queden objetos huérfanos
    // Como esta relación es unidireccional y se usa la anotación "@OneToMany", por defecto se crea de forma automática una tabla intermedia(Por defecto con el nombre "clientes_direcciones") para relacionar clientes con direcciones a través de sus ids como claves foráneas
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // Al usar esta anotación junto con la anotación "@OneToMany" en una relación unidireccional, ya no se crea la tabla intermedia("clientes_direcciones"), ahora se añade una clave foránea("id_cliente) en la tabla asociada a la clase entidad del otro lado de la relación("Dirección")
    //@JoinColumn(name = "id_cliente")
    // Usando esta anotación junto con la anotación "@OneToMany" en una relación unidireccional, podemos personalizar el nombre de la tabla intermedia que se crea de forma automática y los nombres de las claves foráneas que se usan en esta tabla para mantener las relaciones
    @JoinTable(name = "tbl_clientes_direcciones" , joinColumns = @JoinColumn(name = "id_cliente"),
    inverseJoinColumns = @JoinColumn(name = "id_direccion"),
    uniqueConstraints = @UniqueConstraint(columnNames = "id_direccion")) // Añadimos esta restricción que hace que los ids de las direcciones sean únicos en esta tabla. Los ids de los clientes sí puden repetirse porque un cliente puede tener varrias direcciones, pero los ids de las direcciones deben ser únicas
    private List<Direccion> direcciones;

    // Como estamos en una relación bidireccional entre esta clase entidad "Cliente" y la clase entidad "Factura", tenemos que indicar en esta anotación "@OneToMany" el atributo "mappedBy" indicando el nombre de la propiedad de la clase entidad "Factura" que establece la relación con esta clase entidad "Cliente"
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
    private List<Factura> facturas;

    // Si la relación entre esta clase entidad "Cliente" y la clase entidad "ClienteDetalle" es unidireccional y aquí se encuentra la anotación "@OneToOne", esta clase entidad "Cliente" es la dueña de la relación porque es la que tiene la clave foránea de la otra clase entidad "ClienteDetalle" debido a la anotación "@OneToOne"
    // Como ahora estamos en una relación bidireccional entre esta clase entidad "Cliente" y la clase entidad "ClienteDetalle", tenemos que indicar en esta anotación "@OneToMany" el atributo "mappedBy" indicando el nombre de la propiedad de la clase entidad "ClienteDetalle" que establece la relación con esta clase entidad "Cliente"
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
    private ClienteDetalle detalle;

    // Con esta anotación, incrustamos o añadimos en esta clase entidad o de persistencia _todo el código de la clase "Auditoria"(tiene que estar anotada con @Embeddable)
    @Embedded
    private final Auditoria audit = new Auditoria();

    public Cliente() {
        this.facturas = new ArrayList<>();
        this.direcciones = new ArrayList<>();
    }

    public Cliente(String nombre, String apellido) {
        this(); // Llama al construtor vacío para inicializar el ArrayList de "direcciones"
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Cliente(String nombre, String apellido, String formaPago) {
        this(); // Llama al construtor vacío para inicializar el ArrayList de "direcciones"
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.formaPago = formaPago;
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

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public ClienteDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(ClienteDetalle detalle) {
        this.detalle = detalle;
    }

    // Establece la relación bidireccional entre un cliente y una factura
    public void addFactura(Factura factura) {
        this.getFacturas().add(factura);
        factura.setCliente(this);
    }

    // Elimina la relación bidireccional entre un cliente y una factura
    public void removeFactura(Factura factura) {
        this.getFacturas().remove(factura);
        factura.setCliente(null);
    }

    // Establece la relación bidireccional entre un cliente y un detalle
    public void addDetalle(ClienteDetalle detalle) {
        this.setDetalle(detalle);
        detalle.setCliente(this);
    }

    // Elimina la relación bidireccional entre un cliente y un detalle
    public void removeDetalle() {
        this.detalle.setCliente(null);
        this.setDetalle(null);
    }

    @Override
    public String toString() {
        LocalDateTime creado = audit != null ? audit.getCreadoEn() : null;
        LocalDateTime editado = audit != null ? audit.getEditadoEn() : null;

        return  "{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", formaPago='" + formaPago + '\'' +
                ", direcciones='" + direcciones + '\'' +
                ", facturas='" + facturas + '\'' +
                ", detalle='" + detalle + '\'' +
                ", creadoEn='" + creado + '\'' +
                ", editadoEn='" + editado + '\'' +
                '}';
    }
}
