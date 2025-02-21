package com.minin.app.dto.workspace

data class ProjectCreateDto(
    var ownerId: Long? = null,
    val workspaceId: Long,
    val title: String
)
