package gov.nova.cosmic.ai.vector;

import gov.nova.cosmic.ai.config.VectorStoreConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Replace in-memory storage with real Milvus integration using milvus-sdk-java
@ApplicationScoped
public class MilvusVectorStore implements VectorStore {

    @Inject
    VectorStoreConfig vectorStoreConfig;

    private final ConcurrentHashMap<String, VectorEntry> store = new ConcurrentHashMap<>();

    @Override
    public void upsert(String id, float[] vector, Map<String, String> metadata, String content) {
        store.put(id, new VectorEntry(id, vector, metadata, content));
    }

    @Override
    public List<SearchResult> search(float[] queryVector, int topK) {
        List<SearchResult> results = new ArrayList<>();
        for (VectorEntry entry : store.values()) {
            float score = cosineSimilarity(queryVector, entry.vector);
            results.add(new SearchResult(entry.id, score, entry.metadata, entry.content));
        }
        results.sort(Comparator.comparingDouble(SearchResult::getScore).reversed());
        return results.subList(0, Math.min(topK, results.size()));
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }

    private float cosineSimilarity(float[] a, float[] b) {
        if (a.length != b.length) {
            return 0;
        }
        float dot = 0;
        float normA = 0;
        float normB = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        if (normA == 0 || normB == 0) {
            return 0;
        }
        return dot / (float) (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private static class VectorEntry {
        final String id;
        final float[] vector;
        final Map<String, String> metadata;
        final String content;

        VectorEntry(String id, float[] vector, Map<String, String> metadata, String content) {
            this.id = id;
            this.vector = vector;
            this.metadata = metadata;
            this.content = content;
        }
    }
}
