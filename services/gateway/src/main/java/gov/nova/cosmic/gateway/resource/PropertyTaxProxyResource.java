package gov.nova.cosmic.gateway.resource;

import gov.nova.cosmic.gateway.client.PropertyTaxServiceClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/property-tax")
@Produces(MediaType.APPLICATION_JSON)
public class PropertyTaxProxyResource {

    @Inject
    @RestClient
    PropertyTaxServiceClient client;

    @GET
    @Path("/properties")
    public Response listProperties(@QueryParam("page") @DefaultValue("0") int page,
                                   @QueryParam("size") @DefaultValue("20") int size) {
        return client.listProperties(page, size);
    }

    @GET
    @Path("/properties/{parcelNumber}")
    public Response getProperty(@PathParam("parcelNumber") String parcelNumber) {
        return client.getProperty(parcelNumber);
    }

    @GET
    @Path("/properties/search")
    public Response searchProperties(@QueryParam("address") String address,
                                     @QueryParam("owner") String owner) {
        return client.searchProperties(address, owner);
    }

    @GET
    @Path("/records/property/{parcelNumber}")
    public Response getTaxRecords(@PathParam("parcelNumber") String parcelNumber) {
        return client.getTaxRecords(parcelNumber);
    }

    @GET
    @Path("/records/{id}")
    public Response getTaxRecord(@PathParam("id") String id) {
        return client.getTaxRecord(id);
    }
}
