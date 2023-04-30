package kalachik.com.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Insert
    suspend fun insertTask(subscriber: Tasks) : Long

    @Update
    suspend fun updateTask(subscriber: Tasks) : Int

    @Delete
    suspend fun deleteTask(subscriber: Tasks) : Int

    @Query("DELETE FROM tasks_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM tasks_data_table")
    fun getAllTasks(): Flow<List<Tasks>>
}