package com.enescanpolat.chatapp.presentation.HomePageLayout.Status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enescanpolat.chatapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatusFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_status,container,false)
    }
}