package org.aguzman.java.jdbc.repositorio;

import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    T porId(Long id);
    void guardar(T t); // Realiza las operaciones de "insert" y "update"
    void eliminar(Long id);
}
