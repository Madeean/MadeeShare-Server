package example.com

import example.com.data.database.DatabaseManager
import example.com.plugins.configureHTTP
import example.com.plugins.configureRouting
import example.com.plugins.configureSecurity
import example.com.plugins.configureSerialization
import example.com.route.user.userRoute
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
//    io.ktor.server.netty.EngineMain.main(args)
    embeddedServer(
        Netty,
        port = 8080,
        host = "192.168.0.141",
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureSecurity()
    configureRouting()
    userRoute()
    DatabaseManager()
}
