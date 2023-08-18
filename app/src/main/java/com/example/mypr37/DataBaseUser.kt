package com.example.mypr37

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = 1
)
abstract class DataBaseUser:RoomDatabase() {

   abstract fun userDao():UserDao


   companion object {
       fun createDataBase(context:Context):DataBaseUser{
           return Room
               .databaseBuilder(context = context,
                   DataBaseUser::class.java,
                   "UserDataBase")
               .build()
       }
   }
}