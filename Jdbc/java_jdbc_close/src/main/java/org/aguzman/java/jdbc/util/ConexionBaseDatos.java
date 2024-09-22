package org.aguzman.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Ya no es una clase singleton que devuelve siempre la misma conexión a la base de datos porque, ahora, en esta aplicación cada operación sobre la base de datos utiliza y cierra su propia conexión

public class ConexionBaseDatos {
    //private static final String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC"; // Zona horaria estándar(Tiempo Universal Coordinado)
    private final static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=Europe/Madrid"; // Zona horaria de Madrid
    private final static String username = "root";
    private final static String password = "root";

    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
