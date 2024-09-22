package org.aguzman.apiservlet.webapp.session.services;

import org.aguzman.apiservlet.webapp.session.models.Categoria;
import org.aguzman.apiservlet.webapp.session.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService {

    @Override
    public List<Producto> listarProductos() {
        return Arrays.asList(new Producto(1L, "notebook", 175000, "computación"),
                new Producto(2L, "mesa escritorio", 100000, "oficina"),
                new Producto(3L, "teclado mecánico", 40000, "computación"));
    }

    @Override
    public Optional<Producto> porProductoId(Long id) {
        return listarProductos().stream().filter(p -> p.getId().equals(id))
                .findAny(); // Otra opción equivalente es usar el método "findFirst"
    }

    @Override
    public void guardarProducto(Producto producto) {

    }

    @Override
    public void eliminarProducto(Long id) {

    }

    @Override
    public List<Categoria> listarCategorias() {
        return null;
    }

    @Override
    public Optional<Categoria> porCategoriaId(Long id) {
        return Optional.empty();
    }

}
