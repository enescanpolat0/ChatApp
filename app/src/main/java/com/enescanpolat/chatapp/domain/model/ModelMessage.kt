package com.enescanpolat.chatapp.domain.model

data class ModelMessage(
    var messageType : String,
    var messageData:String,
    var messageSender : String,
    var messageReciver : String
)