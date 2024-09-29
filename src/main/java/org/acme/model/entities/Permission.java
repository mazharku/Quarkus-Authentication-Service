package org.acme.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Table(name = "permissions")
@SoftDelete(strategy = SoftDeleteType.ACTIVE, columnName = "deleted")
public class Permission extends PanacheEntity {
    public String name;

    public static Permission findByName(String name) {
        return find("name", name).firstResult();
    }
}
