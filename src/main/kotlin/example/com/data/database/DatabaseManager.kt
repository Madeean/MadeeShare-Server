package example.com.data.database

import io.github.cdimascio.dotenv.Dotenv
import org.ktorm.database.Database

class DatabaseManager {
    val dotenv = Dotenv.load()

    private val hostName = dotenv["DATABASE_HOSTNAME"]
    private val databaseName = dotenv["DATABASE_DATABASE_NAME"]
    private val username = dotenv["DATABASE_USER"]
    private val password = dotenv["DATABASE_PASSWORD"]

    private val ktormDatabase: Database

    init {
        val jdbcUrl =
            "jdbc:mysql://$hostName:3306/$databaseName?user=$username&password=$password&useSSL=false&serverTimezone=Asia/Jakarta"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getDatabase(): Database {
        return ktormDatabase
    }
}