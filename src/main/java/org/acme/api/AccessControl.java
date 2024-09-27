package org.acme.api;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.dto.UserRoleRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/access/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(
        name = "Access Control Management",
        description = "*Endpoints for managing Access Control*"
)
public class AccessControl {
    @Path("add-role/{roleName}")
    @Operation(
            summary = "Add Role",
            description = ""
    )
    @POST
    @Authenticated
    public Response createRole(@PathParam("roleName") String roleName) {
        return Response.ok("").build();
    }


    @Path("user-role/")
    @Operation(
            summary = "Add new Role to User",
            description = ""
    )
    @POST
    @Authenticated
    public Response addRoleToUser(UserRoleRequest userRoleRequest) {
        return Response.ok("").build();
    }
}
