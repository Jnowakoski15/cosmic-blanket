package gov.nova.cosmic.common.event;

import java.time.Instant;

public class CitizenActivityEvent {

    private String action;
    private String serviceArea;
    private Instant timestamp;

    public CitizenActivityEvent() {}

    public CitizenActivityEvent(String action, String serviceArea) {
        this.action = action;
        this.serviceArea = serviceArea;
        this.timestamp = Instant.now();
    }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getServiceArea() { return serviceArea; }
    public void setServiceArea(String serviceArea) { this.serviceArea = serviceArea; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
