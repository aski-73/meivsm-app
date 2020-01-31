package com.aveyon.meivsm.ui.contracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aveyon.meivsm.R

class ContractsFragment : Fragment() {

    private lateinit var contractsViewModel: ContractsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contractsViewModel =
            ViewModelProviders.of(this).get(ContractsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contracts, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        contractsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}