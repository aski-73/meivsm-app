package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentCrowdfundingFundingStateBinding
import com.aveyon.meivsm.ui.contracts.ContractsViewModel
import com.aveyon.meivsm.ui.home.crowdfunding.Crowdfunding4
import com.aveyon.meivsm.web3.CrowdfundingContract


class CrowdfundingFundingStateFragment : Fragment() {

    /**
     * Needed for accessing the selected contract
     */
    val contractsViewModel: ContractsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentCrowdfundingFundingStateBinding>(
            inflater,
            R.layout.fragment_crowdfunding_funding_state,
            container,
            false
        )

        var myFactory =
            CrowdfundingViewModelFactory(contractsViewModel.selectedContract as CrowdfundingContract)

        val viewModel: CrowdfundingViewModel by viewModels { myFactory }


        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    fun onClickPayButton() {
        var action =
            CrowdfundingFundingStateFragmentDirections.actionCrowdfundingFundingStateToCrowdfundingPayingFragment()
        findNavController().navigate(action)
    }
}
