package org.mlorenzo.apiservlet.webapp.session.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginServiceSessionImpl implements LoginService {

    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // El método "getAttribute" devuelve un objeto de tipo Object. Por esta razón, tenemos que hacer un casting al tipo de objeto que nos interesa
        return Optional.ofNullable((String)session.getAttribute("username"));
    }
}
