package com.modsen.software.authservice.dto.request

data class UserRequest(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val credentials: List<Credential>,
    val role: String,
    val phone: String
)

data class Credential(
    val type: String? = "password",
    val value: String,
    val temporary: Boolean? = false)
