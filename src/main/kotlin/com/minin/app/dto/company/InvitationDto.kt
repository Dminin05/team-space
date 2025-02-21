package com.minin.app.dto.company

import java.util.*

data class InvitationDto(
    val email: String,
    var companyId: Long? = null,
    var key: UUID? = null
)
