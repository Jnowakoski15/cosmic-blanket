package gov.nova.cosmic.propertytax.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "property_tax", name = "tax_records")
public class TaxRecord extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "property_id")
    public UUID propertyId;

    @Column(name = "tax_year")
    public int taxYear;

    @Column(name = "assessed_value")
    public BigDecimal assessedValue;

    @Column(name = "tax_rate")
    public BigDecimal taxRate;

    @Column(name = "tax_amount")
    public BigDecimal taxAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public TaxStatus status;

    @Column(name = "due_date")
    public LocalDate dueDate;

    @Column(name = "paid_date")
    public LocalDate paidDate;
}
