package com.minin.infrastructure

import com.minin.infrastructure.config.AppConfig
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases(appConfig: AppConfig) {
    Database.connect(
        url = appConfig.database.url,
        user = appConfig.database.user,
        password = appConfig.database.password
    )
}
