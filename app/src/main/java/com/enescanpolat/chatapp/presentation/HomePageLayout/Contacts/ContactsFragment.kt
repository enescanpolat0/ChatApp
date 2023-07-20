package com.enescanpolat.chatapp.presentation.HomePageLayout.Contacts

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
import com.enescanpolat.chatapp.databinding.FragmentContactsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ContactsFragment:Fragment(),ContactsViews {

    private var binding : FragmentContactsBinding?=null
    private var deviceContacts : MutableList<String> = arrayListOf()

    private val contactsviewModel : ContactsViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentContactsBinding.inflate(inflater,container,false)
        deviceContacts = ContactsManager(requireContext()).getContacts()

        deviceContacts.map {
            it.replace("+90","").replace(" ","")
        }
        requireActivity()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {binding->
            if (deviceContacts.isNotEmpty()){

                val newList = ArrayList<String>()
                newList.add("000000")
                newList.add("000001")
                newList.add("000002")
                newList.add("000003")
                newList.add("000004")
                newList.add("000005")
                newList.add("000006")
                newList.add("000007")
                newList.add("000008")
                contactsviewModel.getAllChatAppContacts(newList,this)
                val contactsAdapter = ContactsAdapter()
                binding.contactsRecylerView.layoutManager=LinearLayoutManager(context)
                binding.contactsRecylerView.adapter=contactsAdapter
                CoroutineScope(Dispatchers.IO).launch{
                    contactsviewModel.chatAppContactsList.collectLatest {
                        withContext(Dispatchers.Main){
                            if (it.isNotEmpty()){

                                contactsAdapter.submitList(it)
                                binding.subtittleText.text = "${contactsAdapter.currentList.size} Contacts"
                            }
                        }

                    }
                }

             }
            else{
                Toast.makeText(context, "You have 0 contacts in your device", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    override fun showProgressBar() {
        binding?.let {
            it.contactProggresBar.visibility=View.VISIBLE
        }
    }

    override fun showError(error: String) {
        binding?.let {
            it.contactProggresBar.visibility=View.GONE
        }
        Toast.makeText(context,error,Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding?.let {
            it.contactProggresBar.visibility=View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_contacts_fragment,menu)
    }

}