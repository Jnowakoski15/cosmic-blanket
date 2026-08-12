package gov.nova.cosmic.vitalrecords.repository;

import gov.nova.cosmic.vitalrecords.entity.Certificate;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CertificateRepository implements PanacheRepositoryBase<Certificate, UUID> {

    public Optional<Certificate> findByCertificateNumber(String certificateNumber) {
        return find("certificateNumber", certificateNumber).firstResultOptional();
    }
}
