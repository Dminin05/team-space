package com.minin.app.controller

import com.minin.app.dto.auth.AcceptInvitationDto
import com.minin.app.dto.auth.EmailDto
import com.minin.app.dto.company.InvitationDto
import com.minin.app.service.AuthService
import com.minin.app.service.InvitationService
import com.minin.infrastructure.config.Controller
import com.minin.infrastructure.extension.auth.AuthConstants
import com.minin.infrastructure.extension.auth.getPrincipal
import com.minin.infrastructure.extension.getOrNull
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

class InvitationController(
    private val invitationService: InvitationService,
    private val authService: AuthService
) : Controller {
    override val setup: Routing.() -> Unit
        get() = {
            route("invitations") {
                authenticate(AuthConstants.USER) {
                    post {
                        val principal = call.getPrincipal()
                        val dto = call.receive<InvitationDto>()
                        invitationService.invite(principal.id, dto)
                        call.respond(HttpStatusCode.OK)
                    }
                }

                get("{key}") {
                    val key = call.parameters.getOrNull<UUID>("key")
                        ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val ans = invitationService.getByKey(key)
                    call.respond(ans)
                }

                post("accept") {
                    val dto = call.receive<AcceptInvitationDto>()
                    val ans = authService.registerByInvitation(dto)
                    call.respond(ans)
                }
            }
        }
}
