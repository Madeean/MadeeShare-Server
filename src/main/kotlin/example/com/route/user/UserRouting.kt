package example.com.route.user

import example.com.repository.user.UserRepository
import example.com.repository.user.UserRepositoryImpl
import example.com.repository.user.model.RegisterDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoute() {
    val userRepository: UserRepository = UserRepositoryImpl()
    routing {
        post("/register") {
            val registerDraft = call.receive<RegisterDraft>()
            val registerResponse = userRepository.register(registerDraft)
            if (registerResponse.success) {
                call.respond(HttpStatusCode.Created, registerResponse)
            } else {
                call.respond(HttpStatusCode.BadRequest, registerResponse)
            }
        }
    }
}
