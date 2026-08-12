package gov.nova.cosmic.propertytax.repository;

import gov.nova.cosmic.propertytax.entity.Property;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PropertyRepository implements PanacheRepositoryBase<Property, UUID> {

    public Optional<Property> findByParcelNumber(String parcelNumber) {
        return find("parcelNumber", parcelNumber).firstResultOptional();
    }

    public List<Property> searchByAddress(String address) {
        return list("lower(addressLine1) like lower(?1)", "%" + address + "%");
    }

    public List<Property> searchByOwner(String owner) {
        return list("lower(ownerName) like lower(?1)", "%" + owner + "%");
    }
}
