package example.com.repository.user

import example.com.data.entity.user.DbUserEntity
import example.com.data.entity.user.DbUserTable
import example.com.repository.user.model.RegisterDraft
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import org.mindrot.jbcrypt.BCrypt

class UserDataSource(private val dbManager: Database) {
    fun checkAccount(username: String, email: String): Boolean =
        dbManager.from(DbUserTable)
            .select()
            .where { (DbUserTable.username eq username) or (DbUserTable.email eq email) }
            .totalRecords > 0

    fun createUser(data: RegisterDraft, hashedPassword: String): Int {
        try {
            val userId = dbManager.insert(DbUserTable) {
                set(DbUserTable.username, data.username)
                set(DbUserTable.email, data.email)
                set(DbUserTable.fullName, data.fullName)
                set(DbUserTable.password, hashedPassword)
                set(DbUserTable.image, data.image)
                set(DbUserTable.createdAt, System.currentTimeMillis().toString())
                set(DbUserTable.updatedAt, System.currentTimeMillis().toString())
                set(DbUserTable.role, 0)
                set(DbUserTable.lastLogin, "-")
            }
            return userId
        } catch (e: Exception) {
            return -1
        }
    }

    fun getUser(username: String): DbUserEntity? = dbManager.sequenceOf(DbUserTable).find { it.username eq username }

    fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}