package example.com.repository.user.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDraft(
    val username: String,
    val email: String,
    val fullName: String,
    val password: String,
    val image: String
)
