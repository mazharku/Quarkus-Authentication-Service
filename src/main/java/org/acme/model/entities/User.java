package org.acme.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.acme.model.dto.UserBasicInfoResponse;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Audited
@SoftDelete(strategy= SoftDeleteType.ACTIVE, columnName = "deleted")
public class User extends PanacheEntity {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public boolean isActive;
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
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

    public static UserBasicInfoResponse getUserBasicInfoByEmail(String email) {
        return find("isActive = true and email = ?1", email)
                .project(UserBasicInfoResponse.class)
                .firstResult();
    }

}
