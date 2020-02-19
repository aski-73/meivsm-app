package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentCrowdfundingEndStateBinding
import com.aveyon.meivsm.ui.CrowdfundingFragmentsInteractionListener
import com.aveyon.meivsm.ui.contracts.ContractsViewModel
import com.aveyon.meivsm.services.web3.CrowdfundingContract

class CrowdfundingEndStateFragment : Fragment() {
    private var listener: CrowdfundingFragmentsInteractionListener? = null

    /**
     * Viewmodel needed for accessing selected contract
     */
    val contractsViewModel: ContractsViewModel by activityViewModels()

    lateinit var viewModel: CrowdfundingViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CrowdfundingFragmentsInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }

        /**
         * Factory for creating CrowdfundingViewmodel that holds all values for interaction
         * with a crowdfunding contract
         */
        var myFactory =
            CrowdfundingViewModelFactory(contractsViewModel.selectedContract as CrowdfundingContract)

        /**
         * Viewmodel that holds all values for interaction with a crowdfunding contract
         * Remind that viewModels are accesssbible after onAttach lifeycle stage
         */
        val _viewModel: CrowdfundingViewModel by activityViewModels { myFactory }
        viewModel = _viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentCrowdfundingEndStateBinding>(
            inflater,
            R.layout.fragment_crowdfunding_end_state,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewModel = viewModel


        return binding.root
    }

    fun back() {
        listener?.onCancelCrowdfundingFragment()
    }
}
