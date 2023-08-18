package com.example.mypr37

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertUser(user: User)
   @Query("SELECT * FROM user")
   fun listUser(): Flow<List<User>>
   @Delete
  suspend fun delete(user: User):Int
}