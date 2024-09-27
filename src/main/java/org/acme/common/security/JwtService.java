package org.acme.common.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class JwtService {
    public String generateToken(String username, Set<String> roles) {
            return Jwt.issuer("auth-service")
                    .upn(username)
                    .groups(roles)
                    .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                    .signWithSecret("agagagbdnjhfhfnfjvbndHGDGDdjdj23dbhdh");
    }
}
