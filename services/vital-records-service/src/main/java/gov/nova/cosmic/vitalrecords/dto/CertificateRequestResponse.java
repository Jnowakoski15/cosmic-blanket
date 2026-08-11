package gov.nova.cosmic.vitalrecords.dto;

import gov.nova.cosmic.vitalrecords.entity.CertificateType;
import gov.nova.cosmic.vitalrecords.entity.RequestStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class CertificateRequestResponse {

    private UUID id;
    private String requesterFirstName;
    private String requesterLastName;
    private String requesterEmail;
    private CertificateType certificateType;
    private String subjectFirstName;
    private String subjectLastName;
    private LocalDate subjectDateOfBirth;
    private LocalDate subjectDateOfDeath;
    private RequestStatus status;
    private String trackingNumber;
    private Instant submittedAt;
    private Instant updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getRequesterFirstName() { return requesterFirstName; }
    public void setRequesterFirstName(String requesterFirstName) { this.requesterFirstName = requesterFirstName; }
    public String getRequesterLastName() { return requesterLastName; }
    public void setRequesterLastName(String requesterLastName) { this.requesterLastName = requesterLastName; }
    public String getRequesterEmail() { return requesterEmail; }
    public void setRequesterEmail(String requesterEmail) { this.requesterEmail = requesterEmail; }
    public CertificateType getCertificateType() { return certificateType; }
    public void setCertificateType(CertificateType certificateType) { this.certificateType = certificateType; }
    public String getSubjectFirstName() { return subjectFirstName; }
    public void setSubjectFirstName(String subjectFirstName) { this.subjectFirstName = subjectFirstName; }
    public String getSubjectLastName() { return subjectLastName; }
    public void setSubjectLastName(String subjectLastName) { this.subjectLastName = subjectLastName; }
    public LocalDate getSubjectDateOfBirth() { return subjectDateOfBirth; }
    public void setSubjectDateOfBirth(LocalDate subjectDateOfBirth) { this.subjectDateOfBirth = subjectDateOfBirth; }
    public LocalDate getSubjectDateOfDeath() { return subjectDateOfDeath; }
    public void setSubjectDateOfDeath(LocalDate subjectDateOfDeath) { this.subjectDateOfDeath = subjectDateOfDeath; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
