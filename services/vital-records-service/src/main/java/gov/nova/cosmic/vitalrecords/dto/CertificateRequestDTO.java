package gov.nova.cosmic.vitalrecords.dto;

import gov.nova.cosmic.vitalrecords.entity.CertificateType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CertificateRequestDTO {

    @NotBlank
    private String requesterFirstName;

    @NotBlank
    private String requesterLastName;

    @NotBlank
    @Email
    private String requesterEmail;

    @NotNull
    private CertificateType certificateType;

    @NotBlank
    private String subjectFirstName;

    @NotBlank
    private String subjectLastName;

    private LocalDate subjectDateOfBirth;

    private LocalDate subjectDateOfDeath;

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
}
