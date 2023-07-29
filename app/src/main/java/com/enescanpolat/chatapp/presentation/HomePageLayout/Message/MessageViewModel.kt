package com.enescanpolat.chatapp.presentation.HomePageLayout.Message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.chatapp.domain.model.ModelMessage
import com.enescanpolat.chatapp.domain.use_case.ChatUseCase
import com.enescanpolat.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val chatUseCase: ChatUseCase):ViewModel() {

    private var _messages = MutableStateFlow<List<ModelMessage>>(emptyList())
    val messagelist : StateFlow<List<ModelMessage>> = _messages

    lateinit var messageViews:MessageViews

    fun getAllChatMessages(chatId:String,listener:MessageViews) = viewModelScope.launch {

        messageViews = listener

        chatUseCase.getAllMessagesOfChat(chatId).collectLatest {
            when(it){

                is Resource.Loading->{
                    messageViews.showProgressBar()
                }

                is Resource.Success->{
                    messageViews.hideProgressBar()
                    _messages.value= it.data!!
                }

                is Resource.Error->{
                    messageViews.hideProgressBar()
                    messageViews.showError(it.message?:"Error")
                }

            }
        }

    }
}