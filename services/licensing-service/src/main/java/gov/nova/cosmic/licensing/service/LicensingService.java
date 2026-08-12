package gov.nova.cosmic.licensing.service;

import gov.nova.cosmic.common.dto.PagedResponse;
import gov.nova.cosmic.common.exception.NotFoundException;
import gov.nova.cosmic.licensing.dto.LicenseApplicationRequest;
import gov.nova.cosmic.licensing.dto.LicenseApplicationResponse;
import gov.nova.cosmic.licensing.dto.LicenseResponse;
import gov.nova.cosmic.licensing.entity.ApplicationStatus;
import gov.nova.cosmic.licensing.entity.License;
import gov.nova.cosmic.licensing.entity.LicenseApplication;
import gov.nova.cosmic.licensing.mapper.LicenseApplicationMapper;
import gov.nova.cosmic.licensing.messaging.LicensingEventPublisher;
import gov.nova.cosmic.licensing.repository.LicenseApplicationRepository;
import gov.nova.cosmic.licensing.repository.LicenseRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class LicensingService {

    @Inject
    LicenseApplicationRepository applicationRepository;

    @Inject
    LicenseRepository licenseRepository;

    @Inject
    LicenseApplicationMapper mapper;

    @Inject
    LicensingEventPublisher eventPublisher;

    public LicenseApplicationResponse submitApplication(LicenseApplicationRequest request) {
        LicenseApplication entity = mapper.toEntity(request);
        applicationRepository.persist(entity);
        eventPublisher.publishApplicationCreated(entity);
        return mapper.toResponse(entity);
    }

    public LicenseApplicationResponse getApplication(UUID id) {
        LicenseApplication entity = applicationRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException("License application not found: " + id);
        }
        return mapper.toResponse(entity);
    }

    public PagedResponse<LicenseApplicationResponse> listApplications(int page, int size) {
        List<LicenseApplicationResponse> content = applicationRepository
                .findAll()
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(mapper::toResponse)
                .toList();
        long total = applicationRepository.count();
        return new PagedResponse<>(content, page, size, total);
    }

    public LicenseApplicationResponse updateStatus(UUID id, ApplicationStatus newStatus) {
        LicenseApplication entity = applicationRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException("License application not found: " + id);
        }
        entity.status = newStatus;
        entity.updatedAt = Instant.now();
        applicationRepository.persist(entity);
        eventPublisher.publishStatusChanged(entity);
        return mapper.toResponse(entity);
    }

    public LicenseResponse getLicense(String licenseNumber) {
        License entity = licenseRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new NotFoundException("License not found: " + licenseNumber));
        return mapper.toResponse(entity);
    }

    public PagedResponse<LicenseResponse> listLicenses(int page, int size) {
        List<LicenseResponse> content = licenseRepository
                .findAll()
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(mapper::toResponse)
                .toList();
        long total = licenseRepository.count();
        return new PagedResponse<>(content, page, size, total);
    }
}
