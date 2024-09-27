package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.common.security.JwtService;
import org.acme.common.util.PasswordUtil;
import org.acme.model.dto.AuthResponse;
import org.acme.model.dto.UserLogInRequest;
import org.acme.model.entities.User;
import org.acme.service.AuthService;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;

    public AuthServiceImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(UserLogInRequest logInRequest) {
        User user = User.findByEmail(logInRequest.email);
        if(user==null) {
            throw new SecurityException("Invalid Email");
        }
        boolean verifyPassword = PasswordUtil.verifyPassword(logInRequest.password, user.password);
        if(!verifyPassword) {
            throw new SecurityException("Wrong Password");
        }
        String generateToken = jwtService.generateToken(user.firstName, user.roles.stream().map(e -> e.name).toList());
        return  new AuthResponse(generateToken);
    }
}
