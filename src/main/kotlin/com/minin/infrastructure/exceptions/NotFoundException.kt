package com.minin.infrastructure.exceptions

data class NotFoundException(
    override val message: String
) : RuntimeException(message)
