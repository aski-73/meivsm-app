package com.aveyon.meivsm

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aveyon.meivsm.ui.accounts.AccountCreationFragment
import com.aveyon.meivsm.ui.accounts.ContactManageFragment

class MainActivity : AppCompatActivity(), AccountCreationFragment.OnFragmentInteractionListener,
    ContactManageFragment.OnFragmentInteractionListener, ContactCreationFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.accountsFragment, R.id.contractsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Show actionbar/toolbar and bottom nav only in certain destinations
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.accountCreationFragment ||
                destination.id == R.id.contactManageFragment
            ) {
                supportActionBar?.show()
                navView.visibility = View.GONE
            } else {
                supportActionBar?.hide()
                navView.visibility = View.VISIBLE
            }
        }
    }
}
