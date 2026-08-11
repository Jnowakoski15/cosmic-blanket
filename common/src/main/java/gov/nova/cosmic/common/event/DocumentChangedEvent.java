package gov.nova.cosmic.common.event;

import java.time.Instant;
import java.util.UUID;

public class DocumentChangedEvent {

    public enum Action { CREATED, UPDATED, DELETED }

    private String sourceService;
    private String documentType;
    private UUID documentId;
    private Action action;
    private String title;
    private String content;
    private Instant timestamp;

    public DocumentChangedEvent() {}

    public DocumentChangedEvent(String sourceService, String documentType, UUID documentId,
                                 Action action, String title, String content) {
        this.sourceService = sourceService;
        this.documentType = documentType;
        this.documentId = documentId;
        this.action = action;
        this.title = title;
        this.content = content;
        this.timestamp = Instant.now();
    }

    public String getSourceService() { return sourceService; }
    public void setSourceService(String sourceService) { this.sourceService = sourceService; }
    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }
    public UUID getDocumentId() { return documentId; }
    public void setDocumentId(UUID documentId) { this.documentId = documentId; }
    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
