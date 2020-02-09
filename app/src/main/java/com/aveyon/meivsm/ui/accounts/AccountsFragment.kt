package com.aveyon.meivsm.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentAccountsBinding
import com.aveyon.meivsm.ui.accounts.contacts.ContactAdapter
import com.aveyon.meivsm.ui.accounts.contacts.ContactViewHolder

class AccountsFragment : Fragment(), ContactViewHolder.AdapterItemClickListener {
    val accountsViewModel: AccountsViewModel by activityViewModels()

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class.
        val binding = DataBindingUtil.inflate<FragmentAccountsBinding>(
            inflater, R.layout.fragment_accounts,
            container, false
        )


        // Set account that should be presented
        accountsViewModel.accounts.observe(this) { accounts ->
            if (accounts.isNotEmpty())
                accountsViewModel.accountToPresent = accounts[0]
        }

        // Assign the component to a property in the binding class.
        binding.viewModel = accountsViewModel

        binding.fragment = this

        binding.lifecycleOwner = this


        // Filling recyclerview with contact objects
        viewManager = LinearLayoutManager(activity)
        contactAdapter = ContactAdapter(this)
        accountsViewModel.contacts.observe(this) { list ->
            contactAdapter.dataSet = list
            contactAdapter.notifyDataSetChanged()
        }

        // return the inflatet fragment with databinding
        return binding.root
    }

    fun onNavToAccountCreation() {
        val action =
            AccountsFragmentDirections.actionNavigationAccountsToAccountCreationFragment()
        findNavController().navigate(action)
    }

    fun onNavToAccountUpdate() {
        val action =
            AccountsFragmentDirections.actionAccountsFragmentToAccountManageFragment()
        findNavController().navigate(action)
    }

    fun onNavToContactCreation() {
        val action =
            AccountsFragmentDirections.actionAccountsFragmentToContactCreationFragment()
        findNavController().navigate(action)
    }

    /**
     * Click on an item in the contact recycler view
     */
    override fun onItemClick(view: View, position: Int) {
        accountsViewModel.contactToUpdate = contactAdapter.dataSet[position]
        onNavToContactUpdate()
    }

    /**
     * When a Contact Needs to be updated
     */
    fun onNavToContactUpdate() {
        val action =
            AccountsFragmentDirections.actionNavigationAccountsToContactManageFragment()
        findNavController().navigate(action)
    }
}