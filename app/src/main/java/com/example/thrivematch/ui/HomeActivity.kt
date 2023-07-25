package com.example.thrivematch.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.thrivematch.R
import com.example.thrivematch.data.network.UserApi
import com.example.thrivematch.databinding.ActivityHomeBinding
import com.example.thrivematch.ui.authentication.AuthenticationViewModel
import com.example.thrivematch.ui.base.BaseViewModel
import com.example.thrivematch.util.Constants.AUTH_TOKEN
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: AuthenticationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarActivityHome)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.fragmentContainerView2)


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
        binding.sideNav.setNavigationItemSelectedListener { item:MenuItem->
            if(item.itemId.equals(R.id.logout_sidenav)){
                lifecycleScope.launch {
                    viewModel.logout()
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, AuthenticationActivity::class.java)
                    startActivity(intent)
                }
                    true
            }
            else{
                false
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