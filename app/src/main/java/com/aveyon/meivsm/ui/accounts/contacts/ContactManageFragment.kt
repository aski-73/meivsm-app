package com.aveyon.meivsm.ui.accounts.contacts

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
import androidx.navigation.fragment.findNavController
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentContactManageBinding
import com.aveyon.meivsm.ui.accounts.AccountsViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ContactManageFragment : Fragment() {
    val accountsViewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentContactManageBinding>(
            inflater,
            R.layout.fragment_contact_manage,
            container,
            false
        )

        binding.viewModel = accountsViewModel
        binding.fragment = this

        return binding.root
    }

    fun onContactUpdate() {
        var exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e(javaClass.name, exception.message)
            val action =
                ContactManageFragmentDirections.actionContactManageFragmentToErrorFragment(
                    exception.message ?: "Invalid Message"
                )
            findNavController().navigate(action)
        }
        lifecycleScope.launch(exceptionHandler) {

            accountsViewModel.updateContact()

            // .. account update went well
            Log.d(javaClass.name, "updated contact")
            val toast = Toast.makeText(
                context, context?.resources?.getString(R.string.contact_changed),
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

    fun onContactDelete() {
        lifecycleScope.launch {

            accountsViewModel.deleteContact()

            // .. account update went well
            Log.d(javaClass.name, "deleted contact")
            val toast = Toast.makeText(
                context, context?.resources?.getString(R.string.contact_deleted),
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}
