package com.minin.infrastructure

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureHTTP() {
    install(CORS) {
        anyMethod()
        allowHeader("Auth")
        allowHeader(HttpHeaders.ContentType)
        allowHost("localhost:3000")
    }
}
