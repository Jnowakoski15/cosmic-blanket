package gov.nova.cosmic.vitalrecords.repository;

import gov.nova.cosmic.vitalrecords.entity.CertificateRequest;
import gov.nova.cosmic.vitalrecords.entity.RequestStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CertificateRequestRepository implements PanacheRepositoryBase<CertificateRequest, UUID> {

    public Optional<CertificateRequest> findByTrackingNumber(String trackingNumber) {
        return find("trackingNumber", trackingNumber).firstResultOptional();
    }

    public List<CertificateRequest> findByStatus(RequestStatus status) {
        return list("status", status);
    }
}
