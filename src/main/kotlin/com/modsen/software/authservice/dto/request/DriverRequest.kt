package com.modsen.software.authservice.dto.request

data class DriverRequest(
    var firstName: String,
    val secondName: String,
    val phone: String,
    val email: String,
    val sex: String? = null,
    val carId: Long? = null
)
