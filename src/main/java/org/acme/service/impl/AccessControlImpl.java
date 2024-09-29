package org.acme.service.impl;

import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.common.exceptions.ResourceNotFound;
import org.acme.common.security.JwtService;
import org.acme.model.dto.RolePermissionRequest;
import org.acme.model.dto.RolePermissionResponse;
import org.acme.model.dto.UserRoleRequest;
import org.acme.model.dto.UserRoleResponse;
import org.acme.model.entities.Permission;
import org.acme.model.entities.Role;
import org.acme.model.entities.User;
import org.acme.service.AccessControl;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AccessControlImpl implements AccessControl {
    final JwtService jwtService;

    public AccessControlImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void addRoleToUser(UserRoleRequest userRoleRequest) {
        Role role = Role.findByName(userRoleRequest.roleName);
        if(role==null) {
            throw new ResourceNotFound("Role not found");
        }
        User user = User.findById(userRoleRequest.userId);
        user.roles.add(role);
        user.persist();
    }

    @Override
    @Transactional
    public void removeRoleFromUser(UserRoleRequest userRoleRequest) {
        Role role = Role.findByName(userRoleRequest.roleName);
        if(role==null) {
            throw new ResourceNotFound("Role not found");
        }
        User user = User.findById(userRoleRequest.userId);
        user.roles.remove(role);
        user.persist();
    }

    @Override
    public UserRoleResponse getUserRoles(Integer userId) {
        User user = User.findById(userId);
        UserRoleResponse userRoleResponse = new UserRoleResponse();
        userRoleResponse.userId = userId;
        userRoleResponse.roles = user.roles.stream().map(e-> e.name).toList();
        return userRoleResponse;
    }

    @Override
    public UserRoleResponse getCurrentUserRoles(HttpServerRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        String email = jwtService.extractUser(token);

        User user = User.findByEmail(email);

        UserRoleResponse response = new UserRoleResponse();
        response.userId= Math.toIntExact(user.id);
        response.roles = user.roles.stream().map(e->e.name).toList();
        return response;
    }

    @Override
    @Transactional
    public void addPermissionToRole(RolePermissionRequest rolePermissionRequest) {
        Permission permission = Permission.findByName(rolePermissionRequest.permission);
        if(permission==null) {
            throw new ResourceNotFound("Permission not found");
        }
        Role role = Role.findByName(rolePermissionRequest.roleName);
        if(role==null) {
            throw new ResourceNotFound("Role not found");
        }
        role.permissions.add(permission);
        role.persist();

    }

    @Override
    @Transactional
    public void removePermissionFromRole(RolePermissionRequest rolePermissionRequest) {
        Permission permission = Permission.findByName(rolePermissionRequest.permission);
        if(permission==null) {
            throw new ResourceNotFound("Permission not found");
        }
        Role role = Role.findByName(rolePermissionRequest.roleName);
        if(role==null) {
            throw new ResourceNotFound("Role not found");
        }
        role.permissions.remove(permission);
        role.persist();
    }

    @Override
    public RolePermissionResponse getRolePermissions(String roleName) {
        Role role = Role.findByName(roleName);
        if(role==null) {
            throw new ResourceNotFound("Role not found");
        }
        RolePermissionResponse response = new RolePermissionResponse();
        response.roleName = roleName;
        response.permissions = role.permissions.stream().map(r-> r.name).toList();
        return response;
    }

    @Override
    public List<RolePermissionResponse> getCurrentUserPermissions(HttpServerRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
             token = authorizationHeader.substring(7);
        }
        String email = jwtService.extractUser(token);

        List<RolePermissionResponse> rolePermissionResponses = new ArrayList<>();
        User user = User.findByEmail(email);
        for(Role role : user.roles) {
            RolePermissionResponse response = new RolePermissionResponse();
            response.roleName = role.name;
            response.permissions = role.permissions.stream().map(e->e.name).toList();
            rolePermissionResponses.add(response);
        }
        return rolePermissionResponses;
    }
}
