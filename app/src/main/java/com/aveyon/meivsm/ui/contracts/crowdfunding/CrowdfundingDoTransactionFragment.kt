package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aveyon.meivsm.R
import com.aveyon.meivsm.ui.contracts.ContractsViewModel
import com.aveyon.meivsm.services.web3.CrowdfundingContract
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CrowdfundingDoTransactionFragment : Fragment() {
    /**
     * Viewmodel needed for accessing selected contract
     */
    val contractsViewModel: ContractsViewModel by activityViewModels()

    lateinit var viewModel: CrowdfundingViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

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
        var fragment =
            inflater.inflate(R.layout.fragment_crowdfunding_do_transaction, container, false)

        return fragment
    }

    override fun onStart() {
        super.onStart()
        var exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.d(javaClass.name, exception.stackTrace.toString())
            exception.printStackTrace()
            var action =
                CrowdfundingDoTransactionFragmentDirections.actionCrowdfundingDoTransactionFragmentToErrorFragment(
                    exception.message.toString()
                )
            findNavController().navigate(action)
        }

        lifecycleScope.launch(exceptionHandler) {
            Log.d(javaClass.name, viewModel.toString())
            // geht nicht, da viewmodel eine neue instanz ist und somit den Userinput nicht mehr enthält
            var receipt = viewModel.makePayTransaction()

            // Navigate to DoneTransactionFragment when Transaction went without problems
            var action =
                CrowdfundingDoTransactionFragmentDirections.actionCrowdfundingDoTransactionFragmentToCrowdfundingDoneTransactionFragment()
            findNavController().navigate(action)
        }
    }
}
