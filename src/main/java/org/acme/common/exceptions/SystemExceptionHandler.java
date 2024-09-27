package org.acme.common.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.model.dto.Message;

@Provider
public class SystemExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
       return switch (exception) {
            case ResourceNotFound ex -> Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(Message.of(ex.getMessage()))
                    .build();
            case RuntimeException ex ->  Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(Message.of(ex.getMessage()))
                    .build();
            default -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Message.of("Something went wrong")).build();
        };

    }
}
