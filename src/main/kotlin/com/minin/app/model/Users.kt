package com.minin.app.model

import com.minin.app.model.utils.Role
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable() {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 1024)
    val role = enumerationByName("role", 10, Role::class)
}

class UserDao(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<UserDao>(Users)
    var firstName by Users.firstName
    var lastName by Users.lastName
    var email by Users.email
    var password by Users.password
    var role by Users.role

    fun toSerializable() = User(
        id.value,
        firstName,
        lastName,
        email,
        password,
        role
    )
}

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: Role
)
