package org.aguzman.hibernateapp.repositories;

import java.util.List;

// Interfaz genérica con las operaciones CRUD

public interface CrudRepository<T> {

    List<T> listar();
    T porId(Long id);
    void guardar(T t); // Realiza la persistencia de un nuevo objeto o la actualización de un objeto existente
    void eliminar(Long id);
}
