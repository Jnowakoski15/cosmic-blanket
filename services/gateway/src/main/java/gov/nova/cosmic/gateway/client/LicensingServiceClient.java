package gov.nova.cosmic.gateway.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "licensing-service")
@Path("/api/licensing")
public interface LicensingServiceClient {

    @GET
    @Path("/applications")
    @Produces(MediaType.APPLICATION_JSON)
    Response listApplications(@QueryParam("page") @DefaultValue("0") int page,
                              @QueryParam("size") @DefaultValue("20") int size);

    @GET
    @Path("/applications/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getApplication(@PathParam("id") String id);

    @POST
    @Path("/applications")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response submitApplication(String body);

    @PATCH
    @Path("/applications/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateApplicationStatus(@PathParam("id") String id, String body);

    @GET
    @Path("/licenses")
    @Produces(MediaType.APPLICATION_JSON)
    Response listLicenses(@QueryParam("page") @DefaultValue("0") int page,
                          @QueryParam("size") @DefaultValue("20") int size);

    @GET
    @Path("/licenses/{licenseNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getLicense(@PathParam("licenseNumber") String licenseNumber);
}
