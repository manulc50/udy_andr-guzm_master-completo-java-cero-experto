package org.aguzman.java.jdbc.servicio;


import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;

import java.sql.SQLException;
import java.util.List;

public interface Servicio {
    List<Producto> listarProductos() throws SQLException;
    Producto porProductoId(Long id) throws SQLException;
    Producto guardarProducto(Producto producto) throws SQLException;
    void eliminarProducto(Long id) throws SQLException;
    List<Categoria> listarCategorias() throws SQLException;
    Categoria porCategoriaId(Long id) throws SQLException;
    Categoria guardarCategoria(Categoria categoria) throws SQLException;
    void eliminarCategoria(Long id) throws SQLException;
    void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException;
}
