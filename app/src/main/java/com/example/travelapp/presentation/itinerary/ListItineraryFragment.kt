package com.example.travelapp.presentation.itinerary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.databinding.FragmentListItineraryBinding
import com.example.travelapp.domain.model.TravelLocalState
import com.example.travelapp.presentation.adapter.ItemListener
import com.example.travelapp.presentation.adapter.ItineraryAdapter
import com.example.travelapp.presentation.itinerary.detail.DetailItineraryActivity
import com.example.travelapp.presentation.itinerary.detail.DetailItineraryActivity.Companion.ID_TRAVEL_ITINERARY
import kotlinx.coroutines.launch


class ListItineraryFragment : Fragment(), ItemListener {
    private var _binding: FragmentListItineraryBinding? = null
    private val binding get() = _binding
    private val viewModel: ListItineraryViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListItineraryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllTravel()
        lifecycleScope.launch {
            viewModel.travelState.collect { value ->
                when (value) {
                    is TravelLocalState.Error -> {
                        Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT).show()
                    }
                    TravelLocalState.Loading -> {

                    }
                    is TravelLocalState.Success -> {
                        val dataTravel = value.data
                        showRecycleView(dataTravel)
                    }
                }
            }
        }

    }

    private fun showRecycleView(listTravel: List<TravelEntity>) {
        val adapter = ItineraryAdapter(listTravel,this)
        binding?.rvItinerary?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvItinerary?.adapter = adapter
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val isDeleted = result.data?.getBooleanExtra(DetailItineraryActivity.RESULT_DELETED, false) ?: false
            if (isDeleted) {
                viewModel.getAllTravel()
            }
        }
    }


    override fun onClick(id: Int) {
        val intent = Intent(requireActivity(),DetailItineraryActivity::class.java)
        intent.putExtra(ID_TRAVEL_ITINERARY,id)
        detailLauncher.launch(intent)
    }

}