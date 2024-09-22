package org.mlorenzo.apiservlet.webapp.session.services;

import org.mlorenzo.apiservlet.webapp.session.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService {

    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "notebook", "computación", 175000),
                new Producto(2L, "mesa escritorio", "oficina", 100000),
                new Producto(3L, "teclado mecánico", "computación", 40000));
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getId().equals(id))
                .findAny(); // Otra opción equivalente es usar el método "findFirst"
    }

    @Override
    public Optional<Producto> porNombre(String nombre) {
        return listar().stream().filter(p -> (nombre != null && !nombre.isBlank() && p.getNombre().contains(nombre)))
                .findFirst(); // Otra opción equivalente es usar el método "findAny"
    }
}
