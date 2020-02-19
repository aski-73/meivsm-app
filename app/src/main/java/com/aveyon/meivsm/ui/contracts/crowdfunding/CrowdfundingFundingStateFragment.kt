package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentCrowdfundingFundingStateBinding
import com.aveyon.meivsm.ui.contracts.ContractsViewModel
import com.aveyon.meivsm.services.web3.CrowdfundingContract
import com.aveyon.meivsm.ui.accounts.AccountsViewModel

class CrowdfundingFundingStateFragment : Fragment() {
    /**
     * Needed for accessing the selected contract
     */
    private val contractsViewModel: ContractsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCrowdfundingFundingStateBinding>(
            inflater,
            R.layout.fragment_crowdfunding_funding_state,
            container,
            false
        )
        val myFactory =
            CrowdfundingViewModelFactory(contractsViewModel.selectedContract as CrowdfundingContract)
        val viewModel: CrowdfundingViewModel by viewModels { myFactory }
        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    fun onClickPayButton() {
        val action =
            CrowdfundingFundingStateFragmentDirections.actionCrowdfundingFundingStateToCrowdfundingPayingFragment()
        findNavController().navigate(action)
    }
}
