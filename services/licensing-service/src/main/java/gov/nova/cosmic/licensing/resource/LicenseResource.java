package gov.nova.cosmic.licensing.resource;

import gov.nova.cosmic.common.dto.PagedResponse;
import gov.nova.cosmic.licensing.dto.LicenseResponse;
import gov.nova.cosmic.licensing.service.LicensingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/licensing/licenses")
@Produces(MediaType.APPLICATION_JSON)
public class LicenseResource {

    @Inject
    LicensingService licensingService;

    @GET
    @Path("/{licenseNumber}")
    public LicenseResponse getLicense(@PathParam("licenseNumber") String licenseNumber) {
        return licensingService.getLicense(licenseNumber);
    }

    @GET
    public PagedResponse<LicenseResponse> listLicenses(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        return licensingService.listLicenses(page, size);
    }
}
