package gov.nova.cosmic.gateway.resource;

import gov.nova.cosmic.gateway.client.VitalRecordsServiceClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/vital-records")
@Produces(MediaType.APPLICATION_JSON)
public class VitalRecordsProxyResource {

    @Inject
    @RestClient
    VitalRecordsServiceClient client;

    @GET
    @Path("/requests")
    public Response listRequests(@QueryParam("page") @DefaultValue("0") int page,
                                 @QueryParam("size") @DefaultValue("20") int size) {
        return client.listRequests(page, size);
    }

    @GET
    @Path("/requests/{id}")
    public Response getRequest(@PathParam("id") String id) {
        return client.getRequest(id);
    }

    @POST
    @Path("/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitRequest(String body) {
        return client.submitRequest(body);
    }

    @PATCH
    @Path("/requests/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRequestStatus(@PathParam("id") String id, String body) {
        return client.updateRequestStatus(id, body);
    }

    @GET
    @Path("/requests/tracking/{trackingNumber}")
    public Response getByTrackingNumber(@PathParam("trackingNumber") String trackingNumber) {
        return client.getByTrackingNumber(trackingNumber);
    }

    @GET
    @Path("/certificates/{certificateNumber}")
    public Response getCertificate(@PathParam("certificateNumber") String certificateNumber) {
        return client.getCertificate(certificateNumber);
    }
}
