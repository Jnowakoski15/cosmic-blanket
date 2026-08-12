package gov.nova.cosmic.gateway.resource;

import gov.nova.cosmic.gateway.client.LicensingServiceClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/licensing")
@Produces(MediaType.APPLICATION_JSON)
public class LicensingProxyResource {

    @Inject
    @RestClient
    LicensingServiceClient client;

    @GET
    @Path("/applications")
    public Response listApplications(@QueryParam("page") @DefaultValue("0") int page,
                                     @QueryParam("size") @DefaultValue("20") int size) {
        return client.listApplications(page, size);
    }

    @GET
    @Path("/applications/{id}")
    public Response getApplication(@PathParam("id") String id) {
        return client.getApplication(id);
    }

    @POST
    @Path("/applications")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitApplication(String body) {
        return client.submitApplication(body);
    }

    @PATCH
    @Path("/applications/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateApplicationStatus(@PathParam("id") String id, String body) {
        return client.updateApplicationStatus(id, body);
    }

    @GET
    @Path("/licenses")
    public Response listLicenses(@QueryParam("page") @DefaultValue("0") int page,
                                 @QueryParam("size") @DefaultValue("20") int size) {
        return client.listLicenses(page, size);
    }

    @GET
    @Path("/licenses/{licenseNumber}")
    public Response getLicense(@PathParam("licenseNumber") String licenseNumber) {
        return client.getLicense(licenseNumber);
    }
}
