package gov.nova.cosmic.licensing.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "licensing", name = "license_applications")
public class LicenseApplication extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @NotBlank
    @Column(name = "applicant_first_name")
    public String applicantFirstName;

    @NotBlank
    @Column(name = "applicant_last_name")
    public String applicantLastName;

    @NotBlank
    @Column(name = "applicant_email")
    public String applicantEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "license_type")
    public LicenseType licenseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public ApplicationStatus status = ApplicationStatus.SUBMITTED;

    @Column(name = "submitted_at")
    public Instant submittedAt;

    @Column(name = "updated_at")
    public Instant updatedAt;

    @Column(name = "notes")
    public String notes;
}
