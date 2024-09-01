package example.com.repository.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val username: String,
    val email: String,
    val full_name: String,
    val created_at: String,
    val updated_at: String,
    val image: String,
)