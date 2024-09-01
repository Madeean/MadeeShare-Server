package example.com.repository.user

import example.com.repository.user.model.RegisterDraft
import example.com.repository.user.model.UserModel
import example.com.repository.user.model.UserResponse

interface UserRepository {
    fun register(
        data: RegisterDraft
    ): UserResponse<UserModel?>
}