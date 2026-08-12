package gov.nova.cosmic.vitalrecords.resource;

import gov.nova.cosmic.vitalrecords.dto.CertificateResponse;
import gov.nova.cosmic.vitalrecords.service.VitalRecordsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/vital-records/certificates")
@Produces(MediaType.APPLICATION_JSON)
public class CertificateResource {

    @Inject
    VitalRecordsService vitalRecordsService;

    @GET
    @Path("/{certificateNumber}")
    public CertificateResponse getCertificate(@PathParam("certificateNumber") String certificateNumber) {
        return vitalRecordsService.getCertificate(certificateNumber);
    }
}
