package com.enescanpolat.chatapp.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enescanpolat.chatapp.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase :RoomDatabase() {

    abstract fun userDao(): UserDao

}