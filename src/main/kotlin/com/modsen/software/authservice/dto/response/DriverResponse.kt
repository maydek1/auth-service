package com.modsen.software.authservice.dto.response


data class DriverResponse (
    val id: Long,
    val firstName: String,
    val secondName: String,
    val phone: String,
    val email: String,
    val sex: String,
    val carId: Long,
    val rate: Int,
    val ratingCount: Int,
    val available: Boolean
)

