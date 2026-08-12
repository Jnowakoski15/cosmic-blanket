package gov.nova.cosmic.propertytax.resource;

import gov.nova.cosmic.propertytax.dto.TaxRecordResponse;
import gov.nova.cosmic.propertytax.service.PropertyTaxService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/api/property-tax/records")
@Produces(MediaType.APPLICATION_JSON)
public class TaxRecordResource {

    @Inject
    PropertyTaxService propertyTaxService;

    @GET
    @Path("/property/{parcelNumber}")
    public List<TaxRecordResponse> getTaxRecords(@PathParam("parcelNumber") String parcelNumber) {
        return propertyTaxService.getTaxRecords(parcelNumber);
    }

    @GET
    @Path("/{id}")
    public TaxRecordResponse getTaxRecord(@PathParam("id") UUID id) {
        return propertyTaxService.getTaxRecord(id);
    }
}
