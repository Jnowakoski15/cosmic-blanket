package gov.nova.cosmic.ai.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nova.cosmic.ai.rag.DocumentIndexer;
import gov.nova.cosmic.common.event.DocumentChangedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DocumentChangedConsumer {

    private static final Logger LOG = Logger.getLogger(DocumentChangedConsumer.class);

    @Inject
    ObjectMapper objectMapper;

    @Inject
    DocumentIndexer documentIndexer;

    @Incoming("documents-changed")
    public void consume(String message) {
        try {
            DocumentChangedEvent event = objectMapper.readValue(message, DocumentChangedEvent.class);
            LOG.infof("Received document changed event: %s/%s action=%s",
                    event.getSourceService(), event.getDocumentId(), event.getAction());
            documentIndexer.indexDocument(event);
        } catch (Exception e) {
            LOG.errorf(e, "Failed to process document changed event: %s", message);
        }
    }
}
