package gov.nova.cosmic.propertytax.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "property_tax", name = "properties")
public class Property extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @NotBlank
    @Column(name = "parcel_number", unique = true)
    public String parcelNumber;

    @NotBlank
    @Column(name = "address_line1")
    public String addressLine1;

    @Column(name = "address_line2")
    public String addressLine2;

    @NotBlank
    @Column(name = "city")
    public String city;

    @Column(name = "state")
    public String state = "Nova";

    @NotBlank
    @Column(name = "zip_code")
    public String zipCode;

    @NotBlank
    @Column(name = "owner_name")
    public String ownerName;

    @Column(name = "assessed_value")
    public BigDecimal assessedValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "land_use_type")
    public LandUseType landUseType;
}
