# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Cosmic Blanket is a modern US state government website for the fictional "State of Nova." It's a Quarkus microservices platform deployed serverless on OpenShift using Red Hat UBI 9 containers, with AI-powered citizen chatbot and semantic search.

## Build & Run Commands

### Full build (all modules)
```bash
source ~/.sdkman/bin/sdkman-init.sh
mvn clean compile              # compile all modules
mvn clean package -DskipTests  # build all JARs
mvn clean verify               # compile + run all tests
```

### Single service development
```bash
cd services/licensing-service && mvn quarkus:dev   # starts on :8081 with Dev Services
cd services/gateway && mvn quarkus:dev             # starts on :8080
```
Quarkus Dev Services auto-starts PostgreSQL, Kafka, and Keycloak containers — no manual setup needed.

### Frontend development
```bash
cd frontend && npm run dev     # Vite dev server on :5173, proxies /api to :8080
cd frontend && npm run build   # production build
cd frontend && npx tsc --noEmit  # type-check only
```

### Run a single test
```bash
mvn test -pl services/licensing-service -Dtest=LicenseApplicationResourceTest
```

### Docker Compose (full stack)
```bash
docker compose up -d           # all services + infrastructure
docker compose down
```

## Architecture

**Monorepo with Maven multi-module structure.** Root POM (`gov.nova.cosmic:cosmic-blanket-parent`) manages dependency versions via Quarkus BOM import.

### Module layout
- `common/` — Shared library (DTOs, exceptions, event classes). Plain JAR, not a Quarkus app.
- `services/gateway/` — API Gateway (port 8080). Proxies all requests to downstream services via MicroProfile REST Client. Single entry point for the React SPA.
- `services/licensing-service/` — License applications and licenses (port 8081, schema `licensing`)
- `services/vital-records-service/` — Certificate requests and certificates (port 8082, schema `vital_records`)
- `services/property-tax-service/` — Property and tax records (port 8083, schema `property_tax`)
- `services/ai-service/` — RAG chatbot + semantic search (port 8084, schema `ai`)
- `frontend/` — React SPA (Vite + TypeScript + React Router + TanStack Query)

### Communication patterns
- **Synchronous**: React SPA → Gateway → Target Service → PostgreSQL
- **Asynchronous**: Domain services publish to Kafka topics (`documents.changed`, `services.notifications`), consumed by ai-service for indexing

### Database
Single PostgreSQL instance with schema-per-service isolation. Each service manages its own schema via Flyway migrations in `src/main/resources/db/migration/`. Services never query another service's schema directly.

### Key conventions
- **Entity pattern**: JPA entities extend `PanacheEntityBase` with UUID `@Id @GeneratedValue`. Public fields. `@Table(schema="<service_schema>")`.
- **Architecture per service**: Entity → Repository (PanacheRepositoryBase) → DTO → Mapper (@ApplicationScoped, manual) → Service (@ApplicationScoped @Transactional) → Resource (@Path)
- **Error handling**: `ServiceException` hierarchy in common module. Each service has a `@Provider ExceptionMapper` returning `ApiError` DTO.
- **REST client config**: Gateway uses `@RegisterRestClient(configKey = "...")` with `quarkus.rest-client.<key>.url` in properties.
- **Kafka events**: Published via SmallRye `@Channel` Emitter in `*EventPublisher` classes. Consumed via `@Incoming` in ai-service.

### Container images
All based on `registry.access.redhat.com/ubi9/openjdk-21-runtime:latest`. Multi-stage builds defined in `deployment/containers/`. Frontend uses UBI 9 + Nginx.

### Deployment
Helm umbrella chart at `deployment/helm/cosmic-blanket/` with sub-charts per service. Backend services deploy as Knative Services (serverless). Frontend as standard Deployment.
