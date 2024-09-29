package org.acme.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Audited
@SoftDelete(strategy= SoftDeleteType.ACTIVE,columnName = "deleted")
public class Role extends PanacheEntity {
    public String name;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    public Set<Permission> permissions = new HashSet<>();

    public static Role findByName(String name) {
        return find("name", name).firstResult();
    }
}
