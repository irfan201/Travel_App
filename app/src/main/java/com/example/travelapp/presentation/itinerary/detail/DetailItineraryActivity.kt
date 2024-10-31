package com.example.travelapp.presentation.itinerary.detail

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.travelapp.R
import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.databinding.ActivityDetailItineraryBinding
import com.example.travelapp.databinding.CustomEditDialogBinding
import com.example.travelapp.domain.model.TravelLocalDetailState
import com.example.travelapp.presentation.itinerary.add.AddItineraryActivity
import com.example.travelapp.presentation.itinerary.add.AddItineraryActivity.Companion.ID_ADD_TRAVEL
import com.example.travelapp.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailItineraryBinding
    private val viewModel: DetailItineraryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idTravel = intent.getIntExtra(ID_TRAVEL_ITINERARY, 0)
        binding.ivBack.setOnClickListener {
            finish()
        }
        lifecycleScope.launch {
            viewModel.getTravelById(idTravel)
            viewModel.travelDetail.collect { value ->
                when (value) {
                    is TravelLocalDetailState.Error -> {
                        Toast.makeText(
                            this@DetailItineraryActivity,
                            value.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TravelLocalDetailState.Loading -> {

                    }

                    is TravelLocalDetailState.Success -> {
                        val dataTravel = value.data
                        initData(dataTravel)
                        binding.btnDelete.setOnClickListener {
                            deleteTravel(dataTravel)
                        }
                        binding.btnEdit.setOnClickListener {
                            showEditDialog(dataTravel)
                        }
                    }
                }
            }
        }

    }

    private fun showEditDialog(dataTravel: TravelEntity) {
        val editBinding = CustomEditDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this).create()
        dialog.setView(editBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        editBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        editBinding.etNotes.setText(dataTravel.note)
        editBinding.btnSave.setOnClickListener {
            val notes = editBinding.etNotes.text.toString()
            lifecycleScope.launch {
                viewModel.updateTravel(TravelEntity(
                    id = dataTravel.id,
                    name = dataTravel.name,
                    image = dataTravel.image,
                    type = dataTravel.type,
                    location = dataTravel.location,
                    description = dataTravel.description,
                    popularity = dataTravel.popularity,
                    activity = dataTravel.activity,
                    note = notes
                ))
                viewModel.getTravelById(dataTravel.id)
            }

            dialog.dismiss()
        }
    }

    private fun deleteTravel(dataTravel: TravelEntity) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Delete Itinerary")
        dialog.setMessage("Are you sure want to delete this destination?")
        dialog.setPositiveButton("Yes"){_,_ ->
            lifecycleScope.launch {
                viewModel.deleteTravel(dataTravel)
                val resultIntent = Intent()
                resultIntent.putExtra(RESULT_DELETED, true)
                setResult(RESULT_OK, resultIntent)
                finish()
            }

        }
        dialog.setNegativeButton("No"){_,_ ->
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun initData(dataTravel : TravelEntity){
        binding.apply {
         Glide.with(this@DetailItineraryActivity)
             .load(dataTravel.image)
             .into(ivDetailItinerary)
            tvTittleDetailItinerary.text = dataTravel.name
            tvLocationDetailItinerary.text = dataTravel.location
            rateDestinationDetailItinerary.rating = dataTravel.popularity.toFloat()
            tvRateItinerary.text = dataTravel.popularity
            tvActivity.text = dataTravel.activity
            tvAboutDesc.text = dataTravel.description
            tvNotesDesc.text = dataTravel.note
        }
    }

    companion object {
        val ID_TRAVEL_ITINERARY = "id_travel_itinerary"
        val RESULT_DELETED = "result_deleted"

    }
}