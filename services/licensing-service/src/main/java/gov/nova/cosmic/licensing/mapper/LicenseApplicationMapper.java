package gov.nova.cosmic.licensing.mapper;

import gov.nova.cosmic.licensing.dto.LicenseApplicationRequest;
import gov.nova.cosmic.licensing.dto.LicenseApplicationResponse;
import gov.nova.cosmic.licensing.dto.LicenseResponse;
import gov.nova.cosmic.licensing.entity.ApplicationStatus;
import gov.nova.cosmic.licensing.entity.License;
import gov.nova.cosmic.licensing.entity.LicenseApplication;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;

@ApplicationScoped
public class LicenseApplicationMapper {

    public LicenseApplication toEntity(LicenseApplicationRequest request) {
        LicenseApplication entity = new LicenseApplication();
        entity.applicantFirstName = request.getApplicantFirstName();
        entity.applicantLastName = request.getApplicantLastName();
        entity.applicantEmail = request.getApplicantEmail();
        entity.licenseType = request.getLicenseType();
        entity.status = ApplicationStatus.SUBMITTED;
        entity.submittedAt = Instant.now();
        return entity;
    }

    public LicenseApplicationResponse toResponse(LicenseApplication entity) {
        LicenseApplicationResponse response = new LicenseApplicationResponse();
        response.setId(entity.id);
        response.setApplicantFirstName(entity.applicantFirstName);
        response.setApplicantLastName(entity.applicantLastName);
        response.setApplicantEmail(entity.applicantEmail);
        response.setLicenseType(entity.licenseType);
        response.setStatus(entity.status);
        response.setSubmittedAt(entity.submittedAt);
        response.setUpdatedAt(entity.updatedAt);
        response.setNotes(entity.notes);
        return response;
    }

    public LicenseResponse toResponse(License entity) {
        LicenseResponse response = new LicenseResponse();
        response.setId(entity.id);
        response.setApplicationId(entity.applicationId);
        response.setLicenseNumber(entity.licenseNumber);
        response.setLicenseType(entity.licenseType);
        response.setHolderName(entity.holderName);
        response.setIssuedDate(entity.issuedDate);
        response.setExpiryDate(entity.expiryDate);
        response.setStatus(entity.status);
        return response;
    }
}
