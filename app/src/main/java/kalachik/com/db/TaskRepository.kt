package kalachik.com.db

class TaskRepository(private val dao: TaskDAO) {
    val task = dao.getAllTasks()

    suspend fun insert(task: Tasks): Long {
        return dao.insertTask(task)
    }

    suspend fun update(task: Tasks): Int {
        return dao.updateTask(task)
    }

    suspend fun delete(task: Tasks): Int {
        return dao.deleteTask(task)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}