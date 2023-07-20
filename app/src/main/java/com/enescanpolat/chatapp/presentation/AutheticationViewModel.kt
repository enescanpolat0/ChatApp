package com.enescanpolat.chatapp.presentation

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.domain.use_case.AutheticationUseCase
import com.enescanpolat.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutheticationViewModel @Inject constructor(private val authUseCase:AutheticationUseCase) :ViewModel() {


    lateinit var views: Views

    fun signInWithPhoneNumber(phoneNumber:String,activity: MainActivity){

        viewModelScope.launch {
            views = activity
            authUseCase.phoneNumberSignIn(phoneNumber, activity).collect {

                when(it){

                    is Resource.Loading->{
                        views.showProgressBar()
                    }

                    is Resource.Error->{
                        views.showError(it.message?:"Error")
                    }

                    is Resource.Success->{
                        views.hideProgressBar()
                        views.dismissOtpFragmentBottomSheetDialog()
                        views.changeViewVisibility()
                    }

                }


            }

        }

    }


    fun CreateUserProfile(userName : String, userNumber : String){
        val modelUser : User = User(userName = userName, userNumber = userNumber, userId = "", userImage = "", userStatus = "")
        viewModelScope.launch {
            authUseCase.createUserProfile(user = modelUser,authUseCase.getUserId()).collectLatest {
                when(it){
                    is Resource.Loading->{
                        views.showProgressBar()
                    }
                    is Resource.Error->{
                        views.showError(it.message?:"Error")
                    }
                    is Resource.Success->{
                        views.hideProgressBar()
                        views.showHomePage()
                    }


                }
            }
        }
    }

    fun isUserAuthenticated()=authUseCase.isUserAuthenticated()




}