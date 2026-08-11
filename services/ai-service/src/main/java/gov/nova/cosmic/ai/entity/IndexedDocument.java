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
@Table(schema = "ai", name = "indexed_documents")
public class IndexedDocument extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "source_service")
    public String sourceService;

    @Column(name = "document_type")
    public String documentType;

    @Column(name = "document_id")
    public UUID documentId;

    @Column(name = "title")
    public String title;

    @Column(name = "chunk_count")
    public int chunkCount;

    @Column(name = "indexed_at")
    public Instant indexedAt;
}
