package com.modsen.software.authservice.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    @JsonProperty("lastName") val secondName: String
)
