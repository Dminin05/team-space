package com.minin.app.service

import com.minin.app.dto.workspace.ProjectCreateDto
import com.minin.app.repository.ProjectRepository

interface ProjectService {
    fun create(dto: ProjectCreateDto)
}

class ProjectServiceImpl(
    private val projectRepository: ProjectRepository
) : ProjectService {
    override fun create(dto: ProjectCreateDto) = projectRepository.create(dto)
}
