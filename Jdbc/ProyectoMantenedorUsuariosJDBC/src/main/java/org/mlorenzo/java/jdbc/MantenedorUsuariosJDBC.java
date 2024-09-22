package org.mlorenzo.java.jdbc;

import org.mlorenzo.java.jdbc.modelo.Usuario;
import org.mlorenzo.java.jdbc.repositorio.Repositorio;
import org.mlorenzo.java.jdbc.repositorio.UsuarioRepositorioImpl;
import org.mlorenzo.java.jdbc.util.ConexionBaseDatos;

import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MantenedorUsuariosJDBC {

    public static void main(String[] args) {

        int opcionIndice = 0;

        Map<String, Integer> operaciones = new HashMap<>();
        operaciones.put("Actualizar", 1);
        operaciones.put("Eliminar", 2);
        operaciones.put("Agregar", 3);
        operaciones.put("Listar", 4);
        operaciones.put("Salir", 5);

        Object[] opArreglo = operaciones.keySet().toArray();

        try {
            Connection conn = ConexionBaseDatos.getInstance();
            Repositorio<Usuario> repositorio = new UsuarioRepositorioImpl();

            while (opcionIndice != 5) {
                Object opcion = JOptionPane.showInputDialog(null,
                        "Seleccione una Operación",
                        "Mantenedor de Usuarios",
                        JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[0]);

                if (opcion == null)
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una operación");
                else {
                    opcionIndice = operaciones.get(opcion.toString());

                    switch(opcionIndice) {
                        case 1: {
                            final String titulo = "Actualización de un usuario";
                            long id = Long.parseLong(JOptionPane.showInputDialog(null,
                                    "Introduce el id del usuario",
                                    titulo, JOptionPane.QUESTION_MESSAGE));

                            Usuario usuario = repositorio.porId(id);
                            if(usuario != null) {
                                String username = JOptionPane.showInputDialog(null,
                                        "Introduce el username del usuario",
                                        titulo, JOptionPane.QUESTION_MESSAGE);
                                String password = JOptionPane.showInputDialog(null,
                                        "Introduce el password del usuario",
                                        titulo, JOptionPane.QUESTION_MESSAGE);
                                String email = JOptionPane.showInputDialog(null,
                                        "Introduce el email del usuario",
                                        titulo, JOptionPane.QUESTION_MESSAGE);

                                usuario.setUsername(username);
                                usuario.setPassword(password);
                                usuario.setEmail(email);

                                repositorio.guardar(usuario);

                                JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente");
                            }
                            else
                                JOptionPane.showMessageDialog(null, "El usuario con id " + id + " no existe en la base de datos");

                            break;
                        }
                        case 2: {
                            long id = Long.parseLong(JOptionPane.showInputDialog(null,
                                    "Introduce el id del usuario",
                                    "Eliminación de un usuario", JOptionPane.QUESTION_MESSAGE));

                            Usuario usuario = repositorio.porId(id);
                            if(usuario != null) {
                                repositorio.eliminar(id);
                                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
                            }
                            else
                                JOptionPane.showMessageDialog(null, "El usuario con id " + id + " no existe en la base de datos");

                            break;
                        }
                        case 3: {
                            final String titulo = "Creación de un usuario";

                            String username = JOptionPane.showInputDialog(null,
                                    "Introduce el username del usuario",
                                    titulo, JOptionPane.QUESTION_MESSAGE);
                            String password = JOptionPane.showInputDialog(null,
                                    "Introduce el password del usuario",
                                    titulo, JOptionPane.QUESTION_MESSAGE);
                            String email = JOptionPane.showInputDialog(null,
                                    "Introduce el email del usuario",
                                    titulo, JOptionPane.QUESTION_MESSAGE);

                            Usuario usuario = new Usuario(username, password, email);

                            repositorio.guardar(usuario);

                            JOptionPane.showMessageDialog(null, "Usuario creado correctamente");

                            break;
                        }
                        case 4: {
                            System.out.println("========== lista de usuarios ==========");
                            repositorio.listar().forEach(System.out::println); // Versión simplificada de la expresión "u -> System.out.println(u)"
                            break;
                        }
                        case 5: {
                            conn.close();
                            break;
                        }
                    }
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
