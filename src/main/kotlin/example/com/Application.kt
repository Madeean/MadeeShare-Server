package example.com

import example.com.data.database.DatabaseManager
import example.com.plugins.configureHTTP
import example.com.plugins.configureRouting
import example.com.plugins.configureSecurity
import example.com.plugins.configureSerialization
import example.com.route.user.userRoute
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main() {
//    io.ktor.server.netty.EngineMain.main(args)
    embeddedServer(
        Netty,
        port = 8080,
        host = "localhost",
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
    routing {
        static("/uploads") {
            files("uploads")
        }
    }
}
