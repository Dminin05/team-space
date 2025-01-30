package com.minin

import com.minin.infrastructure.config.AppConfig
import com.minin.infrastructure.config.Controller
import com.minin.infrastructure.config.HttpServerConfig
import com.minin.infrastructure.kodein
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.kodein.di.instance
import java.util.concurrent.Executors

class WebStarter(
    private val appConfig: AppConfig,
    private val controllers: Set<Controller>
) {
    fun start(scope: CoroutineScope) {
        scope.embeddedServer(
            Netty,
            port = 8080,
            parentCoroutineContext = scope.coroutineContext
        ) {
            HttpServerConfig.configure(this, appConfig, controllers)
        }.start(wait = true)
    }
}

fun main() {
    Executors.newFixedThreadPool(4)
        .asCoroutineDispatcher().use { dispatcher ->
            runBlocking(dispatcher) {
                val webStarter by kodein.instance<WebStarter>()
                webStarter.start(this)
            }
        }
}
