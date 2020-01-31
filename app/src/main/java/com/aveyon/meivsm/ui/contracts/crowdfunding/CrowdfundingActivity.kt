package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.aveyon.meivsm.R

class CrowdfundingActivity : AppCompatActivity(), Crowdfunding1.OnFragmentInteractionListener,
    Crowdfunding2.OnFragmentInteractionListener, Crowdfunding3.OnFragmentInteractionListener,
    Crowdfunding4.OnFragmentInteractionListener {
    private var TAG: String = "CrowdfundingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowdfunding)
    }

    override fun onFragmentInteraction(uri: Uri) {
        Log.i(TAG, "haloooooooooooo")
    }
}