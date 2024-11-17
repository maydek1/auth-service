package com.modsen.software.authservice.dto.request

data class IdAttributesUserRequest(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val attributes: Map<String, List<Long>>
)
