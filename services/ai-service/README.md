# AI Service

RAG-powered AI assistant and semantic search for the State of Nova. Consumes document change events from other services, indexes them into a vector store, and provides chat and search endpoints.

**Port:** 8084

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/ai/chat` | Chat with the AI assistant. Body: `{ conversationId, message }`. Returns `{ conversationId, response, citations }` |
| GET | `/api/ai/search` | Semantic search. Query params: `q` (query string), `limit` (default 10) |
| POST | `/api/ai/index` | Trigger manual re-indexing (stub — returns 202 Accepted) |

## Domain Model

| Entity | Key Fields |
|--------|------------|
| `Conversation` | userId, startedAt, updatedAt |
| `ChatMessage` | conversationId, role, content, createdAt |
| `IndexedDocument` | sourceService, documentType, documentId, title, chunkCount, indexedAt |

## RAG Pipeline

1. **Ingest** — `DocumentChangedConsumer` receives Kafka events from other services
2. **Chunk** — `DocumentChunker` splits text at sentence boundaries (1000 char max, 100 char overlap)
3. **Embed** — `LlmProvider.embed()` converts chunks to 1024-dim vectors
4. **Store** — `VectorStore.upsert()` saves chunks with metadata
5. **Query** — `RagEngine` embeds the user's question, searches the vector store (top 5), builds context, and sends to the LLM

## Current Limitations

The LLM provider and vector store are **stubs**:

- `ClaudeLlmProvider` returns placeholder responses and deterministic random embeddings
- `MilvusVectorStore` uses an in-memory `ConcurrentHashMap` instead of a real Milvus instance

These need to be replaced with real integrations before the AI features are production-ready.

## Database

- **Schema:** `ai`
- **Tables:** `conversations`, `messages`, `indexed_documents`
- **Migration:** `src/main/resources/db/migration/V1__create_ai_schema.sql`

## Kafka Topics

| Direction | Topic | Event |
|-----------|-------|-------|
| Consumes | `documents.changed` | `DocumentChangedEvent` — indexes new/updated documents, removes deleted ones |

## Running Locally

```bash
cd services/ai-service
mvn quarkus:dev
```

Dev Services auto-starts **PostgreSQL** and **Kafka** containers.

For real LLM integration, set the API key:

```bash
export ANTHROPIC_API_KEY=your-key-here
mvn quarkus:dev
```

### Configuration

| Property | Default | Description |
|----------|---------|-------------|
| `cosmic.ai.llm.provider` | `claude` | LLM provider |
| `cosmic.ai.llm.model` | `claude-sonnet-4-20250514` | Model name |
| `cosmic.ai.vector.host` | `localhost` | Milvus host |
| `cosmic.ai.vector.port` | `19530` | Milvus port |
| `cosmic.ai.vector.collection` | `state_documents` | Vector collection name |

## Running Tests

```bash
cd services/ai-service
mvn test
```

No tests yet. Tests would use Dev Services for PostgreSQL and `smallrye-in-memory` for Kafka.

## Key Configuration

See `src/main/resources/application.properties` for database, Flyway, Kafka, LLM, and vector store settings.
