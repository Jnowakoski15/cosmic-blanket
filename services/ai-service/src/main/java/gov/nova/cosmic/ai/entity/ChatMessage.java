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
@Table(schema = "ai", name = "messages")
public class ChatMessage extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "conversation_id")
    public UUID conversationId;

    @Column(name = "role")
    public String role;

    @Column(name = "content")
    public String content;

    @Column(name = "created_at")
    public Instant createdAt;
}
