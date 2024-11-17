package com.modsen.software.authservice.dto.response

import java.math.BigDecimal


data class PassengerResponse (
    val id: Long,
    val firstName: String,
    val secondName: String,
    val phone: String? = null,
    val email: String? = null,
    val money: BigDecimal
)
