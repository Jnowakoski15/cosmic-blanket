package gov.nova.cosmic.gateway.resource;

import gov.nova.cosmic.gateway.client.AiServiceClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/ai")
@Produces(MediaType.APPLICATION_JSON)
public class AiProxyResource {

    @Inject
    @RestClient
    AiServiceClient client;

    @POST
    @Path("/chat")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response chat(String body) {
        return client.chat(body);
    }

    @GET
    @Path("/search")
    public Response search(@QueryParam("q") String query,
                           @QueryParam("limit") @DefaultValue("10") int limit) {
        return client.search(query, limit);
    }

    @POST
    @Path("/index")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response index(String body) {
        return client.index(body);
    }
}
