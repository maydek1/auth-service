package com.modsen.software.authservice.controller

import com.modsen.software.authservice.dto.request.AddRoleRequest
import com.modsen.software.authservice.dto.request.RegisterRequest
import com.modsen.software.authservice.dto.request.UserRequest
import com.modsen.software.authservice.dto.response.RoleResponse
import com.modsen.software.authservice.dto.response.TokenResponse
import com.modsen.software.authservice.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String): ResponseEntity<TokenResponse> {
        val tokenResponse = authService.getToken(username, password)
        return ResponseEntity.ok(tokenResponse)
    }

    @PostMapping("/admin")
    fun admin(@RequestParam username: String, @RequestParam password: String): ResponseEntity<TokenResponse> {
        val tokenResponse = authService.adminToken(username, password)
        return ResponseEntity.ok(tokenResponse)
    }
    @PostMapping("/register")
    fun register(@RequestBody userRequest: UserRequest): ResponseEntity<RegisterRequest> {
        val userResponse = authService.register(userRequest)
        return ResponseEntity.ok(userResponse)
    }
    @GetMapping("/user")
    fun findUserId(@RequestParam username: String): ResponseEntity<String> {
        val userId = authService.getUserId(username)
        return ResponseEntity.ok(userId)
    }

    @GetMapping("/role")
    fun findRole(@RequestParam role: String): ResponseEntity<RoleResponse> {
        val roleResponse = authService.getRole(role)
        return ResponseEntity.ok(roleResponse)
    }

    @PostMapping("/user/role")
    fun changeRole(@RequestBody addRoleRequest: AddRoleRequest): ResponseEntity<HttpStatus> {
        authService.addRole(addRoleRequest)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("/user/role")
    fun deleteRole(@RequestBody addRoleRequest: AddRoleRequest): ResponseEntity<HttpStatus> {
        authService.deleteRole(addRoleRequest)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
