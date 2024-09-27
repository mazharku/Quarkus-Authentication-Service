package org.acme.api;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.dto.*;
import org.acme.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/user/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(
        name = "User Management",
        description = "*Endpoints for managing user management*"
)
public class UserController {

    final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET()
    @Path("{email}")
    @Operation(
            summary = "User info By Email",
            description = "User info by Email. only active user info will show"
    )
    @Authenticated
    public Response getUserinfoByEmail(@PathParam("email") String email) {
        UserBasicInfoResponse userBasicInfoResponses = userService.getUserInfoByEmail(email);
        return Response.ok(userBasicInfoResponses).build();
    }

    @GET()
    @Path("list")
    @Operation(
            summary = "All user info",
            description = "All user info in pagination list. only active user info will show"
    )
    @Authenticated
    public Response getAllUserInfoWithPagination() {
        List<UserBasicInfoResponse> userBasicInfoResponses = userService.userInfos();
        return Response.ok(userBasicInfoResponses).build();
    }

    @POST
    @Path("")
    @Operation(
            summary = "Create user",
            description = "Create a new user. email should be unique"
    )
    @Authenticated
    public Response createUser(UserRequest userRequest) {
        userService.createUser(userRequest);
        return Response.ok(Message.of("user created successfully!")).build();
    }

    @POST
    @Path("/deactivate")
    @Operation(
            summary = "Deactivate User",
            description = "Deactivate a user"
    )
    @Authenticated
    public Response deActivateUser(UserStatusUpdateRequest request) {
        userService.deactivateUser(request.email);
        return Response.ok(Message.of("Deactivate user successfully")).build();
    }

    @POST
    @Path("/activate")
    @Operation(
            summary = "Activate User",
            description = "Activate a user"
    )
    @Authenticated
    public Response activateUser(UserStatusUpdateRequest request) {
        userService.activateUser(request.email);
        return Response.ok(Message.of("Activate user successfully")).build();
    }

    @Path("change-password")
    @Operation(
            summary = "Change Password",
            description = "User info by Email. only active user info will show"
    )
    @POST
    @Authenticated
    public Response changePassword(ResetPasswordRequest resetPasswordRequest) {
        return Response.ok("").build();
    }


}
