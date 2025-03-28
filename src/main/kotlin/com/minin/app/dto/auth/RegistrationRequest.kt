package com.minin.app.dto.auth

data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val companyId: Long? = null
)
