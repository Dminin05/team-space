package com.minin.app.controller

import com.minin.app.dto.workspace.WorkspaceCreateDto
import com.minin.app.service.WorkspaceService
import com.minin.infrastructure.config.Controller
import com.minin.infrastructure.extension.auth.AuthConstants
import com.minin.infrastructure.extension.auth.getPrincipal
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class WorkspaceController(
    private val workspaceService: WorkspaceService
) : Controller {
    override val setup: Routing.() -> Unit
        get() = {
            authenticate(AuthConstants.USER) {
                route("workspaces") {
                    post {
                        val principal = call.getPrincipal()
                        val dto = call.receive<WorkspaceCreateDto>().apply {
                            this.ownerId = principal.id
                        }
                        workspaceService.create(dto)
                        call.respond(HttpStatusCode.Created)
                    }
                }
            }
        }
}
