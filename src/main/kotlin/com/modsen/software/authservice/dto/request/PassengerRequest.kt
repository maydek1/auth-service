package com.modsen.software.authservice.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class PassengerRequest(
    @get:JsonProperty("firstName") val firstName: String,
    @get:JsonProperty("secondName") val secondName: String? = null,
    @get:JsonProperty("phone") val phone: String,
    @get:JsonProperty("email") val email: String,
    @get:JsonProperty("money") val money: BigDecimal? = BigDecimal.ZERO
)