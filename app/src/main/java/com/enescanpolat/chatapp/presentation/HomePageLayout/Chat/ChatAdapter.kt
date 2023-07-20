package com.enescanpolat.chatapp.presentation.HomePageLayout.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.enescanpolat.chatapp.R
import com.enescanpolat.chatapp.domain.model.ModelChat
import com.enescanpolat.chatapp.util.OutlineProvider

class ChatAdapter : ListAdapter<ModelChat,ChatAdapter.ChatViewHolder>(chatDiffUtil()) {

    class chatDiffUtil:DiffUtil.ItemCallback<ModelChat>(){
        override fun areItemsTheSame(oldItem: ModelChat, newItem: ModelChat): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ModelChat, newItem: ModelChat): Boolean {
            return oldItem.chatId==newItem.chatId
        }

    }

    class ChatViewHolder(var view:View):RecyclerView.ViewHolder(view){

        fun bindview(modelChat: ModelChat){
            val chatImageView : ImageView = view.findViewById(R.id.chat_image)
            val chatName : TextView = view.findViewById(R.id.chatName)
            val chatLastMessageView : TextView = view.findViewById(R.id.chatLastMessage)
            val chatLastMessageTimestampView : TextView = view.findViewById(R.id.chatLastMessageTimeStamp)
            chatName.text=modelChat.chatName
            chatLastMessageView.text=modelChat.chatLastMessage
            chatLastMessageTimestampView.text=modelChat.chatLastMessageTimestamp

            chatImageView.outlineProvider=OutlineProvider()
            chatImageView.clipToOutline=true
            Glide.with(itemView.context).load(modelChat.chatImage).into(chatImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.chat_card,parent,false
        )
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val modelChat = getItem(position)
        holder.bindview(modelChat)
    }

}