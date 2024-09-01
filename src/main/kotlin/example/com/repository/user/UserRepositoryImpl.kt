package example.com.repository.user

import example.com.data.database.DatabaseManager
import example.com.repository.user.model.RegisterDraft
import example.com.repository.user.model.UserModel
import example.com.repository.user.model.UserResponse

class UserRepositoryImpl : UserRepository {
    private val dbManager = DatabaseManager().getDatabase()
    val dataSource = UserDataSource(dbManager)
    override fun register(data: RegisterDraft): UserResponse<UserModel?> {
        val checkUser = dataSource.checkAccount(data.username, data.email)
        if (checkUser) {
            return UserResponse(
                code = 400,
                message = "Akun sudah ada, gunakan username atau email yang lain",
                success = false,
                data = null
            )
        }

        val hashedPassword = dataSource.hashPassword(data.password)

        val userId = dataSource.createUser(data, hashedPassword)
        if (userId == -1) {
            return UserResponse(
                code = 400,
                message = "Akun tidak bisa dibuat, silahkan ulangi lagi",
                success = false,
                data = null
            )
        }

        val getUser = dataSource.getUser(data.username)
            ?: return UserResponse(
                code = 400,
                message = "Gagal memberikan data akun, silahkan ulangi lagi",
                success = false,
                data = null
            )

        return UserResponse(
            code = 200,
            message = "Akun sukses dibuat",
            success = true,
            data = getUser.let {
                UserModel(
                    username = it.username,
                    full_name = it.full_name,
                    created_at = it.created_at,
                    updated_at = it.created_at,
                    email = it.email,
                    image = it.image
                )
            }
        )
    }
}