package com.aveyon.meivsm.ui.accounts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentAccountCreationBinding
import kotlinx.coroutines.launch

class AccountCreationFragment : Fragment() {
    val accountsViewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentAccountCreationBinding>(
            inflater,
            R.layout.fragment_account_creation,
            container,
            false
        )

        binding.viewModel = accountsViewModel
        binding.fragment = this

        return binding.root
    }

    fun onAccountCreate() {
        // TODO launch in global scope, so creaton account doesnt get canceld when fragment is left
        lifecycleScope.launch {
            accountsViewModel.createAccount()
            // .. account update went well
            Log.d(javaClass.name, "created account")
            val toast = Toast.makeText(
                context, context?.resources?.getString(R.string.eoa_created),
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}
