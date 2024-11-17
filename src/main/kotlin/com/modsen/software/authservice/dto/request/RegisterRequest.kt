package com.modsen.software.authservice.dto.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val credentials: List<Credential>
)