package com.minin.infrastructure.extension.auth

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*

fun JWTCredential.validateByRoles(roles: Set<String>): JWTPrincipal? {
    val payload = this.payload
    val claims = payload.claims

    val role = claims["role"]?.asString()
    if (!roles.contains(role)) {
        return null
    }

    return JWTPrincipal(payload)
}

fun RoutingCall.getPrincipal(): CustomPrincipal {
    val claims = this.principal<JWTPrincipal>()!!.payload.claims
    return CustomPrincipal(
        id = claims["id"]!!.asLong(),
        email = claims["email"]!!.asString(),
        role = claims["role"]!!.asString()
    )
}

data class CustomPrincipal(
    val id: Long,
    val email: String,
    val role: String
)

object AuthConstants {
    const val USER = "user"
    const val ADMIN = "admin"
}
