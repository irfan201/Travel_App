package com.example.travelapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.travelapp.databinding.ActivitySplashBinding
import com.example.travelapp.presentation.login.LoginActivity
import com.example.travelapp.presentation.onBoarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel :SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            if (viewModel.getStart()){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else{
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }

        },3000)

    }
}