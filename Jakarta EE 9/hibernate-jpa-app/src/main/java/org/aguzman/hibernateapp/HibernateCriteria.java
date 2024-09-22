package org.aguzman.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.aguzman.hibernateapp.entity.Cliente;
import org.aguzman.hibernateapp.util.JpaUtil;

import java.util.Arrays;
import java.util.List;

public class HibernateCriteria {

    public static void main(String[] args) {

        /* Uso del API Criteria - Alternativa a los lenguaje HQL o JPQL(son similiares) de forma programática y dinámica.
           Es decir, cuando tenemos que construir las consultas de forma de dinámica usando lógica como bloques
           if-else, etc..., es mucho mejor usar la API Criteria en lugar de  los lenguajes HQL o JPQL */

        EntityManager em = JpaUtil.getEntityManager();

        CriteriaBuilder criteria = em.getCriteriaBuilder();

        System.out.println("========== Consultar todos ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        CriteriaQuery<Cliente> query = criteria.createQuery(Cliente.class);

        // from Cliente c
        Root<Cliente> from = query.from(Cliente.class);

        // select c
        query.select(from);

        List<Cliente> clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Listar where equals ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.nombre = :nombre"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // 2 Formas equivalentes de pasar parámetros a una consulta
        // Primera forma
        // select c
        // where c.nombre = 'Jhon'
        /*query.select(from).where(criteria.equal(from.get("nombre"), "Jhon"));
        clientes = em.createQuery(query).getResultList();*/

        // Segunda forma
        // select c
        // where c.nombre = 'Jhon'
        ParameterExpression<String> nombreParam = criteria.parameter(String.class,"nombre");
        query.select(from).where(criteria.equal(from.get("nombre"), nombreParam));
        clientes = em.createQuery(query).setParameter(nombreParam, "Jhon").getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Usando where like para buscar cliente por nombre ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where upper(c.nombre) like upper(:termino)"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // 2 Formas equivalentes de pasar parámetros a una consulta
        // Primera forma
        // select c
        // where c.nombre like '%jh%'
        /*query.select(from).where(criteria.like(from.get("nombre"), "%jh%")); // '%' significa cualquier cosa a la izquierda y a la derecha. En este caso, queremos que el término de búsqueda sea cualquier cosa que contenga el texto "jh"
        clientes = em.createQuery(query).getResultList(); */

        // Segunda forma
        // select c
        // where c.nombre like '%jh%'
        ParameterExpression<String> nombreParamLike = criteria.parameter(String.class,"nombreParam");
        // Usamos la función "upper" en la consulta(también se podría usar la función "lower") para que, si el motor de la base de datos es sensible a mayúsculas y minúsculas, las coincidencias con el término de búsqueda se hagan correctamente
        query.select(from).where(criteria.like(criteria.upper(from.get("nombre")), criteria.upper(nombreParamLike)));
        clientes = em.createQuery(query).setParameter(nombreParamLike, "%jh%").getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Usando where con between para rangos ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.id between 2 and 6"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // select c
        // where c.id between 2 and 6
        query.select(from).where(criteria.between(from.get("id"),2L, 6L));

        clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta where in ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.nombre in ('Andrés', 'Jhon', 'Lou')"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // Primera forma
        // select c
        // where c.id between 2 and 6
        /*query.select(from).where(from.get("nombre").in("Andrés", "Jhon", "Lou"));
        clientes = em.createQuery(query).getResultList(); */

        // Segunda forma
        // select c
        // where c.id between 2 and 6
        /*query.select(from).where(from.get("nombre").in(Arrays.asList("Andrés", "Jhon", "Lou")));
        clientes = em.createQuery(query).getResultList(); */

        // Tercera forma
        // select c
        // where c.id between 2 and 6
        ParameterExpression<List> listParam = criteria.parameter(List.class, "nombres");
        query.select(from).where(from.get("nombre").in(listParam));
        clientes = em.createQuery(query).setParameter(listParam, Arrays.asList("Andrés", "Jhon", "Lou")).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Filtrar usando el predicado mayor igual que ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.id >= 3L"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // select c
        // where c.id >= 3L
        query.select(from).where(criteria.ge(from.get("id"), 3L));

        clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Filtrar usando el predicado mayor que ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where length(c.nombre) > 5"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // select c
        // where length(c.nombre) > 5
        query.select(from).where(criteria.gt(criteria.length(from.get("nombre")), 5));

        clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Filtrar usando el predicado menor que ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where length(c.nombre) < 5"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // select c
        // where length(c.nombre) < 5
        query.select(from).where(criteria.lt(criteria.length(from.get("nombre")), 5));

        clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Filtrar usando el predicado menor igual que ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.id <= 3L"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // select c
        // where c.id <= 3L
        query.select(from).where(criteria.le(from.get("id"), 3L));

        clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta con los predicados conjunción(and) y disyunción(or) ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.id >= 4L and (c.nombre = 'Andres' or c.formaPago = 'debito')"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        Predicate porNombre = criteria.equal(from.get("nombre"), "Andres");
        Predicate porFormaPago = criteria.equal(from.get("formaPago"), "debito");
        Predicate p3 = criteria.ge(from.get("id"), 4L);

        // select c
        // where c.id >= 4L and (c.nombre = 'Andres' or c.formaPago = 'debito')
        query.select(from).where(criteria.and(p3, criteria.or(porNombre, porFormaPago)));

        clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta con order by asc desc ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c order by c.nombre asc, c.apellido desc"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // select c
        // order by c.nombre asc, c.apellido desc
        query.select(from).orderBy(criteria.asc(from.get("nombre")),criteria.desc(from.get("apellido")));

        clientes = em.createQuery(query).getResultList();

        clientes.forEach(System.out::println); // Versión simplificada de la expresión "cliente -> System.out.println(cliente)"

        System.out.println("========== Consulta por id ==========");
        // Equivalente al lenguaje HQL o JPQL "select c from Cliente c where c.id = 1L"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        query = criteria.createQuery(Cliente.class);

        // from Cliente c
        from = query.from(Cliente.class);

        // select c
        // where c.id = 1L
        ParameterExpression<Long> idParam = criteria.parameter(Long.class, "id");
        query.select(from).where(criteria.equal(from.get("id"), idParam));
        Cliente cliente = em.createQuery(query).setParameter(idParam, 1L).getSingleResult();

        System.out.println(cliente);

        System.out.println("========== Consulta sólo el nombre de los clientes ==========");
        // Equivalente al lenguaje HQL o JPQL "select c.nombre from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        CriteriaQuery<String> queryString = criteria.createQuery(String.class);

        // from Cliente c
        from = queryString.from(Cliente.class);

        // select c.nombre
        queryString.select(from.get("nombre"));

        List<String> nombres = em.createQuery(queryString).getResultList();

        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta sólo los nombres únicos de los clientes en mayúscula ==========");
        // Equivalente al lenguaje HQL o JPQL "select upper(distinct(c.nombre)) from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryString = criteria.createQuery(String.class);

        // from Cliente c
        from = queryString.from(Cliente.class);

        // select upper(distinct(c.nombre))
        queryString.select(criteria.upper(from.get("nombre"))).distinct(true);

        nombres = em.createQuery(queryString).getResultList();

        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta por nombres y apellidos concatenados ==========");
        // Equivalente al lenguaje HQL o JPQL "select concat(c.nombre, ' ', c.apellido) as nombreCompleto from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryString = criteria.createQuery(String.class);

        // from Cliente c
        from = queryString.from(Cliente.class);

        // select concat(c.nombre, ' ', c.apellido)
        queryString.select(criteria.concat(criteria.concat(from.get("nombre"), " "),from.get("apellido")));

        nombres = em.createQuery(queryString).getResultList();

        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta por nombres y apellidos concatenados en minúscula ==========");
        // Equivalente al lenguaje HQL o JPQL "select lower(concat(c.nombre, ' ', c.apellido)) from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryString = criteria.createQuery(String.class);

        // from Cliente c
        from = queryString.from(Cliente.class);

        // select lower(concat(c.nombre, ' ', c.apellido))
        queryString.select(criteria.lower(criteria.concat(criteria.concat(from.get("nombre"), " "),from.get("apellido"))));

        nombres = em.createQuery(queryString).getResultList();

        nombres.forEach(System.out::println); // Versión simplificada de la expresión "nombre -> System.out.println(nombre)"

        System.out.println("========== Consulta de campos personalizados del entity Cliente ==========");
        // Equivalente al lenguaje HQL o JPQL "select c.id, c.nombre, c.apellido from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        CriteriaQuery<Object[]> queryObject = criteria.createQuery(Object[].class);

        // from Cliente c
        from = queryObject.from(Cliente.class);

        // select c.id, c.nombre, c.apellido
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido"));

        List<Object[]> registros = em.createQuery(queryObject).getResultList();

        registros.forEach(reg -> {
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id=" + id + ",nombre=" + nombre + ",apellido=" + apellido);
        });

        System.out.println("========== Consulta de campos personalizados del entity Cliente con where ==========");
        // Equivalente al lenguaje HQL o JPQL "select c.id, c.nombre, c.apellido from Cliente c where c.nombre like '%lu%'"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryObject = criteria.createQuery(Object[].class);

        // from Cliente c
        from = queryObject.from(Cliente.class);

        // select c.id, c.nombre, c.apellido
        // where c.nombre like '%lu%'
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(criteria.like(from.get("nombre"), "%lu%"));

        registros = em.createQuery(queryObject).getResultList();

        registros.forEach(reg -> {
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id=" + id + ",nombre=" + nombre + ",apellido=" + apellido);
        });

        System.out.println("========== Consulta de campos personalizados del entity Cliente con where id ==========");
        // Equivalente al lenguaje HQL o JPQL "select c.id, c.nombre, c.apellido from Cliente c where c.id = 1L"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryObject = criteria.createQuery(Object[].class);

        // from Cliente c
        from = queryObject.from(Cliente.class);

        // select c.id, c.nombre, c.apellido
        // where c.id = 1L
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(criteria.equal(from.get("id"), 1L));

        Object[] registro = em.createQuery(queryObject).getSingleResult();

        Long id = (Long) registro[0];
        String nombre = (String) registro[1];
        String apellido = (String) registro[2];
        System.out.println("id=" + id + ",nombre=" + nombre + ",apellido=" + apellido);

        System.out.println("========== Contar registros de la consulta con count ==========");
        // Equivalente al lenguaje HQL o JPQL "select count(c) from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        CriteriaQuery<Long> queryLong = criteria.createQuery(Long.class);

        // from Cliente c
        from = queryLong.from(Cliente.class);

        // select count(c)
        queryLong.select(criteria.count(from));

        Long count = em.createQuery(queryLong).getSingleResult();

        System.out.println(count);

        System.out.println("========== Consulta con la suma de los ids ==========");
        // Equivalente al lenguaje HQL o JPQL "select sum(c.id) from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryLong = criteria.createQuery(Long.class);

        // from Cliente c
        from = queryLong.from(Cliente.class);

        // select sum(c.id)
        queryLong.select(criteria.sum(from.get("id")));

        Long sum = em.createQuery(queryLong).getSingleResult();

        System.out.println(sum);

        System.out.println("========== Consulta con el máximo id ==========");
        // Equivalente al lenguaje HQL o JPQL "select max(c.id) from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryLong = criteria.createQuery(Long.class);

        // from Cliente c
        from = queryLong.from(Cliente.class);

        // select max(c.id)
        queryLong.select(criteria.max(from.get("id")));

        Long max = em.createQuery(queryLong).getSingleResult();

        System.out.println(max);

        System.out.println("========== Consulta con el mínimo id ==========");
        // Equivalente al lenguaje HQL o JPQL "select min(c.id) from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryLong = criteria.createQuery(Long.class);

        // from Cliente c
        from = queryLong.from(Cliente.class);

        // select min(c.id)
        queryLong.select(criteria.min(from.get("id")));

        Long min = em.createQuery(queryLong).getSingleResult();

        System.out.println(min);

        System.out.println("========== Ejemplo de varios resultados de funciones de agragación en una sóla consulta ==========");
        // Equivalente al lenguaje HQL o JPQL "select count(c), sum(c.id), max(c.id), min(c.id) from Cliente c"

        // Crea una consulta indicando el tipo de dato esperado del resultado de la consulta
        queryObject = criteria.createQuery(Object[].class);

        // from Cliente c
        from = queryObject.from(Cliente.class);

        // select count(c), sum(c.id), max(c.id), min(c.id)
        queryObject.multiselect(criteria.count(from), criteria.sum(from.get("id")), criteria.max(from.get("id")), criteria.min(from.get("id")));

        registro = em.createQuery(queryObject).getSingleResult();

        count = (Long) registro[0];
        sum = (Long) registro[1];
        max = (Long) registro[2];
        min = (Long) registro[3];
        System.out.println("count=" + count + ",sum=" + sum + ",max=" + max + ",min=" + min);

        em.close();
    }
}
