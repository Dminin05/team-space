package com.minin.infrastructure.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class BadRequestException(
    override val message: String
) : RuntimeException(message)
