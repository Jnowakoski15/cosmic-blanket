package gov.nova.cosmic.vitalrecords.entity;

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
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "vital_records", name = "certificate_requests")
public class CertificateRequest extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @NotBlank
    @Column(name = "requester_first_name")
    public String requesterFirstName;

    @NotBlank
    @Column(name = "requester_last_name")
    public String requesterLastName;

    @NotBlank
    @Column(name = "requester_email")
    public String requesterEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "certificate_type")
    public CertificateType certificateType;

    @NotBlank
    @Column(name = "subject_first_name")
    public String subjectFirstName;

    @NotBlank
    @Column(name = "subject_last_name")
    public String subjectLastName;

    @Column(name = "subject_date_of_birth")
    public LocalDate subjectDateOfBirth;

    @Column(name = "subject_date_of_death")
    public LocalDate subjectDateOfDeath;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public RequestStatus status = RequestStatus.SUBMITTED;

    @Column(name = "tracking_number", unique = true)
    public String trackingNumber;

    @Column(name = "submitted_at")
    public Instant submittedAt;

    @Column(name = "updated_at")
    public Instant updatedAt;
}
