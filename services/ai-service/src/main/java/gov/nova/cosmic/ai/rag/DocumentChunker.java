package gov.nova.cosmic.ai.rag;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DocumentChunker {

    private static final int DEFAULT_OVERLAP = 100;

    public List<String> chunk(String text, int maxChunkSize) {
        List<String> chunks = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return chunks;
        }

        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + maxChunkSize, text.length());

            if (end < text.length()) {
                // Look for a sentence boundary (period followed by space) before the end
                int sentenceEnd = text.lastIndexOf(". ", end);
                if (sentenceEnd > start) {
                    end = sentenceEnd + 2; // Include the period and space
                }
            }

            chunks.add(text.substring(start, end).trim());

            if (end >= text.length()) {
                break;
            }

            // Move start forward with overlap
            start = Math.max(start + 1, end - DEFAULT_OVERLAP);
        }

        return chunks;
    }
}
