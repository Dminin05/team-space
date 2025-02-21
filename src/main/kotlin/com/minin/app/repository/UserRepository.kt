package com.minin.app.repository

import com.minin.app.dto.auth.RegistrationRequest
import com.minin.app.model.Companies
import com.minin.app.model.User
import com.minin.app.model.UserDao
import com.minin.app.model.Users
import com.minin.app.model.utils.Role
import com.minin.infrastructure.extension.db.dbQuery
import org.jetbrains.exposed.dao.id.EntityID
import org.mindrot.jbcrypt.BCrypt

interface UserRepository {

    fun findByEmail(email: String): User?

    fun save(registrationRequest: RegistrationRequest): User

    fun addCompany(ownerId: Long, companyId: Long)
}

class UserRepositoryImpl : UserRepository {

    override fun findByEmail(email: String) = dbQuery {
        UserDao.find { Users.email eq email }
            .firstOrNull()
            ?.toSerializable()
    }

    override fun save(registrationRequest: RegistrationRequest)= dbQuery {
        UserDao.new {
            firstName = registrationRequest.firstName
            lastName = registrationRequest.lastName
            email = registrationRequest.email
            password = BCrypt.hashpw(registrationRequest.password, BCrypt.gensalt())
            role = Role.USER
            registrationRequest.companyId?.let {
                companyId = EntityID(it, Companies)
            }
        }.toSerializable()
    }

    override fun addCompany(ownerId: Long, companyId: Long): Unit = dbQuery {
        UserDao.findByIdAndUpdate(ownerId) {
            it.companyId = EntityID(companyId, Companies)
        }
    }
}
