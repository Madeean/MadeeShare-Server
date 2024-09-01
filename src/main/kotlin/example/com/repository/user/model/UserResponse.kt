package example.com.repository.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse<T>(
    val code: Int,
    val message: String,
    val success: Boolean,
    val data: T?
)