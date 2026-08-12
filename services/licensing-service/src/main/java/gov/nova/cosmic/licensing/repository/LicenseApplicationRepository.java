package gov.nova.cosmic.licensing.repository;

import gov.nova.cosmic.licensing.entity.ApplicationStatus;
import gov.nova.cosmic.licensing.entity.LicenseApplication;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LicenseApplicationRepository implements PanacheRepositoryBase<LicenseApplication, UUID> {

    public List<LicenseApplication> findByEmail(String email) {
        return list("applicantEmail", email);
    }

    public List<LicenseApplication> findByStatus(ApplicationStatus status) {
        return list("status", status);
    }
}
