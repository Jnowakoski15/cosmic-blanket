package gov.nova.cosmic.licensing.dto;

import gov.nova.cosmic.licensing.entity.LicenseType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LicenseApplicationRequest {

    @NotBlank
    private String applicantFirstName;

    @NotBlank
    private String applicantLastName;

    @NotBlank
    @Email
    private String applicantEmail;

    @NotNull
    private LicenseType licenseType;

    public String getApplicantFirstName() { return applicantFirstName; }
    public void setApplicantFirstName(String applicantFirstName) { this.applicantFirstName = applicantFirstName; }
    public String getApplicantLastName() { return applicantLastName; }
    public void setApplicantLastName(String applicantLastName) { this.applicantLastName = applicantLastName; }
    public String getApplicantEmail() { return applicantEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }
    public LicenseType getLicenseType() { return licenseType; }
    public void setLicenseType(LicenseType licenseType) { this.licenseType = licenseType; }
}
