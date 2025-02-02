package com.minin.infrastructure

import com.minin.app.model.Users
import com.minin.infrastructure.config.AppConfig
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases(appConfig: AppConfig) {
    val database = Database.connect(
        url = appConfig.database.url,
        user = appConfig.database.user,
        password = appConfig.database.password
    )

    transaction(database) {
        SchemaUtils.createMissingTablesAndColumns(Users)
    }
}
