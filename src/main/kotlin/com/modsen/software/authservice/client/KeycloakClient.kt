package com.modsen.software.authservice.client

import com.modsen.software.authservice.dto.request.IdAttributesUserRequest
import com.modsen.software.authservice.dto.request.RegisterRequest
import com.modsen.software.authservice.dto.response.RoleResponse
import com.modsen.software.authservice.dto.response.TokenResponse
import com.modsen.software.authservice.dto.response.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

@FeignClient(name = "keycloakClient", url = "\${keycloak.auth-server-url}")
interface KeycloakClient {


    @PostMapping("/realms/{realm}/protocol/openid-connect/token")
    fun getToken(
        @PathVariable realm: String,
        @RequestBody requestBody: MultiValueMap<String, String>
    ): TokenResponse


    @PostMapping("/admin/realms/{realm}/users")
    fun registerUser(
        @RequestHeader("Authorization") token: String,
        @PathVariable realm: String,
        @RequestBody registerRequest: RegisterRequest
    )

    @GetMapping("/admin/realms/{realm}/users")
    fun getUserByUsername(
        @RequestHeader("Authorization") token: String,
        @PathVariable realm: String,
        @RequestParam username: String
    ): List<UserResponse>

    @GetMapping("/admin/realms/{realm}/clients/{clientId}/roles/{role}")
    fun getRole(
        @RequestHeader("Authorization") token: String,
        @PathVariable realm: String,
        @PathVariable clientId: String,
        @PathVariable role: String
    ): RoleResponse

    @PostMapping("/admin/realms/{realm}/users/{userId}/role-mappings/clients/{clientId}")
    fun addRoleToUser(
        @RequestHeader("Authorization") token: String,
        @PathVariable realm: String,
        @PathVariable userId: String,
        @PathVariable clientId: String,
        @RequestBody roleMapping: List<Map<String, String>>
    )

    @DeleteMapping("/admin/realms/{realm}/users/{userId}/role-mappings/clients/{clientId}")
    fun deleteRoleFromUser(
        @RequestHeader("Authorization") token: String,
        @PathVariable realm: String,
        @PathVariable userId: String,
        @PathVariable clientId: String
    )

    @GetMapping("/admin/realms/{realm}/users/{userId}")
    fun getUserInfo(
        @RequestHeader("Authorization") token: String,
        @PathVariable realm: String,
        @PathVariable userId: String,
    ): UserResponse

    @PutMapping("/admin/realms/{realm}/users/{userId}")
    fun addIdToUser(
        @RequestHeader("Authorization") token: String,
        @PathVariable realm: String,
        @PathVariable userId: String,
        @RequestBody idAttributesUserRequest: IdAttributesUserRequest
    )
}
