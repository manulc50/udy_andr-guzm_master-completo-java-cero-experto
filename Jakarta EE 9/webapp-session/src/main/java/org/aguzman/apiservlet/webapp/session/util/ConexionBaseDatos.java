package org.aguzman.apiservlet.webapp.session.util;


// La idea es que, por cada petición http, se abra una nueva conexión a la base de datos y se cierre cuando termine de ejecutarse esa petición

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    //private static final String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC"; // Zona horaria estándar(Tiempo Universal Coordinado)
    private final static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=Europe/Madrid"; // Zona horaria de Madrid
    private final static String username = "root";
    private final static String password = "root";

    // Devuelve una nueva conexión a la base de datos
    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
