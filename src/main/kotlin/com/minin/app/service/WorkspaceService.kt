package com.minin.app.service

import com.minin.app.dto.workspace.WorkspaceCreateDto
import com.minin.app.repository.WorkspaceRepository

interface WorkspaceService {
    fun create(dto: WorkspaceCreateDto)
}

class WorkspaceServiceImpl(
    private val workspaceRepository: WorkspaceRepository
) : WorkspaceService {
    override fun create(dto: WorkspaceCreateDto) = workspaceRepository.create(dto)
}
