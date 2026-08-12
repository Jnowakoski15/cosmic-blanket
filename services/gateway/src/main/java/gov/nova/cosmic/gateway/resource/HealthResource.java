package gov.nova.cosmic.gateway.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/gateway")
public class HealthResource {

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public String status() {
        return "{\"service\":\"gateway\",\"status\":\"running\"}";
    }
}
