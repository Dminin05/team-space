package com.minin.app.repository

import com.minin.app.dto.company.InvitationDto
import com.minin.app.model.Invitations
import com.minin.infrastructure.exceptions.BadRequestException
import com.minin.infrastructure.extension.db.dbQuery
import com.minin.infrastructure.extension.db.map
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.*

interface InvitationRepository {
    fun save(invitationDto: InvitationDto)

    fun getByKey(key: UUID): InvitationDto

    fun delete(key: UUID)
}

class InvitationRepositoryImpl : InvitationRepository {

    override fun save(invitationDto: InvitationDto): Unit = dbQuery {
        Invitations.insert {
            it[companyId] = EntityID(invitationDto.companyId!!, Invitations)
            it[email] = invitationDto.email
            it[key] = invitationDto.key!!
        }
    }

    override fun getByKey(key: UUID) = dbQuery {
        Invitations.selectAll()
            .where { Invitations.key eq key }
            .singleOrNull()
            ?.map {
                InvitationDto(
                    email = it[Invitations.email],
                    companyId = it[Invitations.companyId].value
                )
            }
            ?: throw BadRequestException("Invitation key not found")
    }

    override fun delete(key: UUID): Unit = dbQuery {
        Invitations.deleteWhere { Invitations.key eq key }
    }
}
