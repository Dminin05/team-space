package com.minin.infrastructure.config

import io.ktor.server.routing.*

interface Controller {
    val setup: (Routing.() -> Unit)
}
