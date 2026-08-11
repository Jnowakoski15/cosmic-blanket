package gov.nova.cosmic.vitalrecords.dto;

import gov.nova.cosmic.vitalrecords.entity.RequestStatus;

public class StatusUpdateRequest {

    private RequestStatus status;

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
}
