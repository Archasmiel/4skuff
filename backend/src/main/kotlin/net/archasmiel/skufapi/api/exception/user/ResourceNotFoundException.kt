package net.archasmiel.skufapi.api.exception.user

import net.archasmiel.skufapi.api.exception.ApiException

class ResourceNotFoundException(
    resource: String,
    id: Any
) : ApiException(
    "RESOURCE_NOT_FOUND",
    "$resource with $id not found"
)