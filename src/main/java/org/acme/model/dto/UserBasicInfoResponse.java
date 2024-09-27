package org.acme.model.dto;

import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.model.entities.Role;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RegisterForReflection
public class UserBasicInfoResponse {
    public String fullName;
    public String email;
    public String roleName;

    public UserBasicInfoResponse(String firstName, String lastName, String email, @ProjectedFieldName("roles") Role role) {
        this(firstName, lastName, email);
        this.roleName = role.name;
    }

    public UserBasicInfoResponse(String firstName, String lastName, String email) {
        this.fullName = Stream.of(firstName, lastName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
        this.email = email;
    }
}
