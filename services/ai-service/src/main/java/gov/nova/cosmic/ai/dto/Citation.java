package gov.nova.cosmic.ai.dto;

public class Citation {

    private String source;
    private String content;

    public Citation() {}

    public Citation(String source, String content) {
        this.source = source;
        this.content = content;
    }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
