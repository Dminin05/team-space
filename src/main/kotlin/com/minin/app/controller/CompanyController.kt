package com.minin.app.controller

import com.minin.app.dto.company.CompanyCreateDto
import com.minin.app.service.CompanyService
import com.minin.infrastructure.config.Controller
import com.minin.infrastructure.extension.auth.AuthConstants
import com.minin.infrastructure.extension.auth.getPrincipal
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class CompanyController(
    private val companyService: CompanyService
) : Controller {
    override val setup: Routing.() -> Unit
        get() = {
            authenticate(AuthConstants.USER) {
                route("companies") {
                    post {
                        val principal = call.getPrincipal()
                        val dto = call.receive<CompanyCreateDto>()
                        companyService.create(principal.id, dto.title)
                        call.respond(HttpStatusCode.Created)
                    }
                }
            }
        }
}
