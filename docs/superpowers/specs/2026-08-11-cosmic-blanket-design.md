# Cosmic Blanket — Modern State Government Web Platform

## Overview

A modern, citizen-facing US state government website for a fictional state ("State of Nova"), built as a microservices platform on Quarkus. Deployed serverless on OpenShift using Red Hat UBI containers. Features AI-powered chatbot and semantic search.

## Architecture: Monorepo Microservices

All services live in a single Git repository with a shared Maven parent POM. Each service is independently deployable as a UBI-based container image running serverless on OpenShift via Knative.

### Repository Structure

```
cosmic-blanket/
├── pom.xml                          # Parent POM (BOM, shared dependency versions)
├── common/                          # Shared library module
│   └── (DTOs, error models, auth utilities, OpenAPI annotations)
├── services/
│   ├── gateway/                     # API Gateway — Quarkus Reactive Routes
│   ├── licensing-service/           # Driver's license, business license
│   ├── vital-records-service/       # Birth/death certificates
│   ├── property-tax-service/        # Property lookup, tax records
│   └── ai-service/                  # Chatbot, RAG pipeline, semantic search
├── frontend/                        # React SPA (Vite + React + TypeScript)
├── deployment/
│   ├── helm/                        # Helm charts for OpenShift deployment
│   └── containers/                  # Containerfiles (UBI-based)
└── docs/
```

### Services

| Service | Purpose | Port | Database Schema |
|---------|---------|------|-----------------|
| `gateway` | API routing, rate limiting, CORS, JWT validation | 8080 | none |
| `licensing-service` | License applications, renewals, lookups | 8081 | `licensing` |
| `vital-records-service` | Certificate requests, status tracking | 8082 | `vital_records` |
| `property-tax-service` | Property search, tax record lookup | 8083 | `property_tax` |
| `ai-service` | Chatbot (RAG), semantic search, document indexing | 8084 | `ai` |

## Service Architecture

Each Quarkus service uses:
- **RESTEasy Reactive** — Jakarta REST endpoints
- **Hibernate ORM with Panache** — Active Record pattern for DB access
- **SmallRye OpenAPI** — Auto-generated API documentation
- **SmallRye Health** — Readiness/liveness probes for OpenShift
- **SmallRye Reactive Messaging** — Kafka producer/consumer
- **MicroProfile Config** — Externalized configuration
- **Quarkus OIDC** — JWT token validation via Keycloak

## Communication Patterns

### Synchronous (REST)
```
React SPA → Gateway → Target Service → PostgreSQL
```
The frontend talks exclusively to the gateway. The gateway routes requests to the appropriate service based on path prefix. Services do not call each other synchronously.

### Asynchronous (Kafka via SmallRye Reactive Messaging)
```
Service → Kafka Topic → Consuming Service
```

**Kafka topics:**
- `documents.changed` — Published by licensing, vital-records, property-tax when records change. Consumed by ai-service to re-index content for semantic search.
- `services.notifications` — Cross-service notification events (e.g., `LicenseApproved`, `RecordReady`).
- `citizen.activity` — Anonymized usage events for future analytics.

**Infrastructure:** Strimzi operator-managed Kafka cluster on OpenShift (Red Hat AMQ Streams).

## AI Service Design

### RAG Pipeline
1. **Ingest**: Consume `documents.changed` events from Kafka
2. **Chunk**: Split documents into semantic chunks
3. **Embed**: Generate embeddings via an embedding model
4. **Store**: Write vectors to dedicated vector database
5. **Query**: User question → embed → vector similarity search → retrieve relevant chunks → augment LLM prompt → return answer with citations

### Endpoints
- `POST /api/ai/chat` — Chatbot conversation (streaming response)
- `GET /api/ai/search?q=...` — Semantic search across all state content
- `POST /api/ai/index` — Manual document indexing trigger

### External Dependencies
- **LLM provider**: Configurable (Claude API, OpenAI, or local model via Ollama). Abstracted behind an interface for swappability.
- **Vector database**: Dedicated instance (Elasticsearch with vector search, Milvus, or Weaviate — chosen at deployment time). Connected via a repository abstraction.
- **Embedding model**: Configurable; can use the LLM provider's embedding API or a local model.

## Database Strategy

**PostgreSQL** (single instance, schema-per-service isolation):
- Each service owns its schema and manages migrations via Flyway
- Services never query another service's schema directly
- Cross-service data access is via REST or Kafka events

**Vector database** (separate instance):
- Stores document embeddings for semantic search
- Owned exclusively by `ai-service`
- Default: Milvus. Abstracted behind a repository interface so alternatives can be swapped in.

## Authentication

**Keycloak** (or Red Hat SSO) via Quarkus OIDC:
- Citizens authenticate through Keycloak's login page (authorization code flow)
- Gateway validates JWT tokens on every request
- Services receive validated identity via forwarded headers
- Role-based access: `citizen`, `employee`, `admin`

## Frontend Architecture

**Stack**: Vite + React + TypeScript

**Key libraries:**
- React Router — client-side routing
- TanStack Query — server state management and API caching
- OpenAPI-generated TypeScript clients — type-safe API calls

**Routes:**
| Path | Page |
|------|------|
| `/` | Landing page — service cards, search bar |
| `/licensing` | License lookup, application, renewal |
| `/vital-records` | Certificate requests, status tracking |
| `/property-tax` | Property search, tax record lookup |
| `/chat` | AI chatbot interface |
| `/search` | Semantic search results |

## Containerization

**Base images**: Red Hat UBI 9 minimal + OpenJDK 21 runtime.

```dockerfile
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:latest
```

- Each Quarkus service builds as a fast-jar and copies into the UBI runtime image
- Frontend: UBI 9 + Nginx serving static build output
- Multi-stage builds to keep images small

## Deployment

**OpenShift + Knative** for serverless:
- Each Quarkus service deploys as a Knative `Service` (auto-scales, scales to zero)
- Frontend deploys as a standard `Deployment` (static content, no scale-to-zero needed)
- PostgreSQL via Crunchy Postgres Operator or StatefulSet
- Kafka via Strimzi operator (Red Hat AMQ Streams)
- Keycloak via Keycloak Operator

**Helm charts** package all manifests:
- `cosmic-blanket` umbrella chart with sub-charts per service
- Configurable values for image tags, replicas, resource limits, external URLs
- Environment-specific values files (dev, staging, prod)

## Dev Experience

- **Quarkus Dev Services**: Auto-starts PostgreSQL, Kafka, and Keycloak containers during `mvn quarkus:dev`
- **Hot reload**: Quarkus live coding for backend, Vite HMR for frontend
- **Single command**: `mvn quarkus:dev` from any service directory
- **Frontend dev**: `npm run dev` in `frontend/` with proxy to gateway

## Testing Strategy

- **Unit tests**: JUnit 5 + Mockito for service logic
- **Integration tests**: `@QuarkusTest` with Testcontainers (PostgreSQL, Kafka)
- **API tests**: RestAssured for endpoint verification
- **Frontend unit tests**: Vitest
- **E2E tests**: Playwright
- **Contract testing**: OpenAPI specs as the contract between frontend and backend
