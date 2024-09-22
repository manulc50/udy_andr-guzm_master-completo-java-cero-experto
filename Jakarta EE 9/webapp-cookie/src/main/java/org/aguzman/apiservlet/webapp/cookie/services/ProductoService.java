package org.aguzman.apiservlet.webapp.cookie.services;

import org.aguzman.apiservlet.webapp.cookie.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> buscarProducto(String nombre);
}
