package com.aveyon.meivsm.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.aveyon.meivsm.R
import com.aveyon.meivsm.ui.contracts.crowdfunding.CrowdfundingActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        val btn = root.findViewById<Button>(R.id.choose_contract)
        btn.setOnClickListener {
            openCrowdfundingActivity()
        }
//        val navController = findNavController(R.id.nav_host_fragment)
        return root
    }

    private fun openCrowdfundingActivity() {
        startActivity(Intent(activity, CrowdfundingActivity::class.java))
    }
}