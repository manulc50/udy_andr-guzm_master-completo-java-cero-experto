package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.services.ClienteService;
import org.aguzman.hibernateapp.services.ClienteServiceImpl;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class HibernateCRUDService {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        ClienteService service = new ClienteServiceImpl(em);

        System.out.println("========== Listar ==========");
        List<Cliente> clientes = service.listar();
        clientes.forEach(System.out::println); // Versión simplificada de la expresion "c -> System.out.println(c)"

        System.out.println("========== Obtener por Id ==========");
        Optional<Cliente> optionalCliente = service.porId(1L); // No se realiza una consulta SQL a la base de datos por debajo porque aprovecha la consulta anterior, que obtiene el listado de clientes y el resultado se encuenta almacenado en memoria(Contexto de JPA) como si fuera una caché, para recuperar el cliente solicitado

        // 2 formas equivalentes

        // Primera forma
        /*if(optionalCliente.isPresent())
            System.out.println(optionalCliente.get());*/

        // Segunda forma
        optionalCliente.ifPresent(System.out::println); // Versión simplificada de la expresion "c -> System.out.println(c)"

        System.out.println("========== Insertar nuevo cliente ==========");
        Cliente cliente = new Cliente();
        cliente.setNombre("Lucy");
        cliente.setApellido("Mena");
        cliente.setFormaPago("paypal");

        service.guardar(cliente); // A partir de aquí, el objeto "cliente" está en el contexto de JPA almacenado en memoria como si fuera una caché
        System.out.println("Cliente registrado con éxito");
        service.listar().forEach(System.out::println); // Versión simplificada de la expresion "c -> System.out.println(c)"

        System.out.println("========== Editar cliente ==========");
        cliente.setFormaPago("credito");
        service.guardar(cliente); // No hace falta localizar previamente al cliente porque el objeto "cliente" ya está en el contexto de JPA
        System.out.println("Cliente editado con éxito");
        service.listar().forEach(System.out::println); // Versión simplificada de la expresion "c -> System.out.println(c)"

        System.out.println("========== Eliminar cliente ==========");
        service.eliminar(cliente.getId()); // No hace falta localizar previamente al cliente porque el objeto "cliente" ya está en el contexto de JPA
        System.out.println("Cliente eliminado con éxito");
        service.listar().forEach(System.out::println); // Versión simplificada de la expresion "c -> System.out.println(c)"

        em.close();
    }
}
