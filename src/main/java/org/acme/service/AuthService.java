package org.acme.service;

import org.acme.model.dto.AuthResponse;
import org.acme.model.dto.UserLogInRequest;

public interface AuthService {
    AuthResponse login(UserLogInRequest logInRequest);
}
