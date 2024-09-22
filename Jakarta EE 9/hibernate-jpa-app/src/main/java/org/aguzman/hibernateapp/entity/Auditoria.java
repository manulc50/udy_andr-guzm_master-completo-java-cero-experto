package org.aguzman.hibernateapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

/* La anotación @Embeddable nos permite definir o implementar un código reutilizable que es común en las clases entidad
   o de persistencia para después incrustarlo o añadirlo en esas clases entidad o de persistencia */

@Embeddable // Anotación para implementar código que va a formar parte de clases entidad o de persistencia
public class Auditoria {

    // "LocalDateTime" es de la versión 8 de Java
    @Column(name ="creado_en")
    private LocalDateTime creadoEn; // Propiedad de auditoria

    @Column(name ="editado_en")
    private LocalDateTime editadoEn; // Propiedad de auditoria

    /* Eventos del ciclo de vida de una objeto entidad o de persistencia:
       Anotaciones @PrePersist, @PreUpdate, @PreRemove, @PostPersist, @PostUpdate, @PostRemove

       Los métodos que usan estas anotaciones siempre devuelven void, es decir, nada
     */

    @PrePersist
    public void prePresist() {
        System.out.println("inicializar algo justo antes de persistir una entidad en la base de datos");
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("inicializar algo justo antes de actualizar una entidad en la base de datos");
        this.editadoEn = LocalDateTime.now();
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getEditadoEn() {
        return editadoEn;
    }

    public void setEditadoEn(LocalDateTime editadoEn) {
        this.editadoEn = editadoEn;
    }
}
