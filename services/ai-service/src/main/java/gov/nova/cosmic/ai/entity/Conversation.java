package gov.nova.cosmic.ai.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "ai", name = "conversations")
public class Conversation extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "started_at")
    public Instant startedAt;

    @Column(name = "updated_at")
    public Instant updatedAt;
}
