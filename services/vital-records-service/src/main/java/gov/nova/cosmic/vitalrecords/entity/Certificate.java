package gov.nova.cosmic.vitalrecords.entity;

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
@Table(schema = "vital_records", name = "certificates")
public class Certificate extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "request_id")
    public UUID requestId;

    @Column(name = "certificate_number", unique = true)
    public String certificateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "certificate_type")
    public CertificateType certificateType;

    @Column(name = "issued_date")
    public LocalDate issuedDate;
}
