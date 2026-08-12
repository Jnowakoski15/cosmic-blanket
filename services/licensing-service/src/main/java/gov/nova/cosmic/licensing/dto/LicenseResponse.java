package gov.nova.cosmic.licensing.dto;

import gov.nova.cosmic.licensing.entity.LicenseStatus;
import gov.nova.cosmic.licensing.entity.LicenseType;
import java.time.LocalDate;
import java.util.UUID;

public class LicenseResponse {

    private UUID id;
    private UUID applicationId;
    private String licenseNumber;
    private LicenseType licenseType;
    private String holderName;
    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private LicenseStatus status;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getApplicationId() { return applicationId; }
    public void setApplicationId(UUID applicationId) { this.applicationId = applicationId; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public LicenseType getLicenseType() { return licenseType; }
    public void setLicenseType(LicenseType licenseType) { this.licenseType = licenseType; }
    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public LicenseStatus getStatus() { return status; }
    public void setStatus(LicenseStatus status) { this.status = status; }
}
