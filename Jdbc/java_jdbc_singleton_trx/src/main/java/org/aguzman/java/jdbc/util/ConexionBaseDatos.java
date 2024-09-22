package org.aguzman.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase que genera y devuelve un singleton con la conexión a la base de datos
// La idea es tener una sóla conexión a la base de datos abierta para usarla en toda las consultas y operaciones que se hagan en la aplicación sobre la base de datos

public class ConexionBaseDatos {
    //private static final String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC"; // Zona horaria estándar(Tiempo Universal Coordinado)
    private final static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=Europe/Madrid"; // Zona horaria de Madrid
    private final static String username = "root";
    private final static String password = "root";
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        if(connection == null)
            connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}
