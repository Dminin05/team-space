package com.minin.app.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Companies : LongIdTable() {
    val title = varchar("title", 255)
}

class CompanyDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CompanyDao>(Companies)
    var title by Companies.title

    fun toSerializable() = CompanyEntity(id.value, title)
}

data class CompanyEntity(
    val id: Long,
    val title: String
)
