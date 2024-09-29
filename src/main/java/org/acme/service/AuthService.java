package org.acme.service;

import io.vertx.mutiny.core.http.HttpServerRequest;
import org.acme.model.dto.AuthResponse;
import org.acme.model.dto.PasswordResetRequest;
import org.acme.model.dto.ResetPasswordRequest;
import org.acme.model.dto.UserLogInRequest;

public interface AuthService {
    AuthResponse login(UserLogInRequest logInRequest);

    void logout(HttpServerRequest serverRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest, String token);

    void forgotPassword(PasswordResetRequest passwordResetRequest);
}
