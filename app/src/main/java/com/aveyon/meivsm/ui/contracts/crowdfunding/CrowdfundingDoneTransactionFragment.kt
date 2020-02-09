package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import com.aveyon.meivsm.R
class CrowdfundingDoneTransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var fragment = inflater.inflate(R.layout.fragment_crowdfunding_done_transaction, container, false)

        var doneBtn = fragment.findViewById<Button>(R.id.crowdfunding_btn_done)
        doneBtn.setOnClickListener { view ->
            onDone()
        }

        return fragment
    }


    fun onDone() {
        var action = ActionOnlyNavDirections(R.id.action_global_contractsFragment)
        findNavController().navigate(action)
    }
}
