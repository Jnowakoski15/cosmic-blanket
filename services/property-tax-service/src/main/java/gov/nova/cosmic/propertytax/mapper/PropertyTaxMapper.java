package gov.nova.cosmic.propertytax.mapper;

import gov.nova.cosmic.propertytax.dto.PropertyResponse;
import gov.nova.cosmic.propertytax.dto.TaxRecordResponse;
import gov.nova.cosmic.propertytax.entity.Property;
import gov.nova.cosmic.propertytax.entity.TaxRecord;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PropertyTaxMapper {

    public PropertyResponse toResponse(Property entity) {
        PropertyResponse response = new PropertyResponse();
        response.setId(entity.id);
        response.setParcelNumber(entity.parcelNumber);
        response.setAddressLine1(entity.addressLine1);
        response.setAddressLine2(entity.addressLine2);
        response.setCity(entity.city);
        response.setState(entity.state);
        response.setZipCode(entity.zipCode);
        response.setOwnerName(entity.ownerName);
        response.setAssessedValue(entity.assessedValue);
        response.setLandUseType(entity.landUseType);
        return response;
    }

    public TaxRecordResponse toResponse(TaxRecord entity) {
        TaxRecordResponse response = new TaxRecordResponse();
        response.setId(entity.id);
        response.setPropertyId(entity.propertyId);
        response.setTaxYear(entity.taxYear);
        response.setAssessedValue(entity.assessedValue);
        response.setTaxRate(entity.taxRate);
        response.setTaxAmount(entity.taxAmount);
        response.setStatus(entity.status);
        response.setDueDate(entity.dueDate);
        response.setPaidDate(entity.paidDate);
        return response;
    }
}
