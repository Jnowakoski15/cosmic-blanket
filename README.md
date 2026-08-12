# Cosmic Blanket — State of Nova Government Portal

A modern US state government website for the fictional **State of Nova**, built as a Quarkus microservices platform deployed on OpenShift with Red Hat UBI 9 containers.

## Table of Contents

- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Build Commands](#build-commands)
- [Project Structure](#project-structure)
- [Domain Service Pattern](#domain-service-pattern)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

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

| Service | Dev Port | Schema | Description |
|---------|----------|--------|-------------|
| Gateway | 8080 | — | API proxy, CORS, OIDC token validation, request logging |
| Licensing | 8081 | `licensing` | License applications and issued licenses |
| Vital Records | 8082 | `vital_records` | Birth/death certificate requests with tracking numbers |
| Property Tax | 8083 | `property_tax` | Property search and tax record lookup |
| AI | 8084 | `ai` | RAG chatbot, semantic search, document indexing via Kafka |
| Frontend | 5173 | — | React SPA served by Vite (dev) or nginx (prod) |

Each service has its own README with full endpoint documentation. See `services/<name>/README.md`.

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
| Orchestration | OpenShift 4.x (tested on Azure Red Hat OpenShift) |
| GitOps | Red Hat GitOps (ArgoCD) with Kustomize |

## Getting Started

### Prerequisites

- Java 21 and Maven 3.9+ (install via [SDKMAN](https://sdkman.io/))
- Node.js 20+
- Podman or Docker (for Quarkus Dev Services)

### Quick Start

Quarkus Dev Services automatically starts PostgreSQL, Kafka, and Keycloak containers — no manual infrastructure setup needed.

```bash
# Clone the repo
git clone https://github.com/your-org/cosmic-blanket.git
cd cosmic-blanket

# Start a backend service (Dev Services handles the rest)
cd services/licensing-service && mvn quarkus:dev    # starts on :8081

# In another terminal — start the frontend
cd frontend && npm install && npm run dev           # starts on :5173

# Or run the full stack via containers
docker compose up -d
```

### Test Accounts

When running locally with Dev Services, Keycloak is pre-configured with a `nova` realm and these test accounts:

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| `citizen1` | `password` | Citizen | Can submit license applications, request vital records |
| `employee1` | `password` | Employee | State employee, can update application statuses |
| `admin1` | `password` | Admin | Full administrative access |

**Keycloak Admin Console** (local only): username `admin`, password `admin`

> GET requests are public. POST/PUT/PATCH/DELETE require authentication.

## Build Commands

```bash
source ~/.sdkman/bin/sdkman-init.sh
mvn clean compile              # compile all modules
mvn clean package -DskipTests  # build all JARs
mvn clean verify               # compile + run all tests

# Single test
mvn test -pl services/licensing-service -Dtest=LicenseApplicationResourceTest

# Frontend
cd frontend && npm run dev       # dev server on :5173
cd frontend && npm run build     # production build
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
│   ├── licensing-service/           # License applications (port 8081)
│   ├── vital-records-service/       # Certificate requests (port 8082)
│   ├── property-tax-service/        # Property/tax records (port 8083)
│   └── ai-service/                  # RAG chatbot + search (port 8084)
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
│   ├── gitops/                      # ArgoCD apps + Kustomize overlays
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

## Deployment

### Docker Compose (local)

```bash
docker compose up -d       # all services + infrastructure
docker compose down
```

### OpenShift

The application deploys on OpenShift using either GitOps (ArgoCD) or raw manifests.

**GitOps (recommended):**

```bash
oc new-project cosmic-blanket
oc label namespace cosmic-blanket argocd.argoproj.io/managed-by=openshift-gitops
oc apply -f deployment/gitops/argocd/appproject.yaml -n openshift-gitops
oc apply -f deployment/gitops/argocd/root-app.yaml -n openshift-gitops
```

**Manual:**

```bash
oc apply -f deployment/openshift/01-infrastructure.yaml
oc apply -f deployment/openshift/02-builds.yaml
oc apply -f deployment/openshift/03-services.yaml

for bc in gateway licensing-service vital-records-service property-tax-service ai-service frontend; do
  oc start-build $bc
done
```

Configure your cluster domain in the Kustomize overlays before deploying. See `deployment/gitops/services/overlays/dev/`.

### Infrastructure Components

| Component | Image | Notes |
|-----------|-------|-------|
| PostgreSQL | `registry.redhat.io/rhel9/postgresql-16` | PVC-backed, single instance with schema isolation |
| Kafka | `quay.io/strimzi/kafka:latest-kafka-3.7.0` | KRaft mode (no ZooKeeper) |
| Keycloak | `quay.io/keycloak/keycloak:25.0` | PostgreSQL-backed, imports `nova` realm on startup |

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Make your changes and ensure tests pass (`mvn clean verify`)
4. Commit with a descriptive message
5. Push to your fork and open a Pull Request

Please keep PRs focused — one feature or fix per PR.

## License

This project is licensed under the [Apache License 2.0](LICENSE).
