package gov.nova.cosmic.licensing.dto;

import gov.nova.cosmic.licensing.entity.ApplicationStatus;

public class StatusUpdateRequest {

    private ApplicationStatus status;

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}
