package com.example.travelapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.databinding.ActivityLoginBinding
import com.example.travelapp.databinding.LoadingDialogBinding
import com.example.travelapp.domain.model.UserState
import com.example.travelapp.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val loadingDialog by lazy {
        val binding = LoadingDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnLogin.setOnClickListener {
                if (validateLogin()) {
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()
                    lifecycleScope.launch {
                        viewModel.login(LoginRequest(email, password))
                        viewModel.userState.collect { value ->
                            when (value) {
                                is UserState.Error -> {
                                    loadingDialog.dismiss()
                                    Toast.makeText(
                                        this@LoginActivity,
                                        value.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                UserState.Loading -> {
                                    loadingDialog.show()
                                }
                                is UserState.Success -> {
                                    loadingDialog.dismiss()
                                    val data = value.userData
                                    viewModel.saveProfile(data)
                                    viewModel.saveLogin(data.token)
                                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private fun validateLogin(): Boolean {
        binding.apply {
            val email = etEmail.text?.trim().toString()
            val password = etPassword.text.toString()

            if (email.isEmpty()) {
                etEmail.error = "Please input your Email"
                return false
            }
            if (password.isEmpty()) {
                etEmail.error = "Please input your Password"
                return false
            }

            return true

        }
    }
}