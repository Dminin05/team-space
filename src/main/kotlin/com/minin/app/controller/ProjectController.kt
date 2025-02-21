package com.minin.app.controller

import com.minin.app.dto.workspace.ProjectCreateDto
import com.minin.app.service.ProjectService
import com.minin.infrastructure.config.Controller
import com.minin.infrastructure.extension.auth.AuthConstants
import com.minin.infrastructure.extension.auth.getPrincipal
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class ProjectController(
    private val projectService: ProjectService
) : Controller {
    override val setup: Routing.() -> Unit
        get() = {
            authenticate(AuthConstants.USER) {
                route("projects") {
                    post {
                        val principal = call.getPrincipal()
                        val dto = call.receive<ProjectCreateDto>().apply {
                            this.ownerId = principal.id
                        }
                        projectService.create(dto)
                        call.respond(HttpStatusCode.Created)
                    }
                }
            }
        }
}
