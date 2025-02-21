package com.minin.app.dto.auth

import java.util.UUID

data class AcceptInvitationDto(
    val key: UUID,
    val firstName: String,
    val lastName: String,
    val password: String
)
