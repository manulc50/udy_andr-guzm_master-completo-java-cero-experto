package org.aguzman.java.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;


// Clase que crea un singleton de un pool de conexiones y devuelve conexiones de ese pool

public class ConexionBaseDatos {
    //private static final String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC"; // Zona horaria estándar(Tiempo Universal Coordinado)
    private final static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=Europe/Madrid"; // Zona horaria de Madrid
    private final static String username = "root";
    private final static String password = "root";
    private static BasicDataSource pool; // Manejador del pool de conexiones

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }

    private static BasicDataSource getInstance() {
        if(pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);
            // Esta configuraciones son opcionales. Si no se indican, por defecto "initialSize" es 0, "minIdle" es 0, "maxIdle" es 8 y "maxtotal" es 8
            pool.setInitialSize(3); // Establece el número de conexiones habilitadas cuando se cree o se inicie el pool
            pool.setMinIdle(3); // Establece el mínimo número de conexiones inhabilitadas, es decir, el mínimo número de conexiones que están esperando a ser usadas
            pool.setMaxIdle(8); // Establece el máximo número de conexiones inhabilitadas, es decir, el máximo número de conexiones que están esperando a ser usadas
            pool.setMaxTotal(8); // Establece el número total de conexiones habilitas e inhabilitadas del pool
        }

        return pool;
    }
}
