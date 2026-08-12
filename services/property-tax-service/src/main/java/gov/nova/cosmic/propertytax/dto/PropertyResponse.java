package gov.nova.cosmic.propertytax.dto;

import gov.nova.cosmic.propertytax.entity.LandUseType;
import java.math.BigDecimal;
import java.util.UUID;

public class PropertyResponse {

    private UUID id;
    private String parcelNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String ownerName;
    private BigDecimal assessedValue;
    private LandUseType landUseType;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getParcelNumber() { return parcelNumber; }
    public void setParcelNumber(String parcelNumber) { this.parcelNumber = parcelNumber; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public BigDecimal getAssessedValue() { return assessedValue; }
    public void setAssessedValue(BigDecimal assessedValue) { this.assessedValue = assessedValue; }
    public LandUseType getLandUseType() { return landUseType; }
    public void setLandUseType(LandUseType landUseType) { this.landUseType = landUseType; }
}
