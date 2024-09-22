package org.mlorenzo.apiservlet.webapp.session.services;

import org.mlorenzo.apiservlet.webapp.session.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> porId(Long id);
    Optional<Producto> porNombre(String nombre);
}
