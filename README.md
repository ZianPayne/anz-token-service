# anz-token-service

This is a simple submission for the ANZ-Software-Engineer Assignment for Z'Arn Payne.

## Run Locally
- Build the runnable JAR: `./gradlew bootJar` (outputs `build/libs/anz-token-service.jar`).
- Run it: `java -jar build/libs/anz-token-service.jar`.
- Try requests via VS Code REST Client in [requests/tokenize_and_detokenize.http](requests/tokenize_and_detokenize.http) and [requests/errors.http](requests/errors.http).

## Architecture
- API-first DTOs generated from OpenAPI; controllers implement generated interfaces.
- Hexagonal layout: adapters for REST and persistence, application service for orchestration, domain model for invariants.
- Persistence uses H2 (in-memory) through a JPA adapter and repository.

## Spec
- Endpoints: `POST /tokenize`, `POST /detokenize`.
- Payloads: `AccountNumbers`, `Tokens`.
- Error model: `ErrorResponse` for 400/404/500.

## Validation
- Request validation is schema-driven (OpenAPI) at the REST boundary.
- Domain models keep core invariants in check.
- Bulk requests are all-or-nothing.

## Logging
- Controller logs high-level request/response counts, no PII.
- Service logs are `DEBUG` for flow and `WARN` for missing tokens.
- Persistence adapter logs lookups and saves at `DEBUG`.

## Error Modes
- 400 for invalid payloads (validation or malformed JSON).
- 404 for missing tokens on detokenize.
- 500 for data access failures.

## Future Considerations

### As the library gets richer
* In a production system where account numbers were rich objects with issuer, currency, and metadata fields, I'd introduce MapStruct there to keep the domain model decoupled from the API contract.

### Nice to haves
* Error responses could include per-item failure details for bulk requests.
* Consider REST guidance such as HATEOAS if the API grows beyond the current scope.

