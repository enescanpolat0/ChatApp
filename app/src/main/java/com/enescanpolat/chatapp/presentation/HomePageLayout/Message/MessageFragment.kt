package com.enescanpolat.chatapp.presentation.HomePageLayout.Message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enescanpolat.chatapp.databinding.FragmentMessageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessageFragment: Fragment(),MessageViews {

    private var binding : FragmentMessageBinding?=null
    private val messageViewModel:MessageViewModel by viewModels()
    lateinit var messageAdapter : MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMessageBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {binding->
            messageAdapter= MessageAdapter(this)
            binding.messageRecyclerView.adapter = messageAdapter
            binding.messageRecyclerView.layoutManager = LinearLayoutManager(context)
            messageViewModel.getAllChatMessages("",this)
            CoroutineScope(Dispatchers.IO).launch {
                messageViewModel.messages.collectLatest {
                    messageAdapter.submitList(it)
                }
            }
        }

    }

    override fun getUserId(): String {
       return messageViewModel.getUserId()
    }

    override fun showError(error: String) {
        binding!!.messageProgressBar.visibility=View.GONE
        Toast.makeText(context,error,Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding!!.messageProgressBar.visibility=View.GONE
    }

    override fun showProgressBar() {
        binding!!.messageProgressBar.visibility=View.VISIBLE
    }


}