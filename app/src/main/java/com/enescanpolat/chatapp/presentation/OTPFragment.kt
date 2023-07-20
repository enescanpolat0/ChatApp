package com.enescanpolat.chatapp.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.enescanpolat.chatapp.R
import com.enescanpolat.chatapp.databinding.FragmentOTPBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : BottomSheetDialogFragment() {

    private var binding : FragmentOTPBinding?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOTPBinding.inflate(inflater,container,false)
        setUpOTPInput()
        return binding!!.root
    }

    private fun setUpOTPInput(){
        binding?.let {
            it.otpbox1.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {first->
                        if (first.length==1){
                            it.otpbox2.requestFocus()
                        }

                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    TODO("Not yet implemented")
                }

            })
            it.otpbox2.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {second->
                        if (second.length==1){
                            it.otpbox3.requestFocus()
                        }

                    }
                }


                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    TODO("Not yet implemented")
                }

            })
            it.otpbox3.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {third->
                        if (third.length==1){
                            it.otpbox4.requestFocus()
                        }

                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    TODO("Not yet implemented")
                }

            })
            it.otpbox4.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {fourth->
                        if (fourth.length==1){
                            it.otpbox5.requestFocus()
                        }

                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    TODO("Not yet implemented")
                }

            })
            it.otpbox5.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {fiveth->
                        if (fiveth.length==1){
                            it.otpbox6.requestFocus()
                        }

                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    TODO("Not yet implemented")
                }

            })
            it.otpbox6.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {sixth->
                        if (sixth.length==1){
                            val otpValue : String = it.otpbox1.text.toString()+it.otpbox2.text.toString()+
                                    it.otpbox3.text.toString()+it.otpbox4.text.toString()+
                                    it.otpbox5.text.toString()+it.otpbox6.text.toString()
                            (activity as MainActivity).otpValue.value=otpValue
                        }

                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    TODO("Not yet implemented")
                }

            })
        }
    }


}