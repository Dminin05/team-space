package com.minin.infrastructure.exceptions

data class AuthenticationException(
    override val message: String
) : RuntimeException(message)
