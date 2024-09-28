package org.acme.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token extends PanacheEntity {
    public String value;

    public LocalDateTime startTime;

    public LocalDateTime endTime;

    public boolean isRevoked;

    public String revokedBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

}
