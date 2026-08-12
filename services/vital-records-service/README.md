# Vital Records Service

Manages requests for birth and death certificates for the State of Nova.

**Port:** 8082

## API Endpoints

### Certificate Requests (`/api/vital-records/requests`)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/vital-records/requests` | Submit a certificate request |
| GET | `/api/vital-records/requests` | List requests (paginated: `page`, `size`) |
| GET | `/api/vital-records/requests/{id}` | Get request by UUID |
| PATCH | `/api/vital-records/requests/{id}/status` | Update request status |
| GET | `/api/vital-records/requests/tracking/{trackingNumber}` | Look up by tracking number |

### Certificates (`/api/vital-records/certificates`)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/vital-records/certificates/{certificateNumber}` | Get certificate by number |

## Domain Model

| Entity | Key Fields |
|--------|------------|
| `CertificateRequest` | requesterFirstName, requesterLastName, requesterEmail, certificateType, subjectFirstName, subjectLastName, subjectDateOfBirth, subjectDateOfDeath, status, trackingNumber |
| `Certificate` | requestId, certificateNumber (unique), certificateType, issuedDate |

**Certificate types:** `BIRTH_CERTIFICATE`, `DEATH_CERTIFICATE`
**Request statuses:** `SUBMITTED` → `PROCESSING` → `READY` / `MAILED` / `DENIED`
**Tracking numbers:** Generated as `VR-XXXXXXXX`

## Database

- **Schema:** `vital_records`
- **Tables:** `certificate_requests`, `certificates`
- **Migration:** `src/main/resources/db/migration/V1__create_vital_records_schema.sql`

## Kafka Topics

| Direction | Topic | Event |
|-----------|-------|-------|
| Publishes | `documents.changed` | `DocumentChangedEvent` when a request is created |
| Publishes | `services.notifications` | `ServiceNotificationEvent` when request status changes |

## Running Locally

```bash
cd services/vital-records-service
mvn quarkus:dev
```

Dev Services auto-starts **PostgreSQL** and **Kafka** containers. No manual setup needed.

## Running Tests

```bash
# All tests
cd services/vital-records-service
mvn test

# Single test
mvn test -Dtest=CertificateRequestResourceTest
```

Tests use Dev Services for PostgreSQL and `smallrye-in-memory` for Kafka (no real broker needed).

Existing tests cover: submit request, list requests, get request by ID.

## Key Configuration

See `src/main/resources/application.properties` for database, Flyway, and Kafka settings.
