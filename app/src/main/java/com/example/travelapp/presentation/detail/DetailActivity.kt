package com.example.travelapp.presentation.detail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.ActivityDetailBinding
import com.example.travelapp.databinding.LoadingDialogBinding
import com.example.travelapp.domain.model.TravelDetailState
import com.example.travelapp.presentation.itinerary.add.AddItineraryActivity
import com.example.travelapp.presentation.itinerary.add.AddItineraryActivity.Companion.ID_ADD_TRAVEL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val loadingDialog by lazy {
        val binding = LoadingDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(ID_TRAVEL, 0)
        val token = "Bearer ${viewModel.getToken()}"
        lifecycleScope.launch {
            viewModel.getTravelDetail(token, id)
            viewModel.travelDetail.collect { value ->
                when (value) {
                    is TravelDetailState.Error -> {
                        loadingDialog.dismiss()
                        Toast.makeText(this@DetailActivity, value.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    TravelDetailState.Loading -> {
                        loadingDialog.show()
                    }
                    is TravelDetailState.Success -> {
                        loadingDialog.dismiss()
                        val dataTravel = value.data.data
                        initData(dataTravel)
                    }
                }
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {
            val intent = Intent(this, AddItineraryActivity::class.java)
            intent.putExtra(ID_ADD_TRAVEL,id)
            startActivity(intent)
        }
    }

    private fun initData(dataTravel: TravelResponse.DataTravel){
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(dataTravel.image)
                .into(ivDetail)
            tvTittleDetail.text = dataTravel.name
            tvLocationDetail.text = dataTravel.location
            rateDestinationDetail.rating = dataTravel.popularity.toFloat()
            tvRate.text = dataTravel.popularity
            tvAboutDesc.text = dataTravel.description
            tvActivity.text = dataTravel.activity

        }
    }

    companion object {
        val ID_TRAVEL = "id_travel"
    }
}