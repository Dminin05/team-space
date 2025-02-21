package com.minin.app.repository

import com.minin.app.dto.workspace.ProjectCreateDto
import com.minin.app.model.Projects
import com.minin.app.model.Users
import com.minin.app.model.Workspaces
import com.minin.infrastructure.extension.db.dbQuery
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insert

interface ProjectRepository {
    fun create(dto: ProjectCreateDto)
}

class ProjectRepositoryImpl : ProjectRepository {
    override fun create(dto: ProjectCreateDto): Unit = dbQuery {
        Projects.insert {
            it[ownerId] = EntityID(dto.ownerId!!, Users)
            it[workspaceId] = EntityID(dto.workspaceId, Workspaces)
            it[title] = dto.title
        }
    }
}
