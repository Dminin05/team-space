package com.minin.app.repository

import com.minin.app.model.Companies
import com.minin.app.model.Users
import com.minin.infrastructure.exceptions.BadRequestException
import com.minin.infrastructure.extension.db.dbQuery
import com.minin.infrastructure.extension.db.map
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

interface CompanyRepository {
    fun create(title: String): Long

    fun findCompanyIdByUserId(userId: Long): Long
}

class CompanyRepositoryImpl : CompanyRepository {
    override fun create(title: String)  = dbQuery {
        Companies.insert {
            it[Companies.title] = title
        }.resultedValues!!.first()
            .map { it[Companies.id].value }
    }

    override fun findCompanyIdByUserId(userId: Long) = dbQuery {
        Users.selectAll()
            .where { Users.id eq userId }
            .singleOrNull()
            ?.map { it[Users.companyId]?.value }
            ?: throw BadRequestException("No user found with id $userId")
    }
}
