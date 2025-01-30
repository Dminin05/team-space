package com.minin.infrastructure

import com.minin.WebStarter
import com.minin.infrastructure.config.AppConfig
import com.minin.infrastructure.config.Controller
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import org.kodein.di.*

internal val controllers = DI.Module("controllers") {
    bindSet<Controller> {
    }
}

val kodein = DI {
    importOnce(controllers)
    bind { singleton { ConfigFactory.load().extract<AppConfig>("app") } }
    bind<WebStarter>() with singleton { WebStarter(instance(), instance()) }
}
