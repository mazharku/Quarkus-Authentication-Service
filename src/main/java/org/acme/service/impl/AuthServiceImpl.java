package org.acme.service.impl;

import io.vertx.mutiny.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.common.exceptions.ResourceNotFound;
import org.acme.common.security.JwtService;
import org.acme.common.util.PasswordUtil;
import org.acme.model.dto.AuthResponse;
import org.acme.model.dto.PasswordResetRequest;
import org.acme.model.dto.ResetPasswordRequest;
import org.acme.model.dto.UserLogInRequest;
import org.acme.model.entities.Token;
import org.acme.model.entities.User;
import org.acme.service.AuthService;

import java.security.SecureRandom;
import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
        token.persist();
        return  new AuthResponse(generateToken);
    }

    @Override
    @Transactional
    public void logout(HttpServerRequest serverRequest) {
        String authorizationHeader = serverRequest.getHeader("Authorization");
        String authToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authToken = authorizationHeader.substring(7);
            Token token = Token.findByToken(authToken);
            token.isRevoked=true;
            token.deleted=false;
            token.persist();
        } else {
            throw new SecurityException("can't logout");
        }

    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest, String token) {
        Token token1 = Token.findByToken(token);
        if(token1==null){
            throw new ResourceNotFound("Invalid token for resetting password");
        }
        if(!Objects.equals(resetPasswordRequest.password, resetPasswordRequest.reTypePassword)){
            throw new ResourceNotFound("password not matched!");
        }
        token1.deleted=false;
        User user = token1.user;
        user.password= PasswordUtil.hashPassword(resetPasswordRequest.password);
        token1.persist();

    }

    @Override
    @Transactional
    public void forgotPassword(PasswordResetRequest passwordResetRequest) {
        User user = User.findByEmail(passwordResetRequest.email);
        if(user==null) {
            throw new ResourceNotFound("user not registered!");
        }
        SecureRandom random = new SecureRandom();
        String resetToken = String.valueOf(random.nextInt(900000) +100000);
        Token token = new Token();
        token.value=resetToken;
        token.user=user;
        token.startTime=LocalDateTime.now();
        token.endTime= LocalDateTime.MAX;
        token.persist();
    }
}
