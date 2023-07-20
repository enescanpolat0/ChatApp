package com.enescanpolat.chatapp.data.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enescanpolat.chatapp.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId : String) : Flow<User>


    @Insert(onConflict =OnConflictStrategy.REPLACE)
    fun insertUser(user: User)


}