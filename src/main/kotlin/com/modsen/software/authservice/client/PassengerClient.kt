package com.modsen.software.authservice.client

import com.modsen.software.authservice.config.FeignConfig
import com.modsen.software.authservice.dto.request.PassengerRequest
import com.modsen.software.authservice.dto.response.PassengerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    path= "\${service.passenger-service.path}",
    name = "\${service.passenger-service.name}",
    configuration = [FeignConfig::class]
)
interface PassengerClient {
    @PostMapping
    fun createPassenger(@RequestBody passengerRequest: PassengerRequest): ResponseEntity<PassengerResponse>
}

