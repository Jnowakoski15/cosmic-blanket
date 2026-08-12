package gov.nova.cosmic.gateway.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "ai-service")
@Path("/api/ai")
public interface AiServiceClient {

    @POST
    @Path("/chat")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response chat(String body);

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    Response search(@QueryParam("q") String query, @QueryParam("limit") @DefaultValue("10") int limit);

    @POST
    @Path("/index")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response index(String body);
}
