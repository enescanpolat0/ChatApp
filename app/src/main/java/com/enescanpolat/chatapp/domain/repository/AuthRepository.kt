package com.enescanpolat.chatapp.domain.repository

import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.presentation.MainActivity
import com.enescanpolat.chatapp.util.Resource
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun phoneNumberSignIn(phoneNumber:String,activity: MainActivity) : Flow<Resource<Boolean>>

    fun userIsAuthenticated():Boolean

    fun getUserId():String

    suspend fun signInWithAuthCredintal(phoneAuthCredential: PhoneAuthCredential) : Resource<Boolean>

    fun createUserProfile(user:User,userId:String):Flow<Resource<Boolean>>
}