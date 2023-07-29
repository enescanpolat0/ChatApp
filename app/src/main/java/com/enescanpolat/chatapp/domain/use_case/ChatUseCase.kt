package com.enescanpolat.chatapp.domain.use_case

import com.enescanpolat.chatapp.domain.repository.UserRepository
import javax.inject.Inject

class ChatUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun getAllChats(userId:String)=userRepository.getAllChats(userId)

    fun getAllMessagesOfChat(chatId:String)=userRepository.getAllMessagesOfChat(chatId)

}