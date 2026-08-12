# Licensing Service

Manages government license applications and issued licenses for the State of Nova (driver's licenses, business licenses).

**Port:** 8081

## API Endpoints

### License Applications (`/api/licensing/applications`)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/licensing/applications` | Submit a license application |
| GET | `/api/licensing/applications` | List applications (paginated: `page`, `size`) |
| GET | `/api/licensing/applications/{id}` | Get application by UUID |
| PATCH | `/api/licensing/applications/{id}/status` | Update application status |

### Licenses (`/api/licensing/licenses`)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/licensing/licenses` | List licenses (paginated) |
| GET | `/api/licensing/licenses/{licenseNumber}` | Get license by license number |

## Domain Model

| Entity | Key Fields |
|--------|------------|
| `LicenseApplication` | applicantFirstName, applicantLastName, applicantEmail, licenseType, status, submittedAt |
| `License` | applicationId, licenseNumber (unique), licenseType, holderName, issuedDate, expiryDate, status |

**License types:** `DRIVERS_LICENSE`, `BUSINESS_LICENSE`
**Application statuses:** `SUBMITTED` → `UNDER_REVIEW` → `APPROVED` / `DENIED`
**License statuses:** `ACTIVE`, `EXPIRED`, `REVOKED`, `SUSPENDED`

## Database

- **Schema:** `licensing`
- **Tables:** `license_applications`, `licenses`
- **Migration:** `src/main/resources/db/migration/V1__create_licensing_schema.sql`

## Kafka Topics

| Direction | Topic | Event |
|-----------|-------|-------|
| Publishes | `documents.changed` | `DocumentChangedEvent` when an application is created |
| Publishes | `services.notifications` | `ServiceNotificationEvent` when application status changes |

## Running Locally

```bash
cd services/licensing-service
mvn quarkus:dev
```

Dev Services auto-starts **PostgreSQL** and **Kafka** containers. No manual setup needed.

## Running Tests

```bash
# All tests
cd services/licensing-service
mvn test

# Single test
mvn test -Dtest=LicenseApplicationResourceTest
```

Tests use Dev Services for PostgreSQL and `smallrye-in-memory` for Kafka (no real broker needed).

Existing tests cover: submit application, list applications, get application by ID.

## Key Configuration

See `src/main/resources/application.properties` for database, Flyway, and Kafka settings.
