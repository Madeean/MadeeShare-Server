package example.com.data.entity.user

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

interface DbUserEntity : Entity<DbUserEntity> {
    companion object : Entity.Factory<DbUserEntity>()

    val id: Int
    val username: String
    val full_name: String
    val email: String
    val password: String
    val created_at: String
    val updated_at: String
    val image: String
    val role: Int
    val last_login: String
}

object DbUserTable : Table<DbUserEntity>("user") {
    val id = int("id").primaryKey().bindTo { it.id }
    val username = text("username").bindTo { it.username }
    val email = text("email").bindTo { it.email }
    val fullName = text("full_name").bindTo { it.full_name }
    val password = text("password").bindTo { it.password }
    val createdAt = text("created_at").bindTo { it.created_at }
    val updatedAt = text("updated_at").bindTo { it.updated_at }
    val image = text("image").bindTo { it.image }
    val role = int("role").bindTo { it.role }
    val lastLogin = text("last_login").bindTo { it.last_login }
}