package com.minin.app.model

import org.jetbrains.exposed.dao.id.LongIdTable

object Invitations : LongIdTable() {
    val companyId = reference("company_id", Companies)
    val email = varchar("email", 255)
    val key = uuid("key")
}
