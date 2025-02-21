package com.minin.infrastructure.extension

import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.util.*

inline fun <reified R : Any> Parameters.getOrNull(name: String): R? {
    return try {
        this.getOrFail<R>(name)
    } catch (e: ParameterConversionException) {
        null
    }
}
