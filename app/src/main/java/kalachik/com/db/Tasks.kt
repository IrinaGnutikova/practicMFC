package kalachik.com.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_data_table",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["task_user_id"]
        )
    ])
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    val taskid: Int,

    @ColumnInfo(name = "task_user_id")
    val userid: Int,

    val task: String
)
