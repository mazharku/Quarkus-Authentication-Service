package org.acme.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "roles")
@Audited
public class Role extends PanacheEntity {
    public String name;

    public static Role findByName(String name) {
        return find("name", name).firstResult();
    }
}
