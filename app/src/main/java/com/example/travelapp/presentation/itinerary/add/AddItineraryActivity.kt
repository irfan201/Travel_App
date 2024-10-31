package com.example.travelapp.presentation.itinerary.add

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.ActivityAddItineraryBinding
import com.example.travelapp.databinding.LoadingDialogBinding
import com.example.travelapp.domain.model.TravelDetailState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItineraryBinding
    private val viewModel: AddItineraryViewModel by viewModels()
    private val loadingDialog by lazy {
        val binding = LoadingDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(ID_ADD_TRAVEL,0)
        val token = "Bearer ${viewModel.getToken()}"
        lifecycleScope.launch {
            viewModel.getTravelDetail(token,id)
            viewModel.travelDetail.collect { value ->
                when (value) {
                    is TravelDetailState.Error -> {
                        loadingDialog.dismiss()
                        Toast.makeText(this@AddItineraryActivity, value.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    TravelDetailState.Loading -> {
                        loadingDialog.show()
                    }
                    is TravelDetailState.Success -> {
                        loadingDialog.dismiss()
                        val dataTravel = value.data.data
                        initData(dataTravel)
                        binding.btnSave.setOnClickListener {
                            val addNote = binding.etNotes.text.toString()
                            lifecycleScope.launch {

                                viewModel.insertTravel(TravelEntity(
                                    name = dataTravel.name,
                                    image = dataTravel.image,
                                    location = dataTravel.location,
                                    popularity = dataTravel.popularity,
                                    type = dataTravel.type,
                                    activity = dataTravel.activity,
                                    description = dataTravel.description,
                                    note = addNote
                                ))
                            }
                            finish()
                        }
                    }
                }
            }
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }


    private fun initData(dataTravel: TravelResponse.DataTravel){
        binding.apply {
            Glide.with(this@AddItineraryActivity)
                .load(dataTravel.image)
                .into(ivDetail)
            tvTittleDetail.text = dataTravel.name
            tvLocationDetail.text = dataTravel.location

        }
    }

    companion object{
        val ID_ADD_TRAVEL = "id_add_travel"
    }
}