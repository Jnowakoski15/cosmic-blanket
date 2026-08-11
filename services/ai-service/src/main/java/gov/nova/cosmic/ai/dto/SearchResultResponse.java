package gov.nova.cosmic.ai.dto;

public class SearchResultResponse {

    private String id;
    private float score;
    private String title;
    private String content;
    private String source;

    public SearchResultResponse() {}

    public SearchResultResponse(String id, float score, String title, String content, String source) {
        this.id = id;
        this.score = score;
        this.title = title;
        this.content = content;
        this.source = source;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public float getScore() { return score; }
    public void setScore(float score) { this.score = score; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
