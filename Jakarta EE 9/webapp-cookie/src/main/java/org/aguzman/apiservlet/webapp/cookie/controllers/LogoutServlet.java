package org.aguzman.apiservlet.webapp.cookie.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.cookie.services.LoginService;
import org.aguzman.apiservlet.webapp.cookie.services.LoginServiceImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    // Realiza el proceso de logout
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService loginService = new LoginServiceImpl();
        Optional<String> optionalUsername = loginService.getUsername(req);
        // Verificamos que existe una cookie con el username del usuario y, en caso afirmativo, la eliminamos
        // Para eliminar una cookie existente, basta con crear una nueva cookie con el mismo nombre que la cookie que se desea eliminar y se establece su tiempo de vida en 0
        if(optionalUsername.isPresent()) {
            Cookie usernameCookie = new Cookie("username", "");
            usernameCookie.setMaxAge(0);
            resp.addCookie(usernameCookie);
        }
        // Tras realizar el proceso de logout, redirige al servlet asociado a la ruta "/login.html"
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}
