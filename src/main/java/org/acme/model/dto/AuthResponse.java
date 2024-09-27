package org.acme.model.dto;

import org.acme.common.util.PasswordUtil;

public class AuthResponse {
    public String accessToken;

    public AuthResponse() {

    }

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
