package com.enescanpolat.chatapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.enescanpolat.chatapp.R
import com.enescanpolat.chatapp.databinding.ActivityMainBinding
import com.enescanpolat.chatapp.domain.model.User
import com.enescanpolat.chatapp.presentation.HomePageLayout.HomePageFragment
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),Views {

    private val autheticationViewModel : AutheticationViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    val otpValue = MutableStateFlow<String>("")
    lateinit var phoneNumber:String
    lateinit var userName : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkAuthenticationStatus()){
            openHomePage()
        }
        else{

            binding.userAuthenticationlayout.visibility=View.VISIBLE
            binding.applogo.visibility= View.VISIBLE
            binding.usernumberlayout.visibility=View.VISIBLE
            binding.textinputlayout1.visibility=View.VISIBLE
            binding.etNumber.visibility=View.VISIBLE
            binding.btProceed.visibility=View.VISIBLE
        }
        binding.btProceed.setOnClickListener {
            if (binding.etNumber.isVisible){
                phoneNumber=binding.etNumber.text.toString()
                autheticationViewModel.signInWithPhoneNumber(phoneNumber = "+90 $phoneNumber",this)
            }else{
                userName = binding.etName.text.toString()
                autheticationViewModel.CreateUserProfile(userName, userNumber = phoneNumber)
            }

        }


    }


    private fun checkAuthenticationStatus():Boolean{
        return autheticationViewModel.isUserAuthenticated()
    }

    override fun showHomePage() {
        openHomePage()
    }

    fun showBottomSheet(verificationId : String) {
        val otpFragment = OTPFragment()
        supportFragmentManager.beginTransaction().add(otpFragment,"bottomSheetOtpFragment").commit()
    }

    override fun showProgressBar() {
        binding.progressBar.visibility=View.VISIBLE
    }

    override fun hideProgressBar() {
       binding.progressBar.visibility=View.GONE
    }

    override fun showError(error:String) {
        Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
    }

    override fun dismissOtpFragmentBottomSheetDialog() {
        val fragment : Fragment? = supportFragmentManager.findFragmentByTag("bottomSheetOtpFragment")
        fragment?.let {
            (it as BottomSheetDialogFragment).dismiss()

        }
    }

    override fun changeViewVisibility() {
        binding.usernumberlayout.visibility=View.GONE
        binding.textinputlayout1.visibility=View.GONE
        binding.etNumber.visibility=View.GONE

        binding.userNamelayout.visibility=View.VISIBLE
        binding.textinputlayout2.visibility=View.VISIBLE
        binding.etName.visibility=View.VISIBLE
    }

    override fun openHomePageLayout() {
        Toast.makeText(this.applicationContext,"Home Page Layout",Toast.LENGTH_LONG).show()
    }

    private fun openHomePage(){
        setAllMainActivityViewsGone()
        binding.fragmentConteiner.visibility=View.VISIBLE
        val homePageFragment = HomePageFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_Conteiner,homePageFragment,"homePageFragment").commit()

    }

    private fun setAllMainActivityViewsGone(){
        binding.userAuthenticationlayout.visibility=View.GONE
        binding.applogo.visibility=View.GONE
        binding.usernumberlayout.visibility=View.GONE
        binding.textinputlayout1.visibility=View.GONE
        binding.etNumber.visibility=View.GONE
        binding.btProceed.visibility=View.GONE
        binding.userNamelayout.visibility=View.GONE
        binding.textinputlayout2.visibility=View.GONE
        binding.etName.visibility=View.GONE
    }



}