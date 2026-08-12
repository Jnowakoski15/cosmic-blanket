package gov.nova.cosmic.propertytax.repository;

import gov.nova.cosmic.propertytax.entity.TaxRecord;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TaxRecordRepository implements PanacheRepositoryBase<TaxRecord, UUID> {

    public List<TaxRecord> findByPropertyId(UUID propertyId) {
        return list("propertyId", propertyId);
    }
}
