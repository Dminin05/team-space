package com.minin.app.dto.workspace

data class WorkspaceCreateDto(
    var ownerId: Long? = null,
    var companyId: Long,
    val title: String
)
