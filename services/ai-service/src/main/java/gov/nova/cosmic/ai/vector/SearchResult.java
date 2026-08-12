package gov.nova.cosmic.ai.vector;

import java.util.Map;

public class SearchResult {

    private String id;
    private float score;
    private Map<String, String> metadata;
    private String content;

    public SearchResult() {}

    public SearchResult(String id, float score, Map<String, String> metadata, String content) {
        this.id = id;
        this.score = score;
        this.metadata = metadata;
        this.content = content;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public float getScore() { return score; }
    public void setScore(float score) { this.score = score; }
    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
