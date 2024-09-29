package org.acme.service;

import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.vertx.core.http.HttpServerRequest;
import org.acme.model.dto.RolePermissionRequest;
import org.acme.model.dto.RolePermissionResponse;
import org.acme.model.dto.UserRoleRequest;
import org.acme.model.dto.UserRoleResponse;

import java.util.List;

public interface AccessControl {
    void addRoleToUser(UserRoleRequest userRoleRequest);
    void removeRoleFromUser(UserRoleRequest userRoleRequest);
    UserRoleResponse getUserRoles(Integer userId);
    UserRoleResponse getCurrentUserRoles(HttpServerRequest request);
    void addPermissionToRole(RolePermissionRequest rolePermissionRequest);
    void removePermissionFromRole(RolePermissionRequest rolePermissionRequest);
    RolePermissionResponse getRolePermissions(String roleName);
    List<RolePermissionResponse> getCurrentUserPermissions(HttpServerRequest request);
}
