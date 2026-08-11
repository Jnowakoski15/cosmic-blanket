package gov.nova.cosmic.vitalrecords.resource;

import gov.nova.cosmic.common.dto.PagedResponse;
import gov.nova.cosmic.vitalrecords.dto.CertificateRequestDTO;
import gov.nova.cosmic.vitalrecords.dto.CertificateRequestResponse;
import gov.nova.cosmic.vitalrecords.dto.StatusUpdateRequest;
import gov.nova.cosmic.vitalrecords.service.VitalRecordsService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

@Path("/api/vital-records/requests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CertificateRequestResource {

    @Inject
    VitalRecordsService vitalRecordsService;

    @POST
    public Response submitRequest(@Valid CertificateRequestDTO request) {
        CertificateRequestResponse response = vitalRecordsService.submitRequest(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public CertificateRequestResponse getRequest(@PathParam("id") UUID id) {
        return vitalRecordsService.getRequest(id);
    }

    @GET
    public PagedResponse<CertificateRequestResponse> listRequests(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        return vitalRecordsService.listRequests(page, size);
    }

    @PATCH
    @Path("/{id}/status")
    public CertificateRequestResponse updateStatus(
            @PathParam("id") UUID id,
            StatusUpdateRequest request) {
        return vitalRecordsService.updateStatus(id, request.getStatus());
    }

    @GET
    @Path("/tracking/{trackingNumber}")
    public CertificateRequestResponse getByTrackingNumber(@PathParam("trackingNumber") String trackingNumber) {
        return vitalRecordsService.getByTrackingNumber(trackingNumber);
    }
}
