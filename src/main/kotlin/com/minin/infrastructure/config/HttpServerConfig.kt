package com.minin.infrastructure.config

import com.minin.infrastructure.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

object HttpServerConfig {

    fun configure(
        application: Application,
        appConfig: AppConfig,
        controllers: Set<Controller>
    ) {
        application.apply {
            configureSecurity(appConfig)
            configureSerialization()
            configureStatusPages()
            configureHTTP()
            configureRateLimiter()
            configureMonitoring()
            configureDatabases(appConfig)
            controllers.forEach { routing(it.setup) }
        }
    }
}
