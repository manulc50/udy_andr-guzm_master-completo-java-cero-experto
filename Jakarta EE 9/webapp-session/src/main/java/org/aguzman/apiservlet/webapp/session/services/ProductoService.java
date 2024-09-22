package org.aguzman.apiservlet.webapp.session.services;

import org.aguzman.apiservlet.webapp.session.models.Categoria;
import org.aguzman.apiservlet.webapp.session.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listarProductos();
    Optional<Producto> porProductoId(Long id);
    void guardarProducto(Producto producto);
    void eliminarProducto(Long id);
    List<Categoria> listarCategorias();
    Optional<Categoria> porCategoriaId(Long id);
}
