package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentCrowdfundingPayingBinding
import com.aveyon.meivsm.ui.CrowdfundingFragmentsInteractionListener
import com.aveyon.meivsm.ui.Validator
import com.aveyon.meivsm.ui.contracts.ContractsViewModel
import com.aveyon.meivsm.web3.CrowdfundingContract

class CrowdfundingPayingFragment : Fragment() {
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
        var binding = DataBindingUtil.inflate<FragmentCrowdfundingPayingBinding>(
            inflater,
            R.layout.fragment_crowdfunding_paying,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewModel = viewModel

        // Bind values and create listener for spinner in fragment since i dont know how
        // to do databinding with a spinner..
        var spinner = binding.root.findViewById<Spinner>(R.id.crowdfunding_unit_spinner)
        var adapter = ArrayAdapter.createFromResource(
            activity,
            R.array.eth_units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.payValueUnit = "ether"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (adapter.getItem(position) != null)
                    viewModel.payValueUnit = adapter.getItem(position).toString()
                else
                    onNothingSelected(parent)
            }
        }

        return binding.root
    }

    /**
     * On click of "pay" button. Prepare contract call. Make transactio only when
     * input values are valid
     */
    fun onMakePayment() {
        if (!Validator.isValidNumber(viewModel.payValue) || viewModel.payValueUnit == "") {
            var toast = Toast.makeText(
                context,
                context?.resources?.getString(R.string.error_invalid_number, 1, 6),
                Toast.LENGTH_SHORT
            )
            toast.show()

            return
        } else {
            // Go to "DoTransactionFragment" in order to start transaction
            var action =
                CrowdfundingPayingFragmentDirections.actionCrowdfundingPayingFragmentToCrowdfundingDoTransactionFragment()
            findNavController().navigate(action)
        }
    }

    fun onCancel() {
        listener?.onCancelCrowdfundingFragment()
    }
}
