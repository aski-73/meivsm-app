package com.aveyon.meivsm.ui.home.crowdfunding

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import com.aveyon.meivsm.R

class CrowdfundingActivity : AppCompatActivity(), Crowdfunding1Fragment.OnFragmentInteractionListener,
    Crowdfunding2Fragment.OnFragmentInteractionListener, Crowdfunding3Fragment.OnFragmentInteractionListener,
    Crowdfunding4.OnFragmentInteractionListener {

    private var TAG: String = "CrowdfundingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowdfunding)
    }

    /**
     *  Back Button in (default) Actionbar
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_contract_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onFragmentInteraction(uri: Uri) {
        Log.i(TAG, "haloooooooooooo")
    }

    override fun onFragmentDisplay() {
        Log.i(TAG, "haloooooooooooo")
        supportActionBar?.hide()
    }

    override fun onCancelContractCreation() {
        super.finish()
    }

    /**
     * Smart Contract wurde erstellt
     */
    override fun onContractCreation() {
        super.finish()
    }

    override fun onFinish() {
        super.finish()
    }
}