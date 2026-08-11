package gov.nova.cosmic.vitalrecords.service;

import gov.nova.cosmic.common.dto.PagedResponse;
import gov.nova.cosmic.common.exception.NotFoundException;
import gov.nova.cosmic.vitalrecords.dto.CertificateRequestDTO;
import gov.nova.cosmic.vitalrecords.dto.CertificateRequestResponse;
import gov.nova.cosmic.vitalrecords.dto.CertificateResponse;
import gov.nova.cosmic.vitalrecords.entity.CertificateRequest;
import gov.nova.cosmic.vitalrecords.entity.RequestStatus;
import gov.nova.cosmic.vitalrecords.mapper.VitalRecordsMapper;
import gov.nova.cosmic.vitalrecords.messaging.VitalRecordsEventPublisher;
import gov.nova.cosmic.vitalrecords.repository.CertificateRepository;
import gov.nova.cosmic.vitalrecords.repository.CertificateRequestRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class VitalRecordsService {

    @Inject
    CertificateRequestRepository requestRepository;

    @Inject
    CertificateRepository certificateRepository;

    @Inject
    VitalRecordsMapper mapper;

    @Inject
    VitalRecordsEventPublisher eventPublisher;

    public CertificateRequestResponse submitRequest(CertificateRequestDTO request) {
        CertificateRequest entity = mapper.toEntity(request);
        requestRepository.persist(entity);
        eventPublisher.publishRequestCreated(entity);
        return mapper.toResponse(entity);
    }

    public CertificateRequestResponse getRequest(UUID id) {
        CertificateRequest entity = requestRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Certificate request not found: " + id);
        }
        return mapper.toResponse(entity);
    }

    public PagedResponse<CertificateRequestResponse> listRequests(int page, int size) {
        List<CertificateRequestResponse> content = requestRepository
                .findAll()
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(mapper::toResponse)
                .toList();
        long total = requestRepository.count();
        return new PagedResponse<>(content, page, size, total);
    }

    public CertificateRequestResponse updateStatus(UUID id, RequestStatus newStatus) {
        CertificateRequest entity = requestRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Certificate request not found: " + id);
        }
        entity.status = newStatus;
        entity.updatedAt = Instant.now();
        requestRepository.persist(entity);
        eventPublisher.publishStatusChanged(entity);
        return mapper.toResponse(entity);
    }

    public CertificateRequestResponse getByTrackingNumber(String trackingNumber) {
        CertificateRequest entity = requestRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new NotFoundException("Certificate request not found with tracking number: " + trackingNumber));
        return mapper.toResponse(entity);
    }

    public CertificateResponse getCertificate(String certificateNumber) {
        return certificateRepository.findByCertificateNumber(certificateNumber)
                .map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Certificate not found: " + certificateNumber));
    }
}
