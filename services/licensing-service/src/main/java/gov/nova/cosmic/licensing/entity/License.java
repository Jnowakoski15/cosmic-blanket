package gov.nova.cosmic.licensing.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "licensing", name = "licenses")
public class License extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "application_id")
    public UUID applicationId;

    @Column(name = "license_number", unique = true)
    public String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "license_type")
    public LicenseType licenseType;

    @Column(name = "holder_name")
    public String holderName;

    @Column(name = "issued_date")
    public LocalDate issuedDate;

    @Column(name = "expiry_date")
    public LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public LicenseStatus status;
}
