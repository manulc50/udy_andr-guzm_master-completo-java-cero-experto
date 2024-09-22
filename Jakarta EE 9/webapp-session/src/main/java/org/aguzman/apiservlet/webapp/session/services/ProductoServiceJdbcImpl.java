package org.aguzman.apiservlet.webapp.session.services;

import org.aguzman.apiservlet.webapp.session.exceptions.ServiceJdbcException;
import org.aguzman.apiservlet.webapp.session.models.Categoria;
import org.aguzman.apiservlet.webapp.session.models.Producto;
import org.aguzman.apiservlet.webapp.session.repositories.CategoriaRepositoryJdbcImpl;
import org.aguzman.apiservlet.webapp.session.repositories.ProductoRepositoryJdbcImpl;
import org.aguzman.apiservlet.webapp.session.repositories.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoServiceJdbcImpl implements ProductoService {

    private final Repository<Producto> repositoryProducto;
    private final Repository<Categoria> repositoryCategoria;

    public ProductoServiceJdbcImpl(Connection conn) {
        repositoryProducto = new ProductoRepositoryJdbcImpl(conn);
        repositoryCategoria = new CategoriaRepositoryJdbcImpl(conn);
    }

    @Override
    public List<Producto> listarProductos() {
        try {
            return repositoryProducto.listar();
        }
        catch(SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Producto> porProductoId(Long id) {
        try {
            return Optional.ofNullable(repositoryProducto.porId(id));
        }
        catch(SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardarProducto(Producto producto) {
        try {
            repositoryProducto.guardar(producto);
        }
        catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminarProducto(Long id) {
        try {
            repositoryProducto.eliminar(id);
        }
        catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategorias() {
        try {
            return repositoryCategoria.listar();
        }
        catch(SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Categoria> porCategoriaId(Long id) {
        try {
            return Optional.ofNullable(repositoryCategoria.porId(id));
        }
        catch(SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

}
