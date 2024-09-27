package org.acme.common.security;

import io.quarkus.logging.Log;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.service.UserService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Priority(1)
public class JwtIdentityProvider implements IdentityProvider<TokenAuthenticationRequest> {
    private final JWTParser jwtParser;
    private final UserService userService;
    @ConfigProperty(name = "jwt.verify.secret")
    String secretKey;

    public JwtIdentityProvider(JWTParser jwtParser, UserService userService) {
        this.jwtParser = jwtParser;
        this.userService = userService;
    }

    @Override
    public Class<TokenAuthenticationRequest> getRequestType() {
        return TokenAuthenticationRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(TokenAuthenticationRequest request, AuthenticationRequestContext requestContext) {
        String token = request.getToken().getToken();
        return Uni.createFrom().item(() -> {
            try {
                return jwtParser.verify(token, secretKey);
            } catch (ParseException e) {
                Log.debug(e);
                return null;
            }
        }).flatMap(jwt -> {
            if (jwt == null) {
                return Uni.createFrom().nullItem();
            }
            return requestContext.runBlocking(() -> {
                if (userService.isUserValid(jwt.getName())) {
                    return new JwtSecurityIdentity(jwt, jwt.getName());
                }
                return null;
            });
        });
    }
}
