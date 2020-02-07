package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentCrowdfundingFundingStateBinding


class CrowdfundingFundingStateFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        val viewModel: CrowdfundingViewModel by viewModels()

        binding.viewModel = viewModel
        binding.fragment = this

        return binding.root
    }
}
