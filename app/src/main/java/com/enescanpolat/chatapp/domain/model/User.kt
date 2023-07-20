package com.enescanpolat.chatapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId : String,
    val userName : String?,
    val userImage : String?,
    val userNumber : String?,
    val userStatus : String?

)
