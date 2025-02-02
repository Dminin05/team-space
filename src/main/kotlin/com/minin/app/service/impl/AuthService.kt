package com.minin.app.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.minin.app.dto.auth.LoginRequest
import com.minin.app.dto.auth.RegistrationRequest
import com.minin.app.dto.auth.Token
import com.minin.app.model.User
import com.minin.app.repository.UserRepository
import com.minin.infrastructure.config.AppConfig
import com.minin.infrastructure.exceptions.AuthenticationException
import org.mindrot.jbcrypt.BCrypt
import java.util.*

interface AuthService {

    fun login(loginRequest: LoginRequest): Token

    fun register(registrationRequest: RegistrationRequest): Token
}


class AuthServiceImpl(
    private val appConfig: AppConfig,
    private val userRepository: UserRepository
) : AuthService {

    override fun login(loginRequest: LoginRequest): Token {
        val authenticationException = AuthenticationException("Wrong email or password")
        val user = userRepository.findByEmail(loginRequest.email)
            ?: throw authenticationException
        val isPasswordCorrect = BCrypt.checkpw(loginRequest.password, user.password)
        if (!isPasswordCorrect) {
            throw authenticationException
        }

        return createToken(user)
    }

    override fun register(registrationRequest: RegistrationRequest): Token {
        val isAlreadyExists = userRepository.findByEmail(registrationRequest.email) != null
        if (isAlreadyExists) {
            throw AuthenticationException("User with such email already exists")
        }

        val user = userRepository.save(registrationRequest)

        return createToken(user)
    }

    private fun createToken(user: User): Token {
        val value = JWT.create()
            .withAudience(appConfig.security.jwtAudience)
            .withIssuer(appConfig.security.jwtDomain)
            .withClaim("email", user.email)
            .withClaim("id", user.id)
            .withClaim("role", user.role.name)
            .withExpiresAt(Date(System.currentTimeMillis() + 43200000))
            .sign(Algorithm.HMAC256(appConfig.security.jwtSecret))

        return Token(value)
    }
}
