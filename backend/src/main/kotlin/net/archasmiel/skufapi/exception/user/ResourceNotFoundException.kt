package net.archasmiel.skufapi.exception.user

import net.archasmiel.skufapi.exception.ApiException

class ResourceNotFoundException(
    resource: String,
    id: Any
) : ApiException(
    "RESOURCE_NOT_FOUND",
    "$resource with $id not found"
)