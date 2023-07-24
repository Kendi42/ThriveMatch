package com.example.thrivematch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.ActivityHomeBinding
import com.example.thrivematch.util.Constants.AUTH_TOKEN

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarActivityHome)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.fragmentContainerView2)
        val appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = setOf())
//        setupActionBarWithNavController(navController, appBarConfiguration)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.likedFragment, R.id.matchedFragment -> {
                    binding!!.bottomNavigationView.visibility = View.VISIBLE
                    binding.toolbarActivityHome.visibility = View.VISIBLE
                }
                R.id.myAccountFragment, R.id.documentUploadFragment, R.id.subscriptionFragment -> {
                    binding.toolbarActivityHome.visibility = View.VISIBLE
                    binding!!.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding!!.bottomNavigationView.visibility = View.GONE
                    binding.toolbarActivityHome.visibility = View.GONE

                }
            }
        }
        setupWithNavController(binding!!.bottomNavigationView, navController)
        setupWithNavController(binding.sideNav, navController)

        // Open Side Navigation
        binding.sideDrawerIcon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }





    }

}