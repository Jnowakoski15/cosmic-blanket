package gov.nova.cosmic.vitalrecords.mapper;

import gov.nova.cosmic.vitalrecords.dto.CertificateRequestDTO;
import gov.nova.cosmic.vitalrecords.dto.CertificateRequestResponse;
import gov.nova.cosmic.vitalrecords.dto.CertificateResponse;
import gov.nova.cosmic.vitalrecords.entity.Certificate;
import gov.nova.cosmic.vitalrecords.entity.CertificateRequest;
import gov.nova.cosmic.vitalrecords.entity.RequestStatus;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class VitalRecordsMapper {

    public CertificateRequest toEntity(CertificateRequestDTO dto) {
        CertificateRequest entity = new CertificateRequest();
        entity.requesterFirstName = dto.getRequesterFirstName();
        entity.requesterLastName = dto.getRequesterLastName();
        entity.requesterEmail = dto.getRequesterEmail();
        entity.certificateType = dto.getCertificateType();
        entity.subjectFirstName = dto.getSubjectFirstName();
        entity.subjectLastName = dto.getSubjectLastName();
        entity.subjectDateOfBirth = dto.getSubjectDateOfBirth();
        entity.subjectDateOfDeath = dto.getSubjectDateOfDeath();
        entity.status = RequestStatus.SUBMITTED;
        entity.trackingNumber = "VR-" + UUID.randomUUID().toString().substring(0, 8);
        entity.submittedAt = Instant.now();
        return entity;
    }

    public CertificateRequestResponse toResponse(CertificateRequest entity) {
        CertificateRequestResponse response = new CertificateRequestResponse();
        response.setId(entity.id);
        response.setRequesterFirstName(entity.requesterFirstName);
        response.setRequesterLastName(entity.requesterLastName);
        response.setRequesterEmail(entity.requesterEmail);
        response.setCertificateType(entity.certificateType);
        response.setSubjectFirstName(entity.subjectFirstName);
        response.setSubjectLastName(entity.subjectLastName);
        response.setSubjectDateOfBirth(entity.subjectDateOfBirth);
        response.setSubjectDateOfDeath(entity.subjectDateOfDeath);
        response.setStatus(entity.status);
        response.setTrackingNumber(entity.trackingNumber);
        response.setSubmittedAt(entity.submittedAt);
        response.setUpdatedAt(entity.updatedAt);
        return response;
    }

    public CertificateResponse toResponse(Certificate entity) {
        CertificateResponse response = new CertificateResponse();
        response.setId(entity.id);
        response.setRequestId(entity.requestId);
        response.setCertificateNumber(entity.certificateNumber);
        response.setCertificateType(entity.certificateType);
        response.setIssuedDate(entity.issuedDate);
        return response;
    }
}
