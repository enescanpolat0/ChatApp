package com.enescanpolat.chatapp.presentation.HomePageLayout.Message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.enescanpolat.chatapp.databinding.FragmentMessageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageFragment: Fragment(),MessageViews {

    private var binding : FragmentMessageBinding?=null
    private val messageViewModel:MessageViewModel by viewModels()

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

        messageViewModel.getAllChatMessages("",this)
        CoroutineScope(Dispatchers.IO).launch {

        }
    }

}