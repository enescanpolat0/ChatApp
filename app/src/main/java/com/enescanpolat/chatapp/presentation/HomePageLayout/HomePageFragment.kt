package com.enescanpolat.chatapp.presentation.HomePageLayout

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.enescanpolat.chatapp.R
import com.enescanpolat.chatapp.databinding.FragmentHomePageBinding
import com.enescanpolat.chatapp.presentation.HomePageLayout.Calls.CallsFragment
import com.enescanpolat.chatapp.presentation.HomePageLayout.Chat.ChatFragment
import com.enescanpolat.chatapp.presentation.HomePageLayout.Contacts.ContactsFragment
import com.enescanpolat.chatapp.presentation.HomePageLayout.Status.StatusFragment
import com.enescanpolat.chatapp.util.OutlineProvider
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment:Fragment() {

    private lateinit var binding:FragmentHomePageBinding
    val fragmentList = arrayListOf(
        ChatFragment(),
        CallsFragment(),
        StatusFragment()
    )

    private var requestReadContactsLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->
            if (isGranted){
                activity?.let {
                    it.supportFragmentManager.beginTransaction().add(R.id.fragment_Conteiner,ContactsFragment(),"contacts_fragment").commit()
                }
            }else{
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_CONTACTS)){
                    launchContactsLauncherOnceAgain()
                }
            }
        }

    private fun launchContactsLauncherOnceAgain() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("OK"){_,_->
                    requestReadContactsLauncher.launch(android.Manifest.permission.READ_CONTACTS)
                }
                .setNegativeButton("Cancel",null)
                .create()
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomePageBinding.inflate(layoutInflater,container,false)
        binding.fabshowcontacts.outlineProvider = OutlineProvider()
        binding.fabshowcontacts.clipToOutline=true
        val window=requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), androidx.appcompat.R.color.material_blue_grey_800)
        binding.fabshowcontacts.setOnClickListener {
            requestReadContactsLauncher.launch(android.Manifest.permission.READ_CONTACTS)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPagerAndTabLayout()


    }

    private fun setUpViewPagerAndTabLayout() {
        binding.viewpager2.adapter = object : FragmentStateAdapter(childFragmentManager,lifecycle){
            override fun getItemCount(): Int {
                return fragmentList.size

            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }
        TabLayoutMediator(binding.tablayout,binding.viewpager2){tab,position->
            when(position){
                0->tab.text="Chat"
                1->tab.text="Call"
                2->tab.text="Status"

            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when(item.itemId){
            R.id.menu_search -> {
                // Handle search action
                true
            }

            R.id.menu_camera -> {
                // Handle camera action
                true
            }

            R.id.create_new_group -> {
                // Handle more item 1 action
                true
            }

            R.id.create_new_broadcast -> {
                // Handle more item 2 action
                true
            }

            R.id.payments -> {
                // Handle more item 2 action
                true
            }

            R.id.settings -> {
                // Handle more item 2 action
                true
            }

            R.id.starred_message -> {
                // Handle more item 2 action
                true
            }

            R.id.linked_devices -> {
                // Handle more item 2 action
                true
            }

            else -> {false}
           }
        }

    override fun onDestroy() {
        super.onDestroy()
        requestReadContactsLauncher.unregister()
    }

    //youtube videosunda 12:49'dan devamet


}