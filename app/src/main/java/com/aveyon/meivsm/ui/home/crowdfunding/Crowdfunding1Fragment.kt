package com.aveyon.meivsm.ui.home.crowdfunding

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentCrowdfunding1Binding
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class Crowdfunding1Fragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private val viewModel: CrowdfundingCreationViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentCrowdfunding1Binding>(
            inflater,
            R.layout.fragment_crowdfunding1,
            container,
            false
        )

        binding.viewModel = viewModel

        var spinner = binding.root.findViewById<Spinner>(R.id.crowdfunding_unit_spinner)
        var adapter = ArrayAdapter.createFromResource(
            activity,
            R.array.eth_units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        // Bind values and create listener for spinner in fragment since i dont know how
        // to do databinding with a spinner..
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.templateParameters.unit = "ether"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (adapter.getItem(position) != null)
                    viewModel.templateParameters.unit =
                        adapter.getItem(position).toString().toLowerCase()
                else
                    onNothingSelected(parent)
            }
        }

        // Convert String date to a timestamp (long) value when User makes input change
        var endDateInputfield =
            binding.root.findViewById<TextInputEditText>(R.id.crowdfunding_endDate)
        endDateInputfield.doOnTextChanged { text, start, count, after ->
            if (text.toString().length == 10) {
                var dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
                var date = dateFormat.parse(text.toString())
                // divide by 1000 to get seconds, because contract uses seconds
                viewModel.templateParameters.endDate = date.time / 1000
                Log.d(javaClass.name, date.time.toString())
                Log.d(javaClass.name, viewModel.templateParameters.endDate.toString())
            } else {
                // TODO error
            }
        }

        // Zur nächsten Seite..
        binding.root.findViewById<Button>(R.id.crowdfunding_next1).setOnClickListener { v ->
            val action = Crowdfunding1FragmentDirections.actionCrowdfunding1ToCrowdfunding2()
            v.findNavController().navigate(action)
        }

        // Zur Main Activity zurück gehen
        binding.root.findViewById<Button>(R.id.crowdfunding_cancel).setOnClickListener {
            listener?.onCancelContractCreation()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
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
        fun onCancelContractCreation()
    }
}
