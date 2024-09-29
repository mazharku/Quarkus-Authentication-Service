package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.common.security.JwtService;
import org.acme.common.util.PasswordUtil;
import org.acme.model.dto.AuthResponse;
import org.acme.model.dto.UserLogInRequest;
import org.acme.model.entities.Token;
import org.acme.model.entities.User;
import org.acme.service.AuthService;

import java.time.LocalDateTime;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;

    public AuthServiceImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public AuthResponse login(UserLogInRequest logInRequest) {
        User user = User.findByEmail(logInRequest.email);
        if(user==null) {
            throw new SecurityException("Invalid Email");
        }
        boolean verifyPassword = PasswordUtil.verifyPassword(logInRequest.password, user.password);
        if(!verifyPassword) {
            throw new SecurityException("Wrong Password");
        }
        String generateToken = jwtService.generateToken(user.email, user.roles.stream().map(e -> e.name).toList());
        Token token = new Token();
        token.value= generateToken;
        token.startTime= LocalDateTime.now();
        token.isRevoked=false;
        token.user=user;
       // token.persist();
        return  new AuthResponse(generateToken);
    }
}
