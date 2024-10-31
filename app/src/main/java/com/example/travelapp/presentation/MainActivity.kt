package com.example.travelapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.presentation.home.HomeFragment
import com.example.travelapp.presentation.itinerary.ListItineraryFragment
import com.example.travelapp.presentation.profile.ProfileFragment
import com.example.travelapp.presentation.search.SearchActivity
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.fabSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.bottomNav.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {

                    R.id.btn_home ->{
                        replaceFragment(HomeFragment())
                        true
                    }
                    R.id.btn_profile ->{
                        replaceFragment(ProfileFragment())
                        true
                    }


                    R.id.btn_itinerary ->{
                        replaceFragment(ListItineraryFragment())
                        true
                    }


                    else -> false
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }
}