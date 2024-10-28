package com.example.travelapp.presentation.onBoarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelapp.databinding.ActivityOnBoardingBinding
import com.example.travelapp.presentation.MainActivity
import com.example.travelapp.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOnBoardingBinding
    private val viewModel :OnBoardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.getLogin() != null){
                    startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
                    finish()
                } else{
                    startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
                    finish()
                }
            }

        }

    }
}