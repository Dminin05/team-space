package com.minin.infrastructure

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import java.text.SimpleDateFormat

fun Application.configureSerialization() {

    install(ContentNegotiation) {
        jackson {
            registerModule(JavaTimeModule())
            enable(SerializationFeature.INDENT_OUTPUT)
            dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        }
    }
}
