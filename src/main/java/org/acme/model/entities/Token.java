package org.acme.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token extends PanacheEntity {
    public String value;

    public LocalDateTime startTime;

    public LocalDateTime endTime;

    public boolean isRevoked;

    public String revokedBy;

    @SoftDelete(strategy = SoftDeleteType.ACTIVE, columnName = "deleted")
    public boolean deleted;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    public User user;

    public static Token findByToken(String token){
        return Token.find("value", token).firstResult();
    }

}
