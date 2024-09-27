package org.acme.common.security;

import com.sun.security.auth.UserPrincipal;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.security.Permission;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JwtSecurityIdentity implements SecurityIdentity {
    private final JsonWebToken jwt;
    private final String username;

    public JwtSecurityIdentity(JsonWebToken jwt, String username) {
        this.jwt = jwt;
        this.username = username;
    }

    @Override
    public Principal getPrincipal() {
      return new UserPrincipal(username);
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public Set<String> getRoles() {
        return jwt.getGroups();
    }

    @Override
    public boolean hasRole(String s) {
        return jwt.getGroups().contains(s);
    }

    @Override
    public <T extends Credential> T getCredential(Class<T> aClass) {
        return null;
    }

    @Override
    public Set<Credential> getCredentials() {
        return new HashSet<>();
    }

    @Override
    public <T> T getAttribute(String s) {
        return (T) jwt.getClaim(s);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public Uni<Boolean> checkPermission(Permission permission) {
        return null;
    }
}
