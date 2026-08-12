package gov.nova.cosmic.licensing.repository;

import gov.nova.cosmic.licensing.entity.License;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class LicenseRepository implements PanacheRepositoryBase<License, UUID> {

    public Optional<License> findByLicenseNumber(String licenseNumber) {
        return find("licenseNumber", licenseNumber).firstResultOptional();
    }

    public List<License> findByHolderName(String holderName) {
        return list("holderName", holderName);
    }
}
