package com.minin.infrastructure

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.minin.infrastructure.extension.auth.validateByRoles
import com.minin.infrastructure.config.AppConfig
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(appConfig: AppConfig) {
    authentication {
        customAuth(appConfig, "user", "ADMIN", "USER")
        customAuth(appConfig, "admin", "ADMIN")
    }
}

fun AuthenticationConfig.customAuth(
    appConfig: AppConfig,
    name: String,
    vararg roles: String
) {
    jwt(name) {
        realm = appConfig.security.jwtRealm
        authHeader { call ->
            call.request.headers["Auth"]?.let {
                HttpAuthHeader.Single("Bearer", it.substringAfter("Bearer "))
            }
        }
        verifier(
            JWT
                .require(Algorithm.HMAC256(appConfig.security.jwtSecret))
                .withAudience(appConfig.security.jwtAudience)
                .withIssuer(appConfig.security.jwtDomain)
                .build()
        )
        validate { credential ->
            credential.validateByRoles(roles.toSet())
        }
    }
}

