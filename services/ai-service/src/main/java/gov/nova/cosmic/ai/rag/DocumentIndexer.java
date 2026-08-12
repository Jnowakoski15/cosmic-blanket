package gov.nova.cosmic.ai.rag;

import gov.nova.cosmic.ai.entity.IndexedDocument;
import gov.nova.cosmic.ai.llm.LlmProvider;
import gov.nova.cosmic.ai.vector.VectorStore;
import gov.nova.cosmic.common.event.DocumentChangedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DocumentIndexer {

    @Inject
    LlmProvider llmProvider;

    @Inject
    VectorStore vectorStore;

    @Inject
    DocumentChunker documentChunker;

    private static final int MAX_CHUNK_SIZE = 1000;

    @Transactional
    public void indexDocument(DocumentChangedEvent event) {
        if (event.getAction() == DocumentChangedEvent.Action.DELETED) {
            vectorStore.delete(event.getDocumentId().toString());
            IndexedDocument.delete("documentId", event.getDocumentId());
            return;
        }

        String content = event.getContent();
        if (content == null || content.isEmpty()) {
            return;
        }

        List<String> chunks = documentChunker.chunk(content, MAX_CHUNK_SIZE);

        for (int i = 0; i < chunks.size(); i++) {
            String chunkId = event.getDocumentId() + "_chunk_" + i;
            float[] embedding = llmProvider.embed(chunks.get(i));

            Map<String, String> metadata = new HashMap<>();
            metadata.put("source_service", event.getSourceService());
            metadata.put("document_type", event.getDocumentType());
            metadata.put("document_id", event.getDocumentId().toString());
            metadata.put("title", event.getTitle());
            metadata.put("chunk_index", String.valueOf(i));

            vectorStore.upsert(chunkId, embedding, metadata, chunks.get(i));
        }

        // Persist or update the IndexedDocument entity
        IndexedDocument existing = IndexedDocument.find(
                "sourceService = ?1 and documentId = ?2",
                event.getSourceService(), event.getDocumentId()
        ).firstResult();

        if (existing != null) {
            existing.title = event.getTitle();
            existing.chunkCount = chunks.size();
            existing.indexedAt = Instant.now();
        } else {
            IndexedDocument doc = new IndexedDocument();
            doc.sourceService = event.getSourceService();
            doc.documentType = event.getDocumentType();
            doc.documentId = event.getDocumentId();
            doc.title = event.getTitle();
            doc.chunkCount = chunks.size();
            doc.indexedAt = Instant.now();
            doc.persist();
        }
    }
}
