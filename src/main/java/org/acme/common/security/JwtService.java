package org.acme.common.security;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
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

    private final JWTParser jwtParser;

    public JwtService(JWTParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    public String generateToken(String username, List<String> roles) {
        return Jwt.issuer(appName)
                .upn(username)
                .groups(new HashSet<>(roles))
                .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                .signWithSecret(secretKey);
    }

    public String extractUser(String jwt)  {
        try {
            return jwtParser.verify(jwt, secretKey).getName();
        } catch (ParseException e) {
            return null;
        }
    }

   /* public String extractRollNumber(String jwt) {
        if (jwt == null) return null;
        return parseClaimsFromJWT(jwt).get("roll").toString();
    }*/

    /*public List<String> extractAuthorities(String jwt) {
        if (jwt == null) return new ArrayList<>();
        return (List<String>) parseClaimsFromJWT(jwt).get("authorities");
    }*/

    private JwtClaimsBuilder parseClaimsFromJWT(String jwt) {
        return Jwt.claims(jwt);
    }
}
