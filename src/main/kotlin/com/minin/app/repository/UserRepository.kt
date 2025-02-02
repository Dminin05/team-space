package com.minin.app.repository

import com.minin.app.dto.auth.RegistrationRequest
import com.minin.app.model.User
import com.minin.app.model.UserDao
import com.minin.app.model.Users
import com.minin.app.model.utils.Role
import com.minin.infrastructure.extension.db.dbQuery
import org.mindrot.jbcrypt.BCrypt

interface UserRepository {

    fun findAll(): List<User>

    fun findByEmail(email: String): User?

    fun findById(id: Long): User?

    fun save(registrationRequest: RegistrationRequest): User
}

class UserRepositoryImpl : UserRepository {

    override fun findAll(): List<User> = dbQuery {
        UserDao.all()
            .map { it.toSerializable() }
    }

    override fun findByEmail(email: String) = dbQuery {
        UserDao.find { Users.email eq email }
            .firstOrNull()
            ?.toSerializable()
    }

    override fun findById(id: Long) = dbQuery {
        UserDao.findById(id)
            ?.toSerializable()
    }


    override fun save(registrationRequest: RegistrationRequest)= dbQuery {
        UserDao.new {
            firstName = registrationRequest.firstName
            lastName = registrationRequest.lastName
            email = registrationRequest.email
            password = BCrypt.hashpw(registrationRequest.password, BCrypt.gensalt())
            role = Role.USER
        }.toSerializable()
    }
}
