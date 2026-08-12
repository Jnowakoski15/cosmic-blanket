package gov.nova.cosmic.ai.rag;

import gov.nova.cosmic.ai.llm.LlmProvider;
import gov.nova.cosmic.ai.vector.SearchResult;
import gov.nova.cosmic.ai.vector.VectorStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RagEngine {

    private static final int TOP_K = 5;

    @Inject
    LlmProvider llmProvider;

    @Inject
    VectorStore vectorStore;

    public String answer(String question) {
        float[] queryEmbedding = llmProvider.embed(question);
        List<SearchResult> results = vectorStore.search(queryEmbedding, TOP_K);

        String context = results.stream()
                .map(r -> "--- Source: " + r.getMetadata().getOrDefault("title", "Unknown") + " ---\n" + r.getContent())
                .collect(Collectors.joining("\n\n"));

        String systemPrompt = "You are a helpful AI assistant for the State of Nova government. "
                + "Answer questions based on the provided context. If the context doesn't contain "
                + "relevant information, say so honestly.\n\nContext:\n" + context;

        return llmProvider.complete(systemPrompt, List.of(question));
    }

    public List<SearchResult> search(String query, int limit) {
        float[] queryEmbedding = llmProvider.embed(query);
        return vectorStore.search(queryEmbedding, limit);
    }
}
