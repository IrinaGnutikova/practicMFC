package kalachik.com.db

import androidx.room.*

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(subscriber: Users) : Long

    @Update
    suspend fun updateUser(subscriber: Users) : Int

    @Delete
    suspend fun deleteUser(subscriber: Users) : Int

    @Query("DELETE FROM users_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM users_data_table")
    fun getAllUsers(): List<Users>
}