package com.modsen.software.authservice.client

import com.modsen.software.authservice.config.FeignConfig
import com.modsen.software.authservice.dto.request.DriverRequest
import com.modsen.software.authservice.dto.response.DriverResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    path= "\${service.driver-service.path}",
    name = "\${service.driver-service.name}",
    configuration = [FeignConfig::class]
)
interface DriverClient {
    @PostMapping
    fun createDriver(@RequestBody driverRequest: DriverRequest): ResponseEntity<DriverResponse>
}

