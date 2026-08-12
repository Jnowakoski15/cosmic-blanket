package gov.nova.cosmic.ai.dto;

import java.util.List;

public class ChatResponse {

    private String conversationId;
    private String response;
    private List<Citation> citations;

    public ChatResponse() {}

    public ChatResponse(String conversationId, String response, List<Citation> citations) {
        this.conversationId = conversationId;
        this.response = response;
        this.citations = citations;
    }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    public List<Citation> getCitations() { return citations; }
    public void setCitations(List<Citation> citations) { this.citations = citations; }
}
