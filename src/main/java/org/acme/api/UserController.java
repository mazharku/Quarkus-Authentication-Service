package org.acme.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.dto.Message;
import org.acme.model.dto.UserBasicInfoResponse;
import org.acme.model.dto.UserRequest;
import org.acme.service.UserService;

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    UserService userService;

    @GET()
    @Path("")
    public Response hellResponse() {
        List<UserBasicInfoResponse> userBasicInfoResponses = userService.userInfos();
        return Response.ok(userBasicInfoResponses).build();
    }

    @POST
    @Path("")
    public Response createUser(UserRequest userRequest) {
        userService.createUser(userRequest);
        return Response.ok(Message.of("user created successfully!")).build();
    }
}
