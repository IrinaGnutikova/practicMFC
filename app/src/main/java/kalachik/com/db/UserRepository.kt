package kalachik.com.db

class UserRepository (private val dao: UserDAO)  {
    val user = dao.getAllUsers()

    suspend fun insert(user: Users): Long {
        return dao.insertUser(user)
    }

    suspend fun update(user: Users): Int {
        return dao.updateUser(user)
    }

    suspend fun delete(user: Users): Int {
        return dao.deleteUser(user)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }

}