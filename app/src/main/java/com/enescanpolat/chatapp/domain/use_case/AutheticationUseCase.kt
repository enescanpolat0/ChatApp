package com.enescanpolat.chatapp.domain.use_case

import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.presentation.MainActivity
import com.enescanpolat.chatapp.domain.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthCredential
import javax.inject.Inject

class AutheticationUseCase @Inject constructor(private val authRepository:AuthRepository) {


    fun phoneNumberSignIn(phoneNumber:String,activity: MainActivity) =
        authRepository.phoneNumberSignIn(phoneNumber, activity)

    fun isUserAuthenticated()=authRepository.userIsAuthenticated()

    fun getUserId()=authRepository.getUserId()

    suspend  fun signInWithAuthCredintal(phoneAuthCredential: PhoneAuthCredential) =
        authRepository.signInWithAuthCredintal(phoneAuthCredential)

    fun createUserProfile(user:User,userId:String) =
        authRepository.createUserProfile(user, userId)

}