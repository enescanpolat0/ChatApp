package com.enescanpolat.chatapp.presentation.HomePageLayout.Contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enescanpolat.chatapp.R
import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.util.OutlineProvider


class ContactsAdapter : ListAdapter<User,ContactsAdapter.ViewHolder>(ContactDiffUtil()) {


    class ViewHolder(var view : View):RecyclerView.ViewHolder(view){
        fun bindView(user: User){
            val userName : TextView = view.findViewById(R.id.txtUserName)
            val userStatus : TextView = view.findViewById(R.id.txtUserStatus)
            val userImage : ImageView = view.findViewById(R.id.user_image)
            userImage.outlineProvider = OutlineProvider()

        }
    }

    class ContactDiffUtil : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userNumber==newItem.userNumber
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.contact_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bindView(contact)
    }


}