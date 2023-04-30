package kalachik.com.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_data_table")
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val FIO: String,

    val login: String,

    val password: String,

    val role: String
)
