package com.minin.app.repository

import com.minin.app.dto.workspace.WorkspaceCreateDto
import com.minin.app.model.Companies
import com.minin.app.model.Users
import com.minin.app.model.Workspaces
import com.minin.infrastructure.extension.db.dbQuery
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insert

interface WorkspaceRepository {
    fun create(dto: WorkspaceCreateDto)
}

class WorkspaceRepositoryImpl : WorkspaceRepository {
    override fun create(dto: WorkspaceCreateDto): Unit = dbQuery {
        Workspaces.insert {
            it[ownerId] = EntityID(dto.ownerId!!, Users)
            it[companyId] = EntityID(dto.companyId, Companies)
            it[title] = dto.title
        }
    }
}
