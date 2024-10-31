package com.example.travelapp.presentation.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivityDetailProfileBinding
import com.example.travelapp.databinding.LoadingDialogBinding
import com.example.travelapp.domain.model.UserState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val loadingDialog by lazy {
        val binding = LoadingDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }

            viewModel.getProfile()
            lifecycleScope.launch {
                viewModel.userState.collect { value ->
                    when (value) {
                        is UserState.Error -> {
                            loadingDialog.dismiss()
                            Toast.makeText(
                                this@DetailProfileActivity,
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
                            etFirstName.setText(data.firstName)
                            etLastName.setText(data.lastName)
                            etEmail.setText(data.email)
                            etNumber.setText(data.phone)
                            Glide.with(this@DetailProfileActivity)
                                .load(data.avatar)
                                .circleCrop()
                                .into(ivProfileDetail)
                        }
                    }
                }
            }
        }

    }
}