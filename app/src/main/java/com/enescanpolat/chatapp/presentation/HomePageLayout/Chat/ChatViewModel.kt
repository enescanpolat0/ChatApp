package com.enescanpolat.chatapp.presentation.HomePageLayout.Chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.chatapp.domain.model.ModelChat
import com.enescanpolat.chatapp.domain.use_case.AutheticationUseCase
import com.enescanpolat.chatapp.domain.use_case.ChatUseCase
import com.enescanpolat.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private  val chatUseCase: ChatUseCase ,
    private val autheticationUseCase: AutheticationUseCase):ViewModel(){

    private var _chatList = MutableStateFlow<List<ModelChat>>(emptyList())
    val chatList : StateFlow<List<ModelChat>> = _chatList

    private lateinit var chatViews : ChatViews

    fun fetchAllChats(listener : ChatViews) = viewModelScope.launch {
        chatViews=listener
        chatUseCase.getAllChats(autheticationUseCase.getUserId()).collectLatest {
            when(it){

                is Resource.Loading->{
                    chatViews.showProgressBar()
                }

                is Resource.Success->{
                    chatViews.hideProgressBar()
                    _chatList.value= it.data!!
                }

                is Resource.Error->{
                    chatViews.hideProgressBar()
                    chatViews.showError(it.message?:"Error")
                }

            }
        }
    }

}