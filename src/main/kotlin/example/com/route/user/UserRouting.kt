package example.com.route.user

import example.com.repository.user.UserRepository
import example.com.repository.user.UserRepositoryImpl
import example.com.repository.user.model.RegisterDraft
import example.com.repository.user.model.UserResponse
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.userRoute() {
    val userRepository: UserRepository = UserRepositoryImpl()
    routing {
        post("/register") {
            val uploadDir = File("uploads")
            if (!uploadDir.exists()) {
                uploadDir.mkdirs()
            }

            val host = call.request.origin.host
            val port = call.request.origin.port

            val multipart = call.receiveMultipart()
            var imagePath: String? = null
            var username: String? = null
            var email: String? = null
            var fullName: String? = null
            var password: String? = null

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        val originalFileName = "${System.currentTimeMillis()}${part.originalFileName}"
                        val file = File(uploadDir, originalFileName)
                        part.streamProvider().use { input ->
                            file.outputStream().buffered().use { output ->
                                input.copyTo(output)
                            }
                        }
                        imagePath = "http://${host}:${port}/uploads/${file.name}"
                    }

                    is PartData.FormItem -> {
                        when (part.name) {
                            "username" -> {
                                username = part.value
                            }

                            "email" -> {
                                email = part.value
                            }

                            "full_name" -> {
                                fullName = part.value
                            }

                            "password" -> {
                                password = part.value
                            }
                        }
                    }

                    is PartData.BinaryChannelItem -> {}
                    is PartData.BinaryItem -> {}
                }
                part.dispose()
            }

            if (username != null && email != null && fullName != null && password != null) {
                val registerDraft = RegisterDraft(
                    username = username ?: "",
                    email = email ?: "",
                    fullName = fullName ?: "",
                    password = password ?: "",
                    image = imagePath ?: ""
                )

                val registerResponse = userRepository.register(registerDraft)
                if (registerResponse.success) {
                    call.respond(HttpStatusCode.Created, registerResponse)
                } else {
                    call.respond(HttpStatusCode.BadRequest, registerResponse)
                }
            } else {
                val response = UserResponse(
                    code = 500,
                    message = "ada yang salah dalam pengiriman data, cek koneksi anda",
                    success = false,
                    data = null
                )
                call.respond(HttpStatusCode.BadRequest, response)
            }

        }
    }
}
