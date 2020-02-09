package com.aveyon.meivsm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aveyon.meivsm.R

class ErrorFragment : Fragment() {
    val args by navArgs<ErrorFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragment = inflater.inflate(R.layout.fragment_error, container, false)

        var errorTextView = fragment.findViewById<TextView>(R.id.error_msg)
        errorTextView.text = args.errorText

        fragment.findViewById<Button>(R.id.error_back).setOnClickListener {
            findNavController().popBackStack()
        }
        return fragment
    }
}