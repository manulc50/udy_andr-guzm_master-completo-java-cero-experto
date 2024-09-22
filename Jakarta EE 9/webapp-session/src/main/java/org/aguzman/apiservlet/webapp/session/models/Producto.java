package org.aguzman.apiservlet.webapp.session.models;

import java.time.LocalDate;

public class Producto {
    private Long id;
    private String nombre;
    private Integer precio;
    private String sku;
    private Categoria categoria;
    private LocalDate fechaRegistro;

    public Producto() {
    }

    public Producto(Long id, String nombre, Integer precio, String nombreCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        Categoria categoria = new Categoria(nombreCategoria);
        this.categoria = categoria;
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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
