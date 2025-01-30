package com.minin.infrastructure

import dev.hayden.KHealth
import io.ktor.server.application.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheus.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        callIdMdc("call-id")
    }
    install(CallId) {
        generate(10, "abcdefghijklmnop123456789")
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
    install(KHealth)

//    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
//
//    install(MicrometerMetrics) {
//        registry = appMicrometerRegistry
//        // ...
//    }
//
//    routing {
//        get("/metrics-micrometer") {
//            call.respond(appMicrometerRegistry.scrape())
//        }
//    }
}
