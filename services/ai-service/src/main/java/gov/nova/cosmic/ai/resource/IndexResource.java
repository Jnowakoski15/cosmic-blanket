package gov.nova.cosmic.ai.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/ai/index")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IndexResource {

    @POST
    public Response triggerReindex() {
        // TODO: Implement manual re-index trigger
        return Response.accepted()
                .entity("{\"status\":\"re-index triggered\"}")
                .build();
    }
}
