package gov.nova.cosmic.gateway.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "property-tax-service")
@Path("/api/property-tax")
public interface PropertyTaxServiceClient {

    @GET
    @Path("/properties")
    @Produces(MediaType.APPLICATION_JSON)
    Response listProperties(@QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("size") @DefaultValue("20") int size);

    @GET
    @Path("/properties/{parcelNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getProperty(@PathParam("parcelNumber") String parcelNumber);

    @GET
    @Path("/properties/search")
    @Produces(MediaType.APPLICATION_JSON)
    Response searchProperties(@QueryParam("address") String address,
                              @QueryParam("owner") String owner);

    @GET
    @Path("/records/property/{parcelNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTaxRecords(@PathParam("parcelNumber") String parcelNumber);

    @GET
    @Path("/records/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTaxRecord(@PathParam("id") String id);
}
