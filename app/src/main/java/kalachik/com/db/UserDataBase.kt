package kalachik.com.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class,Tasks::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun taskDAO(): TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: UserDataBase? = null
        fun getInstance(context: Context): UserDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDataBase::class.java,
                        "user_data_database"
                    ).build()
                }
                return instance
            }
        }


    }
}