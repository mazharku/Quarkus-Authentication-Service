package org.acme.common;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.model.dto.Message;

@Provider
public class SystemExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
         return Response
                 .status(Response.Status.NOT_FOUND)
                 .entity(Message.of(exception.getMessage()))
                 .build();
    }
}
