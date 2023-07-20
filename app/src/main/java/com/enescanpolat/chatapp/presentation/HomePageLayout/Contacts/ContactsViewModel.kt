package com.enescanpolat.chatapp.presentation.HomePageLayout.Contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.domain.use_case.ContactUseCase
import com.enescanpolat.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactsViewModel @Inject constructor(private var contactUseCase: ContactUseCase) : ViewModel() {

    private val _chatAppContactsList = MutableStateFlow<List<User>>(emptyList())
    val chatAppContactsList : StateFlow<List<User>> = _chatAppContactsList

    private lateinit var contactsViews : ContactsViews

    fun getAllChatAppContacts(deviceContacts : List<String> , interfaceListener : ContactsViews) =
        viewModelScope.launch{

            contactsViews = interfaceListener

            contactUseCase.getAllUsers(deviceContacts).collectLatest {
                when(it){

                    is Resource.Loading->{
                        contactsViews.showProgressBar()
                    }

                    is Resource.Success->{
                        contactsViews.hideProgressBar()
                        _chatAppContactsList.value= it.data!!
                    }

                    is Resource.Error->{
                        contactsViews.hideProgressBar()
                        contactsViews.showError(it.message?:"Error")
                    }

                }
            }
    }

}