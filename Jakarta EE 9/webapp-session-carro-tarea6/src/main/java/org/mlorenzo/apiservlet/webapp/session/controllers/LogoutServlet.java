package org.mlorenzo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mlorenzo.apiservlet.webapp.session.services.LoginService;
import org.mlorenzo.apiservlet.webapp.session.services.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    // Realiza el proceso de logout
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> optionalUsername = loginService.getUsername(req);
        // Verificamos que existe un atributo de sesión con el username del usuario y, en caso afirmativo, la eliminamos
        if(optionalUsername.isPresent()) {
            HttpSession session = req.getSession();
            session.invalidate(); // Elimina todos los atributos de la sesión http
        }
        // Tras realizar el proceso de logout, redirige al servlet asociado a la ruta "/login.html"
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}
