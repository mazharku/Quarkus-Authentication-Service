package org.acme.api;

import io.quarkus.security.Authenticated;
import io.vertx.core.http.HttpServerRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.dto.*;
import org.acme.service.AccessControl;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/access/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(
        name = "Access Control Management",
        description = "*Endpoints for managing Access Control*"
)
public class AccessControlController {
    private final AccessControl accessControl;

    public AccessControlController(AccessControl accessControl) {
        this.accessControl = accessControl;
    }

    @Path("user-role")
    @Operation(
            summary = "Add Role to User",
            description = ""
    )
    @POST
    @RolesAllowed("Admin")
    public Response addRoleToUser(UserRoleRequest userRoleRequest) {
        accessControl.addRoleToUser(userRoleRequest);
        return Response.ok(Message.of("Role is added to the user")).build();
    }

    @Path("user-role")
    @Operation(
            summary = "Remove Role from User",
            description = ""
    )
    @DELETE
    @RolesAllowed("Admin")
    public Response removeRoleFromUser(UserRoleRequest userRoleRequest) {
        accessControl.removeRoleFromUser(userRoleRequest);
        return Response.ok(Message.of("Role is removed from the user")).build();
    }

    @Path("user-role/{user_id}")
    @Operation(
            summary = "Get User Roles",
            description = ""
    )
    @GET
    @RolesAllowed("Admin")
    public Response getUserRoles(@PathParam("user_id") Integer userId) {
        UserRoleResponse response = accessControl.getUserRoles(userId);
        return Response.ok(response).build();
    }

    @Path("user-role")
    @Operation(
            summary = "Get Current logged In User Roles",
            description = ""
    )
    @GET
    @Authenticated
    public Response getCurrentUserRoles(@Context HttpServerRequest request) {
        UserRoleResponse response = accessControl.getCurrentUserRoles(request);
        return Response.ok(response).build();
    }

    @Path("role-permission")
    @Operation(
            summary = "Add Permission to Role",
            description = ""
    )
    @POST
    @RolesAllowed("Admin")
    public Response addPermissionToRole(RolePermissionRequest rolePermissionRequest) {
        accessControl.addPermissionToRole(rolePermissionRequest);
        return Response.ok(Message.of("Permission is added to the Role")).build();
    }

    @Path("role-permission")
    @Operation(
            summary = "Remove Permission from Role",
            description = ""
    )
    @DELETE
    @RolesAllowed("Admin")
    public Response removePermissionFromRole(RolePermissionRequest rolePermissionRequest) {
        accessControl.removePermissionFromRole(rolePermissionRequest);
        return Response.ok(Message.of("Permission is removed from the Role")).build();
    }

    @Path("role-permission/{role_name}")
    @Operation(
            summary = "Get Role Permissions",
            description = ""
    )
    @GET
    @RolesAllowed("Admin")
    public Response getRolePermissions(@PathParam("role_name") String roleName) {
        RolePermissionResponse response = accessControl.getRolePermissions(roleName);
        return Response.ok(response).build();
    }


    @Path("role-permission")
    @Operation(
            summary = "Get Current logged In User Permissions",
            description = ""
    )
    @GET
    @Authenticated
    public Response getCurrentUserPermissions(@Context HttpServerRequest request) {
        List<RolePermissionResponse> response = accessControl.getCurrentUserPermissions(request);
        return Response.ok(response).build();
    }
}
