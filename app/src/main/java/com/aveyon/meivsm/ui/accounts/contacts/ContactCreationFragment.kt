package com.aveyon.meivsm.ui.accounts.contacts

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentContactCreationBinding
import com.aveyon.meivsm.ui.accounts.AccountsViewModel
import kotlinx.coroutines.launch

class ContactCreationFragment : Fragment() {
    val accountsViewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentContactCreationBinding>(
            inflater,
            R.layout.fragment_contact_creation,
            container,
            false
        )

        binding.viewModel = accountsViewModel
        binding.fragment = this

        return binding.root
    }

    fun onContactCreate() {
        lifecycleScope.launch {

            accountsViewModel.createContact()

            // .. account update went well
            Log.d(javaClass.name, "created contact")
            val toast = Toast.makeText(
                context, getString(R.string.contact_created),
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}
