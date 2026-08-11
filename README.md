# Cosmic Blanket — State of Nova Government Portal

A modern US state government website for the fictional **State of Nova**, built as a Quarkus microservices platform deployed on OpenShift with Red Hat UBI 9 containers.

## Live Environment

| Component | URL |
|-----------|-----|
| Frontend | https://frontend-cosmic-blanket.apps.wz405duy.eastus.aroapp.io |
| Keycloak Admin | https://keycloak-cosmic-blanket.apps.wz405duy.eastus.aroapp.io |
| API (via frontend) | https://frontend-cosmic-blanket.apps.wz405duy.eastus.aroapp.io/api/ |

## Test Accounts

All accounts use the Keycloak `nova` realm. Log in via the **Login** button in the top-right of the frontend.

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| `citizen1` | `password` | Citizen | Jane Doe — can submit license applications, request vital records |
| `employee1` | `password` | Employee | John Smith — state employee, can update application statuses |
| `admin1` | `password` | Admin | Admin User — full administrative access |

**Keycloak Admin Console:** username `admin`, password `admin`

> GET requests are public. POST/PUT/PATCH/DELETE require authentication.

## Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│                        OpenShift Cluster                         │
│                                                                  │
│  ┌─────────┐    ┌──────────┐    ┌───────────────────────────┐   │
│  │ Frontend │───▶│ Gateway  │───▶│ Licensing Service    :8080│   │
│  │ (nginx)  │    │  :8080   │    │ Vital Records Service:8080│   │
│  │  :8080   │    │          │───▶│ Property Tax Service :8080│   │
│  └─────────┘    │          │    │ AI Service           :8080│   │
│       │          │          │───▶└───────────────────────────┘   │
│       │          └──────────┘              │                     │
│       │               │                   │                     │
│       │          ┌──────────┐    ┌────────────────┐             │
│       │          │ Keycloak │    │   PostgreSQL   │             │
│       │          │  (OIDC)  │    │ cosmic_blanket │             │
│       │          │  :8080   │    │    :5432       │             │
│       │          └──────────┘    └────────────────┘             │
│       │                                   │                     │
│       │                          ┌────────────────┐             │
│       └──────────────────────────│     Kafka      │             │
│                                  │  (Redpanda)    │             │
│                                  │   :29092       │             │
│                                  └────────────────┘             │
└──────────────────────────────────────────────────────────────────┘
```

### Request Flow

**Synchronous:** Browser → Frontend (nginx) → `/api/*` proxy → Gateway → Target Service → PostgreSQL

**Asynchronous:** Domain services publish events to Kafka topics (`documents.changed`, `services.notifications`), consumed by the AI service for document indexing.

### Services

| Service | Port | Schema | Description |
|---------|------|--------|-------------|
| **Gateway** | 8080 | — | API proxy, CORS, OIDC token validation, request logging |
| **Licensing** | 8080 | `licensing` | License applications and issued licenses |
| **Vital Records** | 8080 | `vital_records` | Birth/death/marriage certificate requests with tracking numbers |
| **Property Tax** | 8080 | `property_tax` | Property search and tax record lookup |
| **AI** | 8080 | `ai` | RAG chatbot, semantic search, document indexing via Kafka |
| **Frontend** | 8080 | — | React SPA served by nginx, proxies `/api` to gateway |

### API Endpoints

```
GET  /api/licensing/applications          List license applications
POST /api/licensing/applications          Submit new application (auth required)
GET  /api/licensing/applications/{id}     Get application details
PATCH /api/licensing/applications/{id}/status  Update status (auth required)
GET  /api/licensing/licenses              List issued licenses

GET  /api/vital-records/requests          List certificate requests
POST /api/vital-records/requests          Submit new request (auth required)
GET  /api/vital-records/requests/{id}     Get request details
GET  /api/vital-records/certificates      List certificates

GET  /api/property-tax/properties         Search properties
GET  /api/property-tax/properties/{id}    Get property details
GET  /api/property-tax/records            List tax records

POST /api/ai/chat                         Chat with AI assistant
GET  /api/ai/search?q=<query>             Semantic search
POST /api/ai/index                        Trigger document indexing (auth required)

GET  /api/gateway/status                  Gateway health check
```

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Runtime | Java 21 (Temurin) |
| Framework | Quarkus 3.17.2 |
| ORM | Hibernate ORM with Panache |
| Database | PostgreSQL 16 (schema-per-service isolation) |
| Migrations | Flyway |
| Messaging | Apache Kafka (Redpanda) via SmallRye Reactive Messaging |
| Auth | Keycloak 25 (OIDC/OAuth 2.0 + PKCE) |
| Vector DB | Milvus 2.4 (for AI semantic search) |
| Frontend | React 18 + Vite + TypeScript + React Router v7 + TanStack Query v5 |
| Containers | Red Hat UBI 9 (openjdk-21-runtime, nodejs-20, ubi-minimal) |
| Orchestration | OpenShift 4.20 (Azure Red Hat OpenShift) |
| Deployment | Helm umbrella chart with Knative-ready sub-charts |

## Local Development

### Prerequisites

- Java 21 and Maven 3.9+ (install via [SDKMAN](https://sdkman.io/))
- Node.js 20+
- Podman or Docker

### Quick Start

```bash
# Backend — Quarkus Dev Services auto-starts PostgreSQL, Kafka, and Keycloak
cd services/licensing-service && mvn quarkus:dev    # starts on :8081

# Frontend — Vite dev server proxies /api to :8080
cd frontend && npm install && npm run dev           # starts on :5173

# Full stack via containers
docker compose up -d
```

### Build Commands

```bash
source ~/.sdkman/bin/sdkman-init.sh
mvn clean compile              # compile all modules
mvn clean package -DskipTests  # build all JARs
mvn clean verify               # compile + run all tests

# Single test
mvn test -pl services/licensing-service -Dtest=LicenseApplicationResourceTest

# Frontend
cd frontend && npm run dev     # dev server on :5173
cd frontend && npm run build   # production build
cd frontend && npx tsc --noEmit  # type-check only
```

## Project Structure

```
cosmic-blanket/
├── pom.xml                          # Parent POM (Quarkus BOM, Java 21)
├── common/                          # Shared library (DTOs, exceptions, events)
│   └── src/main/java/gov/nova/cosmic/common/
│       ├── dto/                     # ApiError, PagedResponse
│       ├── event/                   # DocumentChangedEvent, ServiceNotificationEvent
│       ├── exception/               # ServiceException hierarchy
│       └── auth/                    # Roles constants
├── services/
│   ├── gateway/                     # API Gateway (port 8080)
│   │   └── src/main/
│   │       ├── java/.../gateway/
│   │       │   ├── client/          # REST client interfaces
│   │       │   ├── resource/        # Proxy resources
│   │       │   └── filter/          # Auth, logging filters
│   │       └── resources/
│   │           ├── application.properties
│   │           └── nova-realm.json  # Keycloak realm config
│   ├── licensing-service/           # License applications (port 8081)
│   ├── vital-records-service/       # Certificate requests (port 8082)
│   ├── property-tax-service/        # Property/tax records (port 8083)
│   └── ai-service/                  # RAG chatbot + search (port 8084)
│       └── src/main/java/.../ai/
│           ├── llm/                 # LlmProvider interface + Claude impl
│           ├── vector/              # VectorStore interface + Milvus impl
│           └── rag/                 # DocumentChunker, Indexer, RagEngine
├── frontend/                        # React SPA (Vite + TypeScript)
│   └── src/
│       ├── api/                     # Axios client + typed API modules
│       ├── auth/                    # OIDC AuthProvider (oidc-client-ts)
│       ├── components/              # Header, Footer, StatusBadge, etc.
│       ├── hooks/                   # TanStack Query hooks per domain
│       ├── pages/                   # Route pages (licensing, vital-records, etc.)
│       └── types/                   # TypeScript type definitions
├── deployment/
│   ├── containers/                  # UBI 9 Containerfiles
│   ├── helm/cosmic-blanket/         # Helm umbrella chart + sub-charts
│   └── openshift/                   # OpenShift deployment manifests
└── docker-compose.yml               # Full local stack
```

## Domain Service Pattern

Every domain service follows the same layered architecture:

```
Entity (JPA/Panache) → Repository → DTO → Mapper → Service → Resource
```

- **Entity**: Extends `PanacheEntityBase` with UUID `@Id`, `@Table(schema="<service_schema>")`
- **Repository**: `PanacheRepositoryBase` with custom finders
- **DTO**: Request (with Bean Validation) and Response records
- **Mapper**: `@ApplicationScoped`, manual field mapping
- **Service**: `@ApplicationScoped @Transactional`, business logic + event publishing
- **Resource**: `@Path` JAX-RS endpoint

## OpenShift Deployment

The application is deployed on Azure Red Hat OpenShift (ARO) 4.20 using OpenShift BuildConfigs with Docker strategy.

```bash
# Apply manifests in order
oc apply -f deployment/openshift/01-infrastructure.yaml
oc apply -f deployment/openshift/02-builds.yaml
oc apply -f deployment/openshift/03-services.yaml

# Start all builds
for bc in gateway licensing-service vital-records-service property-tax-service ai-service frontend; do
  oc start-build $bc
done
```

### Infrastructure Components

| Component | Image | Notes |
|-----------|-------|-------|
| PostgreSQL | `registry.redhat.io/rhel9/postgresql-16` | PVC-backed, single instance with schema isolation |
| Kafka | `quay.io/strimzi/kafka:latest-kafka-3.7.0` | KRaft mode (no ZooKeeper) |
| Keycloak | `quay.io/keycloak/keycloak:25.0` | PostgreSQL-backed, imports `nova` realm on startup |

## License

This project is a demonstration/educational platform for the fictional State of Nova.
