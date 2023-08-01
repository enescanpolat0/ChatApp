package com.enescanpolat.chatapp.presentation.HomePageLayout.Message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.enescanpolat.chatapp.databinding.DefaultLayoutBinding
import com.enescanpolat.chatapp.databinding.ImageReciverBinding
import com.enescanpolat.chatapp.databinding.ImageSenderBinding
import com.enescanpolat.chatapp.databinding.TextReciverBinding
import com.enescanpolat.chatapp.databinding.TextSenderBinding
import com.enescanpolat.chatapp.domain.model.ModelMessage
import com.enescanpolat.chatapp.presentation.Views

class MessageAdapter(var listener:Views) : ListAdapter<ModelMessage,RecyclerView.ViewHolder>(messageDiffUtil()) {

    var text_message_sender = 0
    var text_message_reciver = 1
    var image_message_sender = 2
    var image_message_reciver = 3


    class ImageMessageViewHolderSender(var binding: ImageSenderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(messageModel:ModelMessage){
            Glide.with(itemView.context).load(messageModel.messageData).into(binding.imageSender)
        }
    }
    class ImageMessageViewHolderReciver(var binding: ImageReciverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(messageModel:ModelMessage){
            Glide.with(itemView.context).load(messageModel.messageData).into(binding.imageReciver)
        }
    }
    class TextMessageViewHolderSender(var binding: TextSenderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(messageModel:ModelMessage){
            binding.textSender.text=messageModel.messageData
        }
    }
    class TextMessageViewHolderReciver(var binding: TextReciverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(messageModel:ModelMessage){
            binding.textReciver.text=messageModel.messageData
        }
    }
    class DefaultLayoutViewHolder(var binding: DefaultLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    class messageDiffUtil : DiffUtil.ItemCallback<ModelMessage>(){
        override fun areItemsTheSame(oldItem: ModelMessage, newItem: ModelMessage): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ModelMessage, newItem: ModelMessage): Boolean {
            return oldItem.messageData==newItem.messageData
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when(getItemViewType(viewType)){
            text_message_sender->{
                TextMessageViewHolderSender(TextSenderBinding.inflate(inflater,parent,false))

            }
            text_message_reciver->{
                TextMessageViewHolderReciver(TextReciverBinding.inflate(inflater,parent,false))
            }
            image_message_sender->{
                ImageMessageViewHolderSender(ImageSenderBinding.inflate(inflater,parent,false))
            }
            image_message_reciver->{
                ImageMessageViewHolderReciver(ImageReciverBinding.inflate(inflater,parent,false))

            }
            else->{
                DefaultLayoutViewHolder(DefaultLayoutBinding.inflate(inflater,parent,false))
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var messageItem = getItem(position)
        var userId = listener.getUserId()
        if (messageItem.messageType=="text"&&messageItem.messageSender==userId){
            (holder as TextMessageViewHolderSender).bindView(messageItem)
        } else if (messageItem.messageType=="text"&&messageItem.messageReciver==userId){
            (holder as TextMessageViewHolderReciver).bindView(messageItem)
        } else if (messageItem.messageType=="image"&&messageItem.messageSender==userId){
             (holder as ImageMessageViewHolderSender).bindView(messageItem)
        } else if (messageItem.messageType=="image"&&messageItem.messageReciver==userId){
            (holder as ImageMessageViewHolderReciver).bindView(messageItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        var messageItem = getItem(position)
        var userId = listener.getUserId()
        return if (messageItem.messageType=="text"&&messageItem.messageSender==userId){
            text_message_sender
        } else if (messageItem.messageType=="text"&&messageItem.messageReciver==userId){
             text_message_reciver
        } else if (messageItem.messageType=="image"&&messageItem.messageSender==userId){
             image_message_sender
        } else if (messageItem.messageType=="image"&&messageItem.messageReciver==userId){
             image_message_reciver
        } else {
             -1
        }
    }

}