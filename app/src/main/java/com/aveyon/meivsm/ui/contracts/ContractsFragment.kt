package com.aveyon.meivsm.ui.contracts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentContractsBinding
import com.aveyon.meivsm.web3.ContractCategoriesEnum
import kotlinx.coroutines.launch

class ContractsFragment : Fragment(), ContractViewHolder.AdapterItemClickListener {

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var contractAdapter: ContractAdapter

    /**
     * Accessible after onAttach Stage
     */
    val viewModel: ContractsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding =
            DataBindingUtil.inflate<FragmentContractsBinding>(
                inflater,
                R.layout.fragment_contracts,
                container,
                false
            )

        // Filling recyclerview with contact objects
        viewManager = LinearLayoutManager(activity)
        contractAdapter = ContractAdapter(this)
        viewModel.contracts.observe(this) { contracts ->
            contractAdapter.dataSet = contracts
            contractAdapter.notifyDataSetChanged()
        }

        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    /**
     * Navigate depending on contract category and contract state
     */
    override fun onItemClick(view: View, position: Int) {
        // Start coroutine (not blocking main thread)
        lifecycleScope.launch {
            // TODO implement more contract categories
            when (contractAdapter.dataSet[position].category) {
                ContractCategoriesEnum.CROWDFUNDING.value -> {
                    val action = viewModel.getCrowdfundingAction(contractAdapter.dataSet[position])
                    Log.d(javaClass.name, action.toString())
                    findNavController().navigate(action)
                }
            }
        }

    }
}