package org.acme.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title="Authentication Service API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Mazhar",
                        url = "",
                        email = ""),
                license = @License(
                        name = "",
                        url = ""))
)
public class OpenApiConfig extends Application {
}
