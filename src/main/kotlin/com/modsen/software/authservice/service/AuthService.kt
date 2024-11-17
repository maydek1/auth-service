package com.modsen.software.authservice.service

import com.modsen.software.authservice.client.DriverClient
import com.modsen.software.authservice.client.KeycloakClient
import com.modsen.software.authservice.client.PassengerClient
import com.modsen.software.authservice.dto.request.*
import com.modsen.software.authservice.dto.response.RoleResponse
import com.modsen.software.authservice.dto.response.TokenResponse
import com.modsen.software.authservice.exception.UserAlreadyExistsException
import feign.FeignException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@Service
class AuthService(
    private val keycloakClient: KeycloakClient,
    private val passengerClient: PassengerClient,
    private val driverClient: DriverClient
) {

    @Value("\${keycloak.realm}")
    lateinit var realm: String

    @Value("\${keycloak.clientId}")
    lateinit var clientId: String

    @Value("\${keycloak.resource}")
    lateinit var clientName: String

    @Value("\${keycloak.credentials.secret}")
    lateinit var clientSecret: String

    fun getToken(username: String, password: String): TokenResponse {
        val requestBody: MultiValueMap<String, String> = LinkedMultiValueMap()
        requestBody.add("grant_type", "password")
        requestBody.add("client_id", clientName)
        requestBody.add("client_secret", clientSecret)
        requestBody.add("username", username)
        requestBody.add("password", password)

        return keycloakClient.getToken(realm, requestBody)
    }

    fun register(userRequest: UserRequest): RegisterRequest {
        val adminToken = getAdminToken()
        val registrationRequest = RegisterRequest(
            username = userRequest.username,
            email = userRequest.email,
            firstName = userRequest.firstName,
            lastName = userRequest.lastName,
            enabled = userRequest.enabled,
            credentials = userRequest.credentials
        )
        try {
            keycloakClient.registerUser("Bearer $adminToken", realm, registrationRequest)
        } catch (ex: FeignException.Conflict) {
            throw UserAlreadyExistsException("Пользователь с таким email уже существует.")
        }
        var userId: Long? = null
        when (userRequest.role) {
            "DRIVER" -> {
                addRole(AddRoleRequest(userRequest.username, "DRIVER"))
                userId = createDriver(userRequest)
            }

            "PASSENGER" -> {
                addRole(AddRoleRequest(userRequest.username, "PASSENGER"))
                userId = createPassenger(userRequest)
            }

            "ADMIN" -> addRole(AddRoleRequest(userRequest.username, "ADMIN"))
            else -> {
                addRole(AddRoleRequest(userRequest.username, "PASSENGER"))
                userId = createPassenger(userRequest)
            }
        }
        if (userId != null) {
            addAttribute(userRequest, userId)
        }
        return registrationRequest
    }

    fun getUserId(username: String): String {
        val adminToken = getAdminToken()
        val users = keycloakClient.getUserByUsername("Bearer $adminToken", realm, username)
        return users.firstOrNull()?.id ?: throw IllegalArgumentException("Пользователь не найден")
    }

    fun getRole(role: String): RoleResponse {
        val adminToken = getAdminToken()
        return keycloakClient.getRole("Bearer $adminToken", realm, clientId, role)
    }

    fun addRole(roleRequest: AddRoleRequest) {
        val adminToken = getAdminToken()
        val userId = getUserId(roleRequest.username)
        val role = getRole(roleRequest.role)
        val roleMapping = listOf(mapOf("id" to role.id, "name" to roleRequest.role))

        keycloakClient.addRoleToUser("Bearer $adminToken", realm, userId, clientId, roleMapping)
    }

    fun deleteRole(roleRequest: AddRoleRequest) {
        val adminToken = getAdminToken()
        val userId = getUserId(roleRequest.username)
        keycloakClient.deleteRoleFromUser("Bearer $adminToken", realm, userId, clientId)
    }


    fun getAdminToken(): String {
        val requestBody: MultiValueMap<String, String> = LinkedMultiValueMap()
        requestBody.add("grant_type", "password")
        requestBody.add("client_id", "admin-cli")
        requestBody.add("username", "maydek")
        requestBody.add("password", "maydek")

        return keycloakClient.getToken(realm, requestBody).accessToken
    }

    fun adminToken(username: String, password: String): TokenResponse {
        val requestBody: MultiValueMap<String, String> = LinkedMultiValueMap()
        requestBody.add("grant_type", "password")
        requestBody.add("client_id", "admin-cli")
        requestBody.add("username", username)
        requestBody.add("password", password)
        return keycloakClient.getToken(realm, requestBody)
    }

    fun createPassenger(userRequest: UserRequest): Long {
        val passengerRequest = PassengerRequest(
            email = userRequest.email,
            firstName = userRequest.firstName,
            secondName = userRequest.lastName,
            phone = userRequest.phone

        )
        val passengerResponse = passengerClient.createPassenger(passengerRequest)
        return passengerResponse.body!!.id
    }

    fun createDriver(userRequest: UserRequest): Long {
        val driverRequest = DriverRequest(
            email = userRequest.email,
            firstName = userRequest.firstName,
            secondName = userRequest.lastName,
            phone = userRequest.phone

        )
        val driverResponse = driverClient.createDriver(driverRequest)
        return driverResponse.body!!.id
    }

    fun addAttribute(userRequest: UserRequest, userId: Long) {
        val adminToken = getAdminToken()
        val id = getUserId(userRequest.username)
        val userInfo = keycloakClient.getUserInfo("Bearer $adminToken", realm, id)
        val updateUserRequest = IdAttributesUserRequest(
            id = id,
            username = userInfo.username,
            email = userInfo.email,
            firstName = userInfo.firstName,
            lastName = userInfo.secondName,
            enabled = true,
            attributes = mapOf(
                "userId" to listOf(userId)
            )
        )

        val userIdInfo = keycloakClient.addIdToUser("Bearer $adminToken", realm, id, updateUserRequest)
        println("User with id : $userIdInfo")
    }

}
