package com.enescanpolat.chatapp.domain.repository

import com.enescanpolat.chatapp.domain.model.ModelChat
import com.enescanpolat.chatapp.domain.model.ModelMessage
import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {


    fun getAllUsers(deviceContacts : List<String>) : Flow<Resource<List<User>>>

    fun getAllChats(userId : String) : Flow<Resource<List<ModelChat>>>


    fun getAllMessagesOfChat(chatId:String):Flow<Resource<List<ModelMessage>>>



}