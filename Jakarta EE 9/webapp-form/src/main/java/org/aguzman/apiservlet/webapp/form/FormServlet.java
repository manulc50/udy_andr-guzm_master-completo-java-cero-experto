package org.aguzman.apiservlet.webapp.form;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

// Un Servlet es una clase que hereda la clase HttpServlet

@WebServlet("/registro") // Con esta anotación mapeamos este servlet con la ruta "/registro"
public class FormServlet extends HttpServlet {

    // No se debe sobrescribir el método "service" de la clase padre "HttpServlet"
    // El método "service" se encarga de invocar a los métodos "doGet", "doPost", "doPut", etc..., dependiendo del tipo de la petición http que llegue(Get, Post, Put, etc...)
    // Por lo tanto, sólo tenemos que sobrescribir estos métodos "doGet", "doPost", "doPut", etc..., de la clase padre "HttpServlet" para codificar nuestras implementaciones

    // Procesa el formulario y sirve un documento HTML
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene los parámetros de los campos del formulario que viajan en el cuerpo o body de la petición http
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String pais = req.getParameter("pais");
        String[] lenguajes = req.getParameterValues("lenguajes");
        String[] roles = req.getParameterValues("roles");
        String idioma = req.getParameter("idioma");
        boolean habilitar = req.getParameter("habilitar") != null && req.getParameter("habilitar").equals("on");
        String secreto = req.getParameter("secreto");

        // Validación de los parámetros de los campos del formulario
        Map<String, String> errores = new HashMap<>();

        if(username == null || username.isBlank())
            errores.put("username","El username es requerido");

        if(password == null || password.isBlank())
            errores.put("password","El password es requerido");

        if(email == null || !email.contains("@"))
            errores.put("email","El email es requerido y debe tener un formato de email");

        if(pais == null || pais.trim().equals(""))
            errores.put("pais","El país es requerido");

        if(lenguajes == null || lenguajes.length == 0)
            errores.put("lenguajes","Debe seleccionar al menos un lenguaje de programación");

        if(roles == null || roles.length == 0)
            errores.put("roles","Debe seleccionar al menos un role");

        if(idioma == null)
            errores.put("idioma","Debe seleccionar un idioma");

        if(errores.isEmpty()) {
			// Indicamos el tipo de contenido del cuerpo o body de la respuesta http
			resp.setContentType("text/html;charset=UTF-8");
			
            // Para poder añadir contenido al cuerpo o body de la respuesta
            // En vez de cerrar el PrintWriter de forma manual invocando a su método "close", como esta clase "PrintWriter" implementa la interfaz "Closeable",
            // podemos usar esta clase junto con un bloque "try-with-resources" para que de forma automática se cierre al final del bloque
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Resultado form</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Resultado form</h1>");
                out.println("        <ul>");
                out.println("            <li>Username: " + username + "</li>");
                out.println("            <li>Contraseña: " + password + "</li>");
                out.println("            <li>Email: " + email + "</li>");
                out.println("            <li>País: " + pais + "</li>");
                out.println("            <li>Lenguajes: <ul>");
                Arrays.asList(lenguajes).forEach(lenguaje -> out.println("                <li>" + lenguaje + "</li>"));
                out.println("            </ul></li>");
                out.println("            <li>Roles: <ul>");
                Arrays.asList(roles).forEach(role -> out.println("                <li>" + role + "</li>"));
                out.println("            </ul></li>");
                out.println("            <li>Idioma: " + idioma + "</li>");
                out.println("            <li>Habilitado: " + habilitar + "</li>");
                out.println("            <li>Secreto: " + secreto + "</li>");
                out.println("        </ul>");
                out.println("        <p><a href=\"/webapp-form/index.jsp\">Volver</a></p>");
                out.println("    </body>");
                out.println("</html>");
            }
        }
        else{
            // Creamos el atributo "errores" en la petición http para usarlo en la vista "index.jsp" con los errores de la validación del formulario
            // Nota: Ahora, la vista "index" tiene que ser un JSP en vez de un HTML para que pueda soportar código Java y, de esta forma, podamos mostrar los errores en esa vista
            req.setAttribute("errores", errores);
            // Despacha o redirige(forward) a la vista "index.jsp"
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
