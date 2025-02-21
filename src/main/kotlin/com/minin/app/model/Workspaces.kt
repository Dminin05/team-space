package com.minin.app.model

import org.jetbrains.exposed.dao.id.LongIdTable

object Workspaces : LongIdTable() {
    val ownerId = reference("owner_id", Users)
    val companyId = reference("company_id", Companies)
    val title = varchar("title", 50)
}
