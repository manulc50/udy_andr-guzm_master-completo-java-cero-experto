package org.aguzman.java.jdbc.repositorio;

import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    List<T> listar() throws SQLException;
    T porId(Long id) throws SQLException;
    void guardar(T t) throws SQLException; // Realiza las operaciones de "insert" y "update"
    void eliminar(Long id) throws SQLException;
}
