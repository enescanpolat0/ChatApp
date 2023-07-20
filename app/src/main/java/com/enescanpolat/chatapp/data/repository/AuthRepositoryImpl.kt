package com.enescanpolat.chatapp.data.repository

import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.presentation.MainActivity
import com.enescanpolat.chatapp.domain.repository.AuthRepository
import com.enescanpolat.chatapp.util.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(var  firebaseAuth: FirebaseAuth, var firebaseFirestore: FirebaseFirestore) :AuthRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun phoneNumberSignIn(
        phoneNumber: String,
        activity: MainActivity
    ): Flow<Resource<Boolean>> = channelFlow {
        try {
            trySend(Resource.Loading()).isSuccess

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setActivity(activity)
                .setTimeout(60,TimeUnit.SECONDS)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        coroutineScope.launch {
                            signInWithAuthCredintal(p0)
                        }
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        coroutineScope.launch {
                            trySend(Resource.Error(p0.localizedMessage?:"Error")).isSuccess
                        }
                    }

                    override fun onCodeSent(verificationId : String, p1: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(verificationId , p1)
                        coroutineScope.launch {
                            withContext(Dispatchers.Main){
                                activity.showBottomSheet(verificationId)
                            }

                            activity.otpValue.collect {
                                if (it.isNotBlank()){
                                    trySend(signInWithAuthCredintal(PhoneAuthProvider.getCredential(verificationId,it))).isSuccess
                                }

                            }
                        }
                    }

                }).build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }catch (e:Exception){
            Resource.Error(e.localizedMessage?:"An Error Occurred",data = null)
        }
    }

    override fun userIsAuthenticated(): Boolean {
        return firebaseAuth.currentUser!=null
    }

    override fun getUserId(): String {
        return firebaseAuth.currentUser?.let {
            it.uid
        }?:""
    }

    override suspend fun signInWithAuthCredintal(phoneAuthCredential: PhoneAuthCredential): Resource<Boolean> =
        suspendCoroutine {continuation->
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnSuccessListener {
            continuation.resume(Resource.Success(true))
        }.addOnFailureListener {exception->
            continuation.resume(Resource.Error(exception.localizedMessage?:"Error"))
        }
    }

    override fun createUserProfile(user: User, userId: String): Flow<Resource<Boolean>> = callbackFlow {
        try {
            trySend(Resource.Loading())
            firebaseFirestore.collection("users").document(userId).set(user)
                .addOnSuccessListener {
                    trySend(Resource.Success<Boolean>(true))
                   }.addOnFailureListener {
                  trySend(Resource.Error(it.localizedMessage?:"Error"))
                  }
            awaitClose()


        }catch (e:Exception){
            trySend(Resource.Error(e.localizedMessage?:"Error"))
        }
    }
}