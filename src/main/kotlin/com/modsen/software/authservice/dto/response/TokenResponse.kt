package com.modsen.software.authservice.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val expiresIn: Int,
    @JsonProperty("refresh_expires_in") val refreshExpiresIn: Int,
    @JsonProperty("refresh_token") val refreshToken: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("not_before_policy") val notBeforePolicy: Int,
    @JsonProperty("session_state") val sessionState: String,
    @JsonProperty("scope") val scope: String

)
