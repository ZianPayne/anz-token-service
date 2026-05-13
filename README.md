# anz-token-service


## Spec

## Future Considerations

### Spec
Here are some more changes that I would consider adding to the spec with more information:
* Extend the spec to have proper wrappers, rather than the simple arrays used so far.
* Add more validation, currently no context on what consists of a valid payload (in terms of number of tokens etc) based on the documentation.

### As the library gets richer
* In a production system where account numbers were rich objects with issuer, currency, and metadata fields, I'd introduce MapStruct there to keep the domain model decoupled from the API contract.

### Nice to haves
* Proper exception handling
