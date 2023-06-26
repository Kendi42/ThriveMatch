package com.example.thrivematch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.thrivematch.R
import com.example.thrivematch.databinding.ActivityHomeBinding
import com.example.thrivematch.ui.home_fragments.HomeFragment
import com.example.thrivematch.ui.home_fragments.LikedFragment
import com.example.thrivematch.ui.home_fragments.MatchedFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        setSupportActionBar(binding.toolbarActivityHome)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_page -> replaceFragment(HomeFragment())
                R.id.heart_page -> replaceFragment(LikedFragment())
                R.id.chat_page-> replaceFragment(MatchedFragment())

                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager= supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_home_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}