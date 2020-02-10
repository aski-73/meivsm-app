package com.aveyon.meivsm.ui.home.crowdfunding

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.aveyon.meivsm.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class Crowdfunding3Fragment : Fragment() {
    private val viewModel: CrowdfundingCreationViewModel by activityViewModels()

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_crowdfunding3, container, false)

//        Timer("Timer", false).schedule(2500) {
//            val action = Crowdfunding3Directions.actionCrowdfunding3ToCrowdfunding4()
//            fragment.findNavController().navigate(action)
//        }

        Log.d(javaClass.name, viewModel.templateParameters.goal)
        Log.d(javaClass.name, viewModel.templateParameters.unit)
        Log.d(javaClass.name, viewModel.templateParameters.title)
        Log.d(javaClass.name, viewModel.templateParameters.receiver)
        Log.d(javaClass.name, viewModel.templateParameters.endDate.toString())

        // Select EOA and start with it creation and deployment of contract
        viewModel.accounts.observe(this) { accounts ->
            if (accounts.isNotEmpty()) {
                // Handles Exception when smth goes wrong in creation or deployment
                var exceptionHandler = CoroutineExceptionHandler { _, exception ->
                    Log.d(javaClass.name, exception.stackTrace.toString())
                    exception.printStackTrace()
                    var action =
                        Crowdfunding3FragmentDirections.actionCrowdfunding3ToErrorFragment2(
                            exception.message.toString()
                        )
                    findNavController().navigate(action)
                }

                // start creation and deployment in coroutine
                lifecycleScope.launch(exceptionHandler) {
                    viewModel.createNewCrowdfundingContract(accounts[0])
                    // if creation is done go to next fragment
                    val action = Crowdfunding3FragmentDirections.actionCrowdfunding3ToCrowdfunding4()
                    fragment.findNavController().navigate(action)
                }
            }
        }

        fragment.findViewById<Button>(R.id.crowdfunding_cancel).setOnClickListener{
            listener?.onCancelContractCreation()
        }
//        listener?.onFragmentDisplay()

        return fragment
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
        fun onFragmentDisplay()
        fun onCancelContractCreation()
    }
}
