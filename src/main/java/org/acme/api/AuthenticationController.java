package org.acme.api;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.dto.PasswordResetRequest;
import org.acme.model.dto.ResetPasswordRequest;
import org.acme.model.dto.UserLogInRequest;
import org.acme.model.dto.UserRequest;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(
        name = "Authentication Management",
        description = "*Endpoints for managing user Authentication*"
)
public class AuthenticationController {

    @Path("register")
    @Operation(
            summary = "User Registration",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response register(UserRequest userRequest) {
        return Response.ok("").build();
    }

    @Path("login")
    @Operation(
            summary = "User Login",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response login(UserLogInRequest logInRequest) {
        return Response.ok("").build();
    }

    @Path("forget-password")
    @Operation(
            summary = "Forgot Password",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response forgotPassword(PasswordResetRequest passwordResetRequest) {
        return Response.ok("").build();
    }

    @Path("reset-password/{token}")
    @Operation(
            summary = "Reset Password",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response resetPassword(ResetPasswordRequest resetPasswordRequest,@PathParam("token") String token) {
        return Response.ok("").build();
    }

}
