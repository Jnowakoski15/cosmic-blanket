package gov.nova.cosmic.gateway.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "vital-records-service")
@Path("/api/vital-records")
public interface VitalRecordsServiceClient {

    @GET
    @Path("/requests")
    @Produces(MediaType.APPLICATION_JSON)
    Response listRequests(@QueryParam("page") @DefaultValue("0") int page,
                          @QueryParam("size") @DefaultValue("20") int size);

    @GET
    @Path("/requests/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getRequest(@PathParam("id") String id);

    @POST
    @Path("/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response submitRequest(String body);

    @PATCH
    @Path("/requests/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateRequestStatus(@PathParam("id") String id, String body);

    @GET
    @Path("/requests/tracking/{trackingNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getByTrackingNumber(@PathParam("trackingNumber") String trackingNumber);

    @GET
    @Path("/certificates/{certificateNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCertificate(@PathParam("certificateNumber") String certificateNumber);
}
