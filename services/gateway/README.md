# Gateway

API gateway and single entry point for the Cosmic Blanket platform. Proxies all frontend requests to downstream services via MicroProfile REST Client.

**Port:** 8080

## API Endpoints

All endpoints are prefixed by the target service. The gateway proxies transparently — request/response bodies pass through unchanged.

### Licensing (`/api/licensing`)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/licensing/applications` | List applications (paginated) |
| GET | `/api/licensing/applications/{id}` | Get application by ID |
| POST | `/api/licensing/applications` | Submit application |
| PATCH | `/api/licensing/applications/{id}/status` | Update application status |
| GET | `/api/licensing/licenses` | List licenses (paginated) |
| GET | `/api/licensing/licenses/{licenseNumber}` | Get license by number |

### Vital Records (`/api/vital-records`)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/vital-records/requests` | List requests (paginated) |
| GET | `/api/vital-records/requests/{id}` | Get request by ID |
| POST | `/api/vital-records/requests` | Submit request |
| PATCH | `/api/vital-records/requests/{id}/status` | Update request status |
| GET | `/api/vital-records/requests/tracking/{trackingNumber}` | Get by tracking number |
| GET | `/api/vital-records/certificates/{certificateNumber}` | Get certificate |

### Property Tax (`/api/property-tax`)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/property-tax/properties` | List properties (paginated) |
| GET | `/api/property-tax/properties/{parcelNumber}` | Get property by parcel number |
| GET | `/api/property-tax/properties/search` | Search by address or owner |
| GET | `/api/property-tax/records/property/{parcelNumber}` | Tax records for a property |
| GET | `/api/property-tax/records/{id}` | Get single tax record |

### AI (`/api/ai`)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/ai/chat` | Chat with AI assistant |
| GET | `/api/ai/search` | Semantic search (query params: `q`, `limit`) |
| POST | `/api/ai/index` | Trigger re-indexing |

### Health

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/gateway/status` | Gateway health status |

## Authentication

OIDC via Keycloak (realm: `nova`, client: `cosmic-gateway`).

- **GET** endpoints are public (no auth required)
- **POST/PUT/PATCH/DELETE** require a valid OIDC token

The `AuthenticationFilter` extracts the authenticated user and propagates `X-User-Id` and `X-User-Role` headers to downstream services. The `RequestLoggingFilter` generates an `X-Trace-Id` for each request.

## Running Locally

```bash
cd services/gateway
mvn quarkus:dev
```

Dev Services auto-starts **Keycloak** with a pre-configured `nova` realm (loaded from `src/main/resources/nova-realm.json`).

Downstream services must be running for proxied requests to succeed. Start them individually or use the root `docker compose up -d`.

CORS is configured to allow `http://localhost:5173` (the Vite frontend dev server).

## Running Tests

No tests yet.

## Key Configuration

See `src/main/resources/application.properties` for REST client URLs, OIDC settings, and auth policies.
