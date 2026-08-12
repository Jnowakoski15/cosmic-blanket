package gov.nova.cosmic.ai.resource;

import gov.nova.cosmic.ai.dto.SearchResultResponse;
import gov.nova.cosmic.ai.rag.RagEngine;
import gov.nova.cosmic.ai.vector.SearchResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/ai/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    @Inject
    RagEngine ragEngine;

    @GET
    public List<SearchResultResponse> search(
            @QueryParam("q") String query,
            @QueryParam("limit") @DefaultValue("10") int limit) {

        List<SearchResult> results = ragEngine.search(query, limit);
        return results.stream()
                .map(r -> new SearchResultResponse(
                        r.getId(),
                        r.getScore(),
                        r.getMetadata().getOrDefault("title", ""),
                        r.getContent(),
                        r.getMetadata().getOrDefault("source_service", "")
                ))
                .collect(Collectors.toList());
    }
}
