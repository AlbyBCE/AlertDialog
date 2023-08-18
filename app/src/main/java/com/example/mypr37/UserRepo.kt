package com.example.mypr37

import android.content.Context
import androidx.room.Database
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UserRepo(context: Context) {
    var u1 =User (name = "Dj", age = 10)
    var u2 =User (name = "Dj", age = 10)
    var u3 =User (name = "Dj", age = 10)
 var dataBaseUser = DataBaseUser.createDataBase(context)
    var userDAO: UserDao = dataBaseUser.userDao()
    var userlist= listOf<User>(u1,u2,u3)
    fun list(): Flow<List<User>>{
        return flow{
            delay(3000)
            emit(userlist)
        }
    }
suspend fun delete(user: User){
    userDAO.delete(user)
}
    fun listDB(): Flow<List<User>>{
        return flow{
            delay(1000)
            userDAO.listUser().collect{
                emit(it)
            }

        }

    }
    suspend fun saveDB(user: User){
        userDAO.insertUser(user)
    }
}