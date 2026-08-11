package gov.nova.cosmic.common.event;

import java.time.Instant;
import java.util.UUID;

public class ServiceNotificationEvent {

    private String eventType;
    private String sourceService;
    private UUID entityId;
    private String recipientEmail;
    private String message;
    private Instant timestamp;

    public ServiceNotificationEvent() {}

    public ServiceNotificationEvent(String eventType, String sourceService, UUID entityId,
                                     String recipientEmail, String message) {
        this.eventType = eventType;
        this.sourceService = sourceService;
        this.entityId = entityId;
        this.recipientEmail = recipientEmail;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getSourceService() { return sourceService; }
    public void setSourceService(String sourceService) { this.sourceService = sourceService; }
    public UUID getEntityId() { return entityId; }
    public void setEntityId(UUID entityId) { this.entityId = entityId; }
    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
