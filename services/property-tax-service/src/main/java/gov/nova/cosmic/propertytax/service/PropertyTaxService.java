package gov.nova.cosmic.propertytax.service;

import gov.nova.cosmic.common.dto.PagedResponse;
import gov.nova.cosmic.common.exception.NotFoundException;
import gov.nova.cosmic.propertytax.dto.PropertyResponse;
import gov.nova.cosmic.propertytax.dto.TaxRecordResponse;
import gov.nova.cosmic.propertytax.entity.Property;
import gov.nova.cosmic.propertytax.mapper.PropertyTaxMapper;
import gov.nova.cosmic.propertytax.messaging.PropertyTaxEventPublisher;
import gov.nova.cosmic.propertytax.repository.PropertyRepository;
import gov.nova.cosmic.propertytax.repository.TaxRecordRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PropertyTaxService {

    @Inject
    PropertyRepository propertyRepository;

    @Inject
    TaxRecordRepository taxRecordRepository;

    @Inject
    PropertyTaxMapper mapper;

    @Inject
    PropertyTaxEventPublisher eventPublisher;

    public PropertyResponse getProperty(String parcelNumber) {
        Property entity = propertyRepository.findByParcelNumber(parcelNumber)
                .orElseThrow(() -> new NotFoundException("Property not found with parcel number: " + parcelNumber));
        return mapper.toResponse(entity);
    }

    public List<PropertyResponse> searchProperties(String address, String owner) {
        if (address != null && !address.isBlank()) {
            return propertyRepository.searchByAddress(address).stream()
                    .map(mapper::toResponse)
                    .toList();
        }
        if (owner != null && !owner.isBlank()) {
            return propertyRepository.searchByOwner(owner).stream()
                    .map(mapper::toResponse)
                    .toList();
        }
        return List.of();
    }

    public PagedResponse<PropertyResponse> listProperties(int page, int size) {
        List<PropertyResponse> content = propertyRepository
                .findAll()
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(mapper::toResponse)
                .toList();
        long total = propertyRepository.count();
        return new PagedResponse<>(content, page, size, total);
    }

    public List<TaxRecordResponse> getTaxRecords(String parcelNumber) {
        Property property = propertyRepository.findByParcelNumber(parcelNumber)
                .orElseThrow(() -> new NotFoundException("Property not found with parcel number: " + parcelNumber));
        return taxRecordRepository.findByPropertyId(property.id).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TaxRecordResponse getTaxRecord(UUID id) {
        return taxRecordRepository.findByIdOptional(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Tax record not found: " + id));
    }
}
