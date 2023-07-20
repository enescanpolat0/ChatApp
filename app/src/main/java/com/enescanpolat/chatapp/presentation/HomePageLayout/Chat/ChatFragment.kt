package com.enescanpolat.chatapp.presentation.HomePageLayout.Chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.enescanpolat.chatapp.R
import com.enescanpolat.chatapp.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChatFragment:Fragment(),ChatViews {

    private var binding:FragmentChatBinding?=null
    private val chatViewModel:ChatViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentChatBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {binding->

            val chatAdaper = ChatAdapter()
            binding.chatRecyclerView.layoutManager=LinearLayoutManager(context)
            binding.chatRecyclerView.adapter=chatAdaper
            chatViewModel.fetchAllChats(this)
            CoroutineScope(Dispatchers.IO).launch{
                chatViewModel.chatList.collectLatest {
                    chatAdaper.submitList(it)
                }
            }

        }

    }


    override fun showProgressBar() {
        binding?.let {
            it.chatProgressBar.visibility=View.VISIBLE
        }
    }

    override fun showError(error: String) {
        binding?.let {
            it.chatProgressBar.visibility=View.GONE
        }
        Toast.makeText(context,error, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding?.let {
            it.chatProgressBar.visibility=View.GONE
        }
    }



}