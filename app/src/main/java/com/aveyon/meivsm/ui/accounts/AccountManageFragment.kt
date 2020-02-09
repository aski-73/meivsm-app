package com.aveyon.meivsm.ui.accounts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentAccountManageBinding
import kotlinx.coroutines.launch

class AccountManageFragment : Fragment() {
    val accountsViewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentAccountManageBinding>(
            inflater,
            R.layout.fragment_account_manage,
            container,
            false
        )

        binding.viewModel = accountsViewModel
        binding.fragment = this

        // TODO change so that an id is given to the view model for account updating
        accountsViewModel.accountToUpdate = accountsViewModel.accountToPresent

        return binding.root
    }

    fun onAccountUpdate() {
        accountsViewModel.updateAccount()
        // .. account update went well
        Log.d(javaClass.name, "updated account")
        val toast = Toast.makeText(
            context, context?.resources?.getString(R.string.eoa_updated),
            Toast.LENGTH_SHORT
        )
        toast.show()
    }

    fun onAccountDelete() {
        accountsViewModel.deleteAccount()
        // .. account update went well
        Log.d(javaClass.name, "deleted account")
        val toast = Toast.makeText(
            context, context?.resources?.getString(R.string.eoa_deleted),
            Toast.LENGTH_SHORT
        )
        toast.show()
    }
}
