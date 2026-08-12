# Common Module

Shared library consumed by all Cosmic Blanket services. Contains DTOs, Kafka event contracts, exceptions, and auth constants.

## Contents

### DTOs (`gov.nova.cosmic.common.dto`)

| Class | Purpose |
|-------|---------|
| `ApiError` | Standardized error response (timestamp, status, message, path, traceId) |
| `PagedResponse<T>` | Generic paginated response wrapper (content, page, size, totalElements, totalPages) |

### Kafka Events (`gov.nova.cosmic.common.event`)

| Class | Fields | Used By |
|-------|--------|---------|
| `DocumentChangedEvent` | sourceService, documentType, documentId, action (CREATED/UPDATED/DELETED), title, content, timestamp | Published by domain services, consumed by ai-service for RAG indexing |
| `ServiceNotificationEvent` | eventType, sourceService, entityId, recipientEmail, message, timestamp | Published by domain services on status changes |
| `CitizenActivityEvent` | action, serviceArea, timestamp | Citizen interaction tracking |

### Exceptions (`gov.nova.cosmic.common.exception`)

| Class | HTTP Status | Purpose |
|-------|-------------|---------|
| `ServiceException` | Configurable | Base exception with statusCode |
| `NotFoundException` | 404 | Entity not found |
| `ValidationException` | 422 | Input validation failure |

### Auth (`gov.nova.cosmic.common.auth`)

`Roles` defines constants: `CITIZEN`, `EMPLOYEE`, `ADMIN`.

## Usage

All services declare this as a dependency:

```xml
<dependency>
    <groupId>gov.nova.cosmic</groupId>
    <artifactId>cosmic-blanket-common</artifactId>
    <version>${project.version}</version>
</dependency>
```

Build the common module before running any service:

```bash
mvn clean install -pl common
```
