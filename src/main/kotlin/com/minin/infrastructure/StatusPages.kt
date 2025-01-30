package com.minin.infrastructure

import com.minin.infrastructure.exceptions.AuthenticationException
import com.minin.infrastructure.exceptions.BadRequestException
import com.minin.infrastructure.exceptions.NotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when(cause) {
                is NotFoundException -> call.respond(HttpStatusCode.NotFound, cause)
                is AuthenticationException -> call.respond(HttpStatusCode.Unauthorized, cause)
                is BadRequestException -> call.respond(HttpStatusCode.BadRequest, cause)
                is IllegalStateException -> call.respond(HttpStatusCode.BadRequest, cause)
                is ParameterConversionException -> call.respond(HttpStatusCode.BadRequest, cause)
                else -> call.respond(HttpStatusCode.InternalServerError, cause)
            }
        }
    }
}
