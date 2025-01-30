package com.minin.infrastructure

import io.github.flaxoos.ktor.server.plugins.ratelimiter.*
import io.github.flaxoos.ktor.server.plugins.ratelimiter.implementations.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.time.Duration.Companion.minutes

fun Application.configureRateLimiter() {
    routing {
        route("/") {
            install(RateLimiting) {
                rateLimiter {
                    type = TokenBucket::class
                    capacity = 1
                    rate = 10.minutes
                }

                rateLimitExceededHandler = { limitedBy ->
                    respond(HttpStatusCode.TooManyRequests, "Rate limit exceeded: ${limitedBy.message}")
                    response.headers.append("X-RateLimit-Limit", "${limitedBy.rateLimiter.capacity}")
                    response.headers.append("X-RateLimit-Measured-by", limitedBy.rateLimiter.callVolumeUnit.name)
                    response.headers.append("X-RateLimit-Reset", "${limitedBy.resetIn.inWholeMilliseconds}")
                }

            }
        }
    }
}
