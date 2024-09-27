package org.acme.common.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class JwtService {
    @ConfigProperty(name = "quarkus.application.name")
    String appName;
    @ConfigProperty(name = "jwt.verify.secret")
    String secretKey;

    public String generateToken(String username, List<String> roles) {
        return Jwt.issuer(appName)
                .upn(username)
                .groups(new HashSet<>(roles))
                .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                .signWithSecret(secretKey);
    }
}
