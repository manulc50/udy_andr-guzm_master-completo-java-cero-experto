package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import org.aguzman.hibernateapp.dominio.ClienteDto;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.Arrays;
import java.util.List;

public class HibernateQL {

    public static void main(String[] args) {

        // Lenguaje HQL(Hibernate Query Language) o JPQL(Java Persitence Query Language) - Estos lenguajes son similiares y se aplican sobre las clases entidad o de persistencia y no sobre las tablas de la base de datos

        EntityManager em = JpaUtil.getEntityManager();

        System.out.println("========== Consultar todos ==========");
        List<Cliente> clientes = em.createQuery("select c from Cliente c", Cliente.class).getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "c -> System.out.println(c)"

        System.out.println("========== Consultar por id ==========");
        Cliente cliente = em.createQuery("select c from Cliente c where c.id=:idCliente", Cliente.class)
                .setParameter("idCliente", 1L)
                .getSingleResult();
        System.out.println(cliente);

        System.out.println("========== Consultar sólo el nombre por id ==========");
        String nombreCliente = em.createQuery("select c.nombre from Cliente c where c.id=:id", String.class)
                .setParameter("id", 2L)
                .getSingleResult();
        System.out.println(nombreCliente);

        System.out.println("========== Consultar campos específicos por id ==========");
        // Es un array de Objetos porque en la consulta estamos seleccionando varias propiedades que son de diferentes tipos
        Object[] objectCliente = em.createQuery("select c.id, c.nombre, c.apellido from Cliente c where c.id=:id", Object[].class)
                .setParameter("id", 1L)
                .getSingleResult();
        Long id = (Long)objectCliente[0];
        String nombre = (String)objectCliente[1];
        String apellido = (String)objectCliente[2];
        System.out.println("id=" + id + ",nombre=" + nombre + ",apellido=" + apellido);

        System.out.println("========== Consultar campos específicos por id lista ==========");
        List<Object[]> registros = em.createQuery("select c.id, c.nombre, c.apellido from Cliente c", Object[].class).getResultList();
        for (Object[] reg: registros) {
            id = (Long)reg[0];
            nombre = (String)reg[1];
            apellido = (String)reg[2];
            System.out.println("id=" + id + ",nombre=" + nombre + ",apellido=" + apellido);
        }

        System.out.println("========== Consultar los clientes y su forma de pago ==========");
        registros = em.createQuery("select c, c.formaPago from Cliente c", Object[].class).getResultList();
        registros.forEach(reg -> {
            Cliente c = (Cliente)reg[0];
            String formaPago = (String)reg[1];
            System.out.println("formaPago=" + formaPago + "," + c);
        });

        System.out.println("========== Consulta que puebla y devuelve un objeto de una clase entity o de persistencia personalizada ==========");
        // Esta consulta obtiene todos los clientes de la base de datos y, por cada uno de ellos, crea un objeto de la clase entidad o de persistencia "Cliente" a partir de las propiedades o campos "nombre" y "apellido"
        clientes = em.createQuery("select new Cliente(c.nombre, c.apellido) from Cliente c", Cliente.class).getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "c -> System.out.println(c)"

        System.out.println("========== Consulta que puebla y devuelve un objeto de una clase personalizada ==========");
        // Esta consulta obtiene todos los clientes de la base de datos y, por cada uno de ellos, crea un objeto de la clase "ClienteDto" a partir de las propiedades o campos "nombre" y "apellido"
        // Especificamos el paquete donde se encuentra la clase "ClienteDto" porque, como no se trata de una clase entidad o de persistencia, JPA no la encuentra y se produce un error de ejecución
        List<ClienteDto> clientesDto = em.createQuery("select new org.aguzman.hibernateapp.dominio.ClienteDto(c.nombre, c.apellido) from Cliente c", ClienteDto.class).getResultList();
        clientesDto.forEach(System.out::println); // Versión simplificada de la expresión "c -> System.out.println(c)"

        System.out.println("========== Consultar los nombres de los clientes ==========");
        List<String> nombres = em.createQuery("select c.nombre from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println); // Versión simplificada de la expresión "n -> System.out.println(n)"

        System.out.println("========== Consulta los nombres únicos de los clientes ==========");
        nombres  = em.createQuery("select distinct(c.nombre) from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println); // Versión simplificada de la expresión "n -> System.out.println(n)"

        System.out.println("========== Consultar las formas de pago únicas de los clientes ==========");
        List<String> formasPago = em.createQuery("select distinct(c.formaPago) from Cliente c", String.class).getResultList();
        formasPago.forEach(System.out::println); // Versión simplificada de la expresión "formaPago -> System.out.println(formaPago)"

        System.out.println("========== Consultar el número de formas de pago de los clientes ==========");
        Long totalFormasPago = em.createQuery("select count(distinct(c.formaPago)) from Cliente c", Long.class).getSingleResult();
        System.out.println(totalFormasPago);

        System.out.println("========== Consulta con el nombre y apellido concatenados - Forma 1 ==========");
        nombres = em.createQuery("select concat(c.nombre, ' ', c.apellido) as nombreCompleto from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta con el nombre y apellido concatenados - Forma 2 ==========");
        nombres = em.createQuery("select c.nombre || ' ' || c.apellido as nombreCompleto from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta con el nombre y apellido concatenados en mayúscula ==========");
        // Los alías('as') son opcionales
        nombres = em.createQuery("select upper(concat(c.nombre, ' ', c.apellido)) as nombreCompleto from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta con el nombre y apellido concatenados en minúscula ==========");
        nombres = em.createQuery("select lower(concat(c.nombre, ' ', c.apellido)) as nombreCompleto from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta para buscar por nombre ==========");
        String param = "jH";
        // Usamos la función "upper" en la consulta(también se podría usar la función "lower") para que, si el motor de la base de datos es sensible a mayúsculas y minúsculas, las coincidencias con el término de búsqueda se hagan correctamente
        clientes = em.createQuery("select c from Cliente c where upper(c.nombre) like upper(:termino)", Cliente.class)
                .setParameter("termino", "%" + param + "%") // '%' significa cualquier cosa a la izquierda y a la derecha. En este caso, queremos que el término de búsqueda sea cualquier cosa que contenga el texto de "param"
                .getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta por rangos de números ==========");
        clientes = em.createQuery("select c from Cliente c where c.id between 2 and 5", Cliente.class).getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta por rangos de textos ==========");
        clientes = em.createQuery("select c from Cliente c where c.nombre between 'J' and 'Q'", Cliente.class).getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta con orden ascendente ==========");
        clientes = em.createQuery("select c from Cliente c order by c.nombre asc", Cliente.class).getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta con orden descendente ==========");
        clientes = em.createQuery("select c from Cliente c order by c.nombre desc", Cliente.class).getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta con orden sobre 2 propiedades ==========");
        clientes = em.createQuery("select c from Cliente c order by c.nombre asc, c.apellido asc", Cliente.class).getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta con total de registros de la tabla ==========");
        // Los alías('as') son opcionales
        Long totalRegistros = em.createQuery("select count(c) as total from Cliente c", Long.class).getSingleResult();
        System.out.println(totalRegistros);

        System.out.println("========== Consulta con el mínimo id ==========");
        // Los alías('as') son opcionales
        Long minId = em.createQuery("select min(c.id) as min from Cliente c", Long.class).getSingleResult();
        System.out.println(minId);

        System.out.println("========== Consulta con el máximo o último id ==========");
        // Los alías('as') son opcionales
        Long maxId = em.createQuery("select max(c.id) as min from Cliente c", Long.class).getSingleResult();
        System.out.println(maxId);

        System.out.println("========== Consulta con el nombre y su tamaño ==========");
        registros = em.createQuery("select c.nombre, length(c.nombre) from Cliente c", Object[].class).getResultList();
        registros.forEach(reg -> {
            String nom = (String)reg[0];
            Integer tam = (Integer)reg[1];
            System.out.println("nombre=" + nom + ",longitud=" + tam);
        });

        System.out.println("========== Consulta con el nombre más corto ==========");
        Integer minNombre = em.createQuery("select min(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println(minNombre);

        System.out.println("========== Consulta con el nombre más largo ==========");
        Integer maxNombre = em.createQuery("select max(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println(maxNombre);

        System.out.println("========== Consulta con el resumen de funciones de agregación count min max avg sum ==========");
        Object[] estadisticas = em.createQuery("select min(c.id), max(c.id), sum(c.id), count(c.id), avg(length(c.nombre)) from Cliente c", Object[].class).getSingleResult();
        Long min = (Long)estadisticas[0];
        Long max = (Long)estadisticas[1];
        Long sum = (Long)estadisticas[2];
        Long count = (Long)estadisticas[3];
        Double avg = (Double)estadisticas[4];
        System.out.println("min=" + min + ",max=" + max + ",sum=" + sum + ",count=" + count + ",avg=" + avg);

        System.out.println("========== Consulta con el nombre más corto y su longitud(Subconsulta) ==========");
        registros = em.createQuery("select c.nombre, length(c.nombre) from Cliente c where length(c.nombre) = (select min(length(c.nombre)) from Cliente c)", Object[].class).getResultList();
        registros.forEach(reg -> {
            String nom = (String)reg[0];
            Integer tam = (Integer)reg[1];
            System.out.println("nombre=" + nom + ",longitud=" + tam);
        });

        System.out.println("========== Consulta con el nombre más largo y su longitud(Subconsulta) ==========");
        registros = em.createQuery("select c.nombre, length(c.nombre) from Cliente c where length(c.nombre) = (select max(length(c.nombre)) from Cliente c)", Object[].class).getResultList();
        registros.forEach(reg -> {
            String nom = (String)reg[0];
            Integer tam = (Integer)reg[1];
            System.out.println("nombre=" + nom + ",longitud=" + tam);
        });

        System.out.println("========== Consulta para obtener el último cliente registrado ==========");
        cliente = em.createQuery("select c from Cliente c where c.id = (select max(c.id) from Cliente c)", Cliente.class).getSingleResult();
        System.out.println(cliente);

        System.out.println("========== Consulta usando where in ==========");
        clientes = em.createQuery("select c from Cliente c where c.id in :ids", Cliente.class)
                .setParameter("ids", Arrays.asList(1L, 2L, 11L, 40L))
                .getResultList();
        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        em.close();
    }
}
