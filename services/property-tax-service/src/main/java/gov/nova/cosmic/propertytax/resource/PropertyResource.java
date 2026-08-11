package gov.nova.cosmic.propertytax.resource;

import gov.nova.cosmic.common.dto.PagedResponse;
import gov.nova.cosmic.propertytax.dto.PropertyResponse;
import gov.nova.cosmic.propertytax.service.PropertyTaxService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/property-tax/properties")
@Produces(MediaType.APPLICATION_JSON)
public class PropertyResource {

    @Inject
    PropertyTaxService propertyTaxService;

    @GET
    @Path("/{parcelNumber}")
    public PropertyResponse getProperty(@PathParam("parcelNumber") String parcelNumber) {
        return propertyTaxService.getProperty(parcelNumber);
    }

    @GET
    @Path("/search")
    public List<PropertyResponse> searchProperties(
            @QueryParam("address") String address,
            @QueryParam("owner") String owner) {
        return propertyTaxService.searchProperties(address, owner);
    }

    @GET
    public PagedResponse<PropertyResponse> listProperties(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        return propertyTaxService.listProperties(page, size);
    }
}
