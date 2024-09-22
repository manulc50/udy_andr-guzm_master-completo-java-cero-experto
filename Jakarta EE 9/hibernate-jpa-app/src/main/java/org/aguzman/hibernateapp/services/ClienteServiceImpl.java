package org.aguzman.hibernateapp.services;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.repositories.ClienteRepository;
import org.aguzman.hibernateapp.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements  ClienteService{

    private final CrudRepository<Cliente> repository;
    private final EntityManager em;

    public ClienteServiceImpl(EntityManager em) {
        this.em = em;
        this.repository = new ClienteRepository(em);
    }

    // No requiere transacción porque no modifica ninguna tabla de la base de datos
    @Override
    public List<Cliente> listar() {
        return repository.listar();
    }

    // No requiere transacción porque no modifica ninguna tabla de la base de datos
    @Override
    public Optional<Cliente> porId(Long id) {
        return Optional.ofNullable(repository.porId(id));
    }

    @Override
    public void guardar(Cliente cliente) {
        try {
            em.getTransaction().begin(); // Empieza una transacción
            repository.guardar(cliente);
            em.getTransaction().commit(); // Termina la transacción y hace efectivos los cambios de la transacción en la base de datos
        }
        catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback(); // En caso de producirse algún error durante la transacción, hacemos un rollback
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            em.getTransaction().begin(); // Empieza una transacción
            repository.eliminar(id);
            em.getTransaction().commit(); // Termina la transacción y hace efectivos los cambios de la transacción en la base de datos
        }
        catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback(); // En caso de producirse algún error durante la transacción, hacemos un rollback
        }
    }
}
