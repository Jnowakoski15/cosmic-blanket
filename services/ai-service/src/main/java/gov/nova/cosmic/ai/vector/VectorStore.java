package gov.nova.cosmic.ai.vector;

import java.util.List;
import java.util.Map;

public interface VectorStore {

    void upsert(String id, float[] vector, Map<String, String> metadata, String content);

    List<SearchResult> search(float[] queryVector, int topK);

    void delete(String id);
}
