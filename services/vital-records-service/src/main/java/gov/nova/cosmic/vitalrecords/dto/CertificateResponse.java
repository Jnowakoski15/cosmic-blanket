package gov.nova.cosmic.vitalrecords.dto;

import gov.nova.cosmic.vitalrecords.entity.CertificateType;
import java.time.LocalDate;
import java.util.UUID;

public class CertificateResponse {

    private UUID id;
    private UUID requestId;
    private String certificateNumber;
    private CertificateType certificateType;
    private LocalDate issuedDate;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getRequestId() { return requestId; }
    public void setRequestId(UUID requestId) { this.requestId = requestId; }
    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }
    public CertificateType getCertificateType() { return certificateType; }
    public void setCertificateType(CertificateType certificateType) { this.certificateType = certificateType; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }
}
