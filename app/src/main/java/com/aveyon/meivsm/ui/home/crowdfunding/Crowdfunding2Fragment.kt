package com.aveyon.meivsm.ui.home.crowdfunding

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.aveyon.meivsm.R

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.aveyon.meivsm.databinding.FragmentCrowdfunding2Binding
import androidx.lifecycle.observe
import com.aveyon.meivsm.db.AccountInterface
import java.util.*
import kotlin.collections.ArrayList

class Crowdfunding2Fragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private val viewModel: CrowdfundingCreationViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentCrowdfunding2Binding>(
            inflater,
            R.layout.fragment_crowdfunding2,
            container,
            false
        )

        // Create a spinner containing all accounts and contacts
        val spinner = binding.root.findViewById<Spinner>(R.id.crowdfunding_receiver_spinner)
        viewModel.accounts.observe(this) { accounts ->

            viewModel.contacts.observe(this) { contacts ->
                // Put all objects of type AccountInterface into an array in order to display
                // them in the spinner. The adapter holds references to the objects. When displaying
                // them their toString() method gets called
                var items: Array<AccountInterface> =
                    arrayOf(*accounts.toTypedArray(), *contacts.toTypedArray())

                val adapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
                    items
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.templateParameters.receiver = "No Address"
                // TODO error meldung
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spinner.adapter.getItem(position) != null) {
                    // Since the items store references to objects of Accountinterface
                    // they can be easily retrieved
                    viewModel.templateParameters.receiver =
                        (spinner.adapter.getItem(position) as AccountInterface).publicAddress
                }
            }
        }

        binding.root.findViewById<Button>(R.id.crowdfunding_next2).setOnClickListener {
            var action = Crowdfunding2FragmentDirections.actionCrowdfunding2ToCrowdfunding3()
            binding.root.findNavController().navigate(action)
        }

        binding.root.findViewById<Button>(R.id.crowdfunding_cancel).setOnClickListener {
            listener?.onCancelContractCreation()
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
        fun onCancelContractCreation()
    }
}
