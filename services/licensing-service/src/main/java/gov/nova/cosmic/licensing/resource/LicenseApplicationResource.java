package gov.nova.cosmic.licensing.resource;

import gov.nova.cosmic.common.dto.PagedResponse;
import gov.nova.cosmic.licensing.dto.LicenseApplicationRequest;
import gov.nova.cosmic.licensing.dto.LicenseApplicationResponse;
import gov.nova.cosmic.licensing.dto.StatusUpdateRequest;
import gov.nova.cosmic.licensing.service.LicensingService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

@Path("/api/licensing/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LicenseApplicationResource {

    @Inject
    LicensingService licensingService;

    @POST
    public Response submitApplication(@Valid LicenseApplicationRequest request) {
        LicenseApplicationResponse response = licensingService.submitApplication(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public LicenseApplicationResponse getApplication(@PathParam("id") UUID id) {
        return licensingService.getApplication(id);
    }

    @GET
    public PagedResponse<LicenseApplicationResponse> listApplications(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        return licensingService.listApplications(page, size);
    }

    @PATCH
    @Path("/{id}/status")
    public LicenseApplicationResponse updateStatus(
            @PathParam("id") UUID id,
            StatusUpdateRequest request) {
        return licensingService.updateStatus(id, request.getStatus());
    }
}
