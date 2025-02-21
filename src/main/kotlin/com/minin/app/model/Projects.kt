package com.minin.app.model

import org.jetbrains.exposed.dao.id.LongIdTable

object Projects : LongIdTable() {
    val ownerId = reference("owner_id", Users)
    val workspaceId = reference("workspace_id", Workspaces)
    val title = varchar("title", 255)
}
