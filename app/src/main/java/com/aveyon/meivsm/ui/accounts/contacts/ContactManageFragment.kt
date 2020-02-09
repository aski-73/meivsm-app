package com.aveyon.meivsm.ui.accounts.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentContactManageBinding
import com.aveyon.meivsm.ui.accounts.AccountsViewModel

class ContactManageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accountsViewModel: AccountsViewModel by activityViewModels()

        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentContactManageBinding>(
            inflater,
            R.layout.fragment_contact_manage,
            container,
            false
        )

        binding.viewModel = accountsViewModel

        return binding.root
    }
}
