package org.acme.api;

import io.quarkus.security.Authenticated;
import io.vertx.mutiny.core.http.HttpServerRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.dto.*;
import org.acme.service.AuthService;
import org.acme.service.UserService;
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
     private final UserService userService;
     private final AuthService authService;

    public AuthenticationController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Path("register")
    @Operation(
            summary = "User Registration",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response register(UserRequest userRequest) {
        userService.createUser(userRequest);
        return Response.ok(Message.of("User Registration Complete")).build();
    }

    @Path("login")
    @Operation(
            summary = "User Login",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response login(UserLogInRequest logInRequest) {
        AuthResponse authResponse = authService.login(logInRequest);
        return Response.ok(authResponse).build();
    }

    @Path("forget-password")
    @Operation(
            summary = "Forgot Password",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response forgotPassword(PasswordResetRequest passwordResetRequest) {
        authService.forgotPassword(passwordResetRequest);
        return Response.ok(Message.of("reset token send")).build();
    }

    @Path("reset-password/{token}")
    @Operation(
            summary = "Reset Password",
            description = "User info by Email. only active user info will show"
    )
    @PermitAll
    @POST
    public Response resetPassword(ResetPasswordRequest resetPasswordRequest,@PathParam("token") String token) {
        authService.resetPassword(resetPasswordRequest,token);
        return Response.ok(Message.of("reset password successfully")).build();
    }

    @Path("logout")
    @Operation(
            summary = "User logout",
            description = "User logout"
    )
    @Authenticated
    @POST
    public Response logout(@Context HttpServerRequest serverRequest) {
        authService.logout(serverRequest);
        return Response.ok(Message.of("Logout successfully")).build();
    }

}
