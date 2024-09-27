package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.dto.Message;
import org.acme.model.dto.UserBasicInfoResponse;
import org.acme.model.dto.UserRequest;
import org.acme.model.dto.UserStatusUpdateRequest;
import org.acme.service.UserService;

import java.util.List;

@Path("/user/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET()
    @Path("{email}")
    public Response getUserinfoByEmail(@PathParam("email") String email) {
        UserBasicInfoResponse userBasicInfoResponses = userService.getUserInfoByEmail(email);
        return Response.ok(userBasicInfoResponses).build();
    }

    @GET()
    @Path("list")
    public Response getAllUserInfoWithPagination() {
        List<UserBasicInfoResponse> userBasicInfoResponses = userService.userInfos();
        return Response.ok(userBasicInfoResponses).build();
    }

    @POST
    @Path("")
    public Response createUser(UserRequest userRequest) {
        userService.createUser(userRequest);
        return Response.ok(Message.of("user created successfully!")).build();
    }

    @POST
    @Path("/deactivate")
    public Response deActivateUser(UserStatusUpdateRequest request) {
        userService.deactivateUser(request.email);
        return Response.ok(Message.of("Deactivate user successfully")).build();
    }

    @POST
    @Path("/activate")
    public Response activateUser(UserStatusUpdateRequest request) {
        userService.activateUser(request.email);
        return Response.ok(Message.of("Activate user successfully")).build();
    }

}
