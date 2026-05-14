# anz-token-service


## Spec

### Validation
- Request validation is schema-driven (OpenAPI) at the REST boundary. Domain models keep core invariants in check. This gets handled by an ExceptionHandler, with different failure modes to help distinguish those consuming the API.
- Bulk requests are all-or-nothing.

### Local Run
- Build the runnable JAR: `./gradlew bootJar` (outputs `build/libs/anz-token-service.jar`).
- Run it: `java -jar build/libs/anz-token-service.jar`.
- Try requests via VS Code REST Client in [requests/tokenization.http](requests/tokenization.http).

## Future Considerations

### Spec
Here are some more changes that I would consider adding to the spec with more information:
* Extend the spec to have proper wrappers, rather than the simple arrays used so far.
* Add more validation, currently no context on what consists of a valid payload (in terms of number of tokens etc) based on the documentation.

### As the library gets richer
* In a production system where account numbers were rich objects with issuer, currency, and metadata fields, I'd introduce MapStruct there to keep the domain model decoupled from the API contract.

### Nice to haves
* Proper exception handling, right now very simple error modes, rest layer, domain and persistence. Can perhaps make them more descriptive, add error models to handle multiple errors (admittedly the task is on the simpler side for this).
* We kept validation to a bulk level. In future we could have per-item error reporting as a future extension. However, I felt that this would be behaviour that would need to be clarified in business requirements rather than something than done ad-hoc.

