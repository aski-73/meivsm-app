package com.aveyon.meivsm.ui.accounts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.aveyon.meivsm.R
import com.aveyon.meivsm.databinding.FragmentContactCreationBinding

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContactCreationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContactCreationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactCreationFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accountsViewModel: AccountsViewModel by activityViewModels()

        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentContactCreationBinding>(
            inflater,
            R.layout.fragment_contact_creation,
            container,
            false
        )

        binding.viewModel = accountsViewModel

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
    }
}
