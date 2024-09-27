package org.acme.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import org.acme.model.dto.UserBasicInfoResponse;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {
    public String firstName;
    public String lastName;
    public String email;
    @Transient
    public String password;
    public boolean isActive;
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> roles;

    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public static List<UserBasicInfoResponse> getUsersBasicInfo() {
        return findAll()
                .project(UserBasicInfoResponse.class)
                .stream()
                .toList();
    }
}
