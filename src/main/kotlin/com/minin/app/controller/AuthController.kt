package com.minin.app.controller

import com.minin.app.dto.auth.LoginRequest
import com.minin.app.dto.auth.RegistrationRequest
import com.minin.app.service.impl.AuthService
import com.minin.infrastructure.config.Controller
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthController(
    private val authService: AuthService
) : Controller {

    override val setup: Routing.() -> Unit
        get() = {

            route("auth") {

                post("login") {
                    val loginRequest = call.receive<LoginRequest>()
                    val token = authService.login(loginRequest)
                    call.respond(token)
                }

                post("registration") {
                    val registrationRequest = call.receive<RegistrationRequest>()
                    val token = authService.register(registrationRequest)
                    call.respond(token)
                }

            }

        }
}
