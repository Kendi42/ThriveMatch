package com.example.thrivematch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.ActivityHomeBinding

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
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment|| destination.id == R.id.likedFragment || destination.id == R.id.matchedFragment) {
                binding!!.bottomNavigationView.visibility = View.VISIBLE
                binding.toolbarActivityHome.visibility = View.VISIBLE
            } else {
                binding!!.bottomNavigationView.visibility = View.GONE
                binding.toolbarActivityHome.visibility = View.GONE

            }
        }

        setupWithNavController(binding!!.bottomNavigationView, navController)

    }

}