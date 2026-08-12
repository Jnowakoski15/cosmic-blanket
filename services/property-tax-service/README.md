# Property Tax Service

Read-only service for property records and tax assessments in the State of Nova.

**Port:** 8083

## API Endpoints

### Properties (`/api/property-tax/properties`)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/property-tax/properties` | List properties (paginated: `page`, `size`) |
| GET | `/api/property-tax/properties/{parcelNumber}` | Get property by parcel number |
| GET | `/api/property-tax/properties/search` | Search by `address` or `owner` (query params, case-insensitive) |

### Tax Records (`/api/property-tax/records`)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/property-tax/records/property/{parcelNumber}` | All tax records for a property |
| GET | `/api/property-tax/records/{id}` | Single tax record by UUID |

All endpoints are read-only (GET only).

## Domain Model

| Entity | Key Fields |
|--------|------------|
| `Property` | parcelNumber (unique), addressLine1, addressLine2, city, state (default "Nova"), zipCode, ownerName, assessedValue, landUseType |
| `TaxRecord` | propertyId, taxYear, assessedValue, taxRate, taxAmount, status, dueDate, paidDate |

**Land use types:** `RESIDENTIAL`, `COMMERCIAL`, `AGRICULTURAL`, `INDUSTRIAL`
**Tax statuses:** `BILLED`, `PAID`, `DELINQUENT`

## Database

- **Schema:** `property_tax`
- **Tables:** `properties`, `tax_records`
- **Migration:** `src/main/resources/db/migration/V1__create_property_tax_schema.sql`

## Kafka Topics

| Direction | Topic | Event |
|-----------|-------|-------|
| Publishes | `documents.changed` | `DocumentChangedEvent` when a property is registered or updated |
| Publishes | `services.notifications` | `ServiceNotificationEvent` on assessment changes |

## Running Locally

```bash
cd services/property-tax-service
mvn quarkus:dev
```

Dev Services auto-starts **PostgreSQL** and **Kafka** containers. No manual setup needed.

## Running Tests

```bash
# All tests
cd services/property-tax-service
mvn test

# Single test
mvn test -Dtest=PropertyResourceTest
```

Tests use Dev Services for PostgreSQL and `smallrye-in-memory` for Kafka (no real broker needed).

Existing tests cover: list properties, search properties, get property not found.

## Key Configuration

See `src/main/resources/application.properties` for database, Flyway, and Kafka settings.
