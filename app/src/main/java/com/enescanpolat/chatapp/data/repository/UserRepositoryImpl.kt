package com.enescanpolat.chatapp.data.repository

import com.enescanpolat.chatapp.data.localdatabase.UserDao
import com.enescanpolat.chatapp.domain.model.ModelChat
import com.enescanpolat.chatapp.domain.model.ModelMessage
import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.domain.repository.UserRepository
import com.enescanpolat.chatapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore,
                                             protected val userDao: UserDao
                                               ):UserRepository {



    override fun getAllUsers(deviceContacts: List<String>): Flow<Resource<List<User>>> = channelFlow{

        try {

            trySend(Resource.Loading())
            val contacts =userDao.getAllUsers().firstOrNull()
            if (contacts.isNullOrEmpty()){
                for (contact in deviceContacts){
                    val query = firestore.collection("users").whereEqualTo("userNumber",contact)
                    query.get().addOnCompleteListener {task->
                        if (task.isSuccessful){
                            for (document in task.result){
                                val user = getUserFromDocument(document)
                                userDao.insertUser(user)
                                CoroutineScope(Dispatchers.IO).launch {
                                    userDao.getAllUsers().collectLatest {
                                        trySend(Resource.Success(it))
                                    }
                                }


                            }
                        }
                    }
                }
                awaitClose()
            }else{
                trySend(Resource.Success(contacts))
            }




        }catch (e:Exception){
            trySend(Resource.Error(e.localizedMessage?:"Error"))
        }
    }

    override fun getAllChats(userId: String): Flow<Resource<List<ModelChat>>> = callbackFlow {
        try {
            trySend(Resource.Loading())

            val query= firestore.collection("chats").whereArrayContains("chatParticipants",userId)
            val listener = query.addSnapshotListener { snapshot, exception->
                if (exception!=null){
                    trySend(Resource.Error(exception.localizedMessage?:"Error"))
                    return@addSnapshotListener
                }
                snapshot?.let {documents->
                    val chats= mutableListOf<ModelChat>()
                    for (document in documents){
                        val chat = getChatFromDocument(document)
                        chats.add(chat)

                    }
                    trySend(Resource.Success(chats))

                }

            }
            awaitClose{
                listener.remove()
            }



        }catch (e:Exception){
            trySend(Resource.Error(e.localizedMessage?:"Error"))
        }
    }

    override fun getAllMessagesOfChat(chatId: String): Flow<Resource<List<ModelMessage>>> = callbackFlow {
        try {
            trySend(Resource.Loading())

            val listener= firestore.collection("chats").document(chatId).collection("messages").addSnapshotListener { snapshot, exception->

                    if (exception!=null){
                        trySend(Resource.Error(exception.localizedMessage?:"Error"))
                        return@addSnapshotListener
                    }
                    snapshot?.let {documents->

                        val messages= mutableListOf<ModelMessage>()
                        for (document in documents){

                            val messageModel = getMessageFromDocument(document)
                            messages.add(messageModel)

                        }
                        trySend(Resource.Success(messages))

                    }

            }
              awaitClose{
                listener.remove()
              }



        }catch (e:Exception){
            trySend(Resource.Error(e.localizedMessage?:"Error"))
        }
    }

    private fun getChatFromDocument(document: QueryDocumentSnapshot):ModelChat{
        return ModelChat(
            chatId = document.id,
            chatName = document.getString("chatName").toString(),
            chatImage = document.getString("chatImage").toString(),
            chatLastMessageTimestamp = document.getString("chatLastTimeMessageTimestamp").toString(),
            chatLastMessage = document.getString("chatLastMessage").toString(),
            chatParticipants = document.get("chatParticipants") as List<String>
        )

    }

    private fun getUserFromDocument(document: QueryDocumentSnapshot): User {
        return User(userNumber = document.getString("userNumber"),
            userName = document.getString("userName"),
            userImage = document.getString("userImage"),
            userStatus = document.getString("userStatus"),
            userId = document.id
        )
    }


    private fun getMessageFromDocument(document: QueryDocumentSnapshot):ModelMessage{
        return ModelMessage(
            messageData = document.get("messageData").toString(),
            messageType = document.get("messageType").toString(),
            messageReciver = document.get("messageReciver").toString(),
            messageSender = document.get("messageSender").toString()
        )
    }

}