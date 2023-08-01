package com.enescanpolat.chatapp.presentation

interface Views {

    fun showProgressBar(){}

    fun hideProgressBar(){}

    fun showError(error:String){}

    fun dismissOtpFragmentBottomSheetDialog(){}

    fun changeViewVisibility(){}

    fun openHomePageLayout(){}

    fun showHomePage(){}

    fun getUserId():String

    fun onBackPressed(){}

}