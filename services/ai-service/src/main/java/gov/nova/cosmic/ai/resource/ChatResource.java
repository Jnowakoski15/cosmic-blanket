package gov.nova.cosmic.ai.resource;

import gov.nova.cosmic.ai.dto.ChatRequest;
import gov.nova.cosmic.ai.dto.ChatResponse;
import gov.nova.cosmic.ai.dto.Citation;
import gov.nova.cosmic.ai.entity.ChatMessage;
import gov.nova.cosmic.ai.entity.Conversation;
import gov.nova.cosmic.ai.rag.RagEngine;
import gov.nova.cosmic.ai.vector.SearchResult;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/api/ai/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatResource {

    @Inject
    RagEngine ragEngine;

    @POST
    @Transactional
    public ChatResponse chat(ChatRequest request) {
        Conversation conversation;

        if (request.getConversationId() != null && !request.getConversationId().isEmpty()) {
            conversation = Conversation.findById(UUID.fromString(request.getConversationId()));
            if (conversation == null) {
                conversation = createConversation();
            } else {
                conversation.updatedAt = Instant.now();
            }
        } else {
            conversation = createConversation();
        }

        // Persist user message
        ChatMessage userMessage = new ChatMessage();
        userMessage.conversationId = conversation.id;
        userMessage.role = "user";
        userMessage.content = request.getMessage();
        userMessage.createdAt = Instant.now();
        userMessage.persist();

        // Get answer from RAG engine
        String answer = ragEngine.answer(request.getMessage());

        // Persist assistant message
        ChatMessage assistantMessage = new ChatMessage();
        assistantMessage.conversationId = conversation.id;
        assistantMessage.role = "assistant";
        assistantMessage.content = answer;
        assistantMessage.createdAt = Instant.now();
        assistantMessage.persist();

        // Build citations from search results
        List<SearchResult> searchResults = ragEngine.search(request.getMessage(), 5);
        List<Citation> citations = new ArrayList<>();
        for (SearchResult result : searchResults) {
            citations.add(new Citation(
                    result.getMetadata().getOrDefault("title", "Unknown"),
                    result.getContent()
            ));
        }

        return new ChatResponse(conversation.id.toString(), answer, citations);
    }

    private Conversation createConversation() {
        Conversation conversation = new Conversation();
        conversation.startedAt = Instant.now();
        conversation.updatedAt = Instant.now();
        conversation.persist();
        return conversation;
    }
}
