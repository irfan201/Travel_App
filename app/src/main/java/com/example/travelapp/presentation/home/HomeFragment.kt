package com.example.travelapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.CustomPreferenceDialogBinding
import com.example.travelapp.databinding.FragmentHomeBinding
import com.example.travelapp.domain.model.TravelState
import com.example.travelapp.presentation.DetailActivity
import com.example.travelapp.presentation.adapter.ItemListener
import com.example.travelapp.presentation.adapter.TravelAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), ItemListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var index = 1
    private val token =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

    private val listTravels = mutableListOf<TravelResponse.Data>()
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stringTittle = getString(R.string.tittle_home)
        val spannableString = SpannableString(stringTittle)
        val totalLength = stringTittle.length
        val worldStartIndex = totalLength - 7

        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            ),
            worldStartIndex,
            totalLength,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding?.tvHomeTittle?.text = spannableString

        binding?.swipeRefresh?.setOnRefreshListener {
            listTravels.clear()
            viewModel.getTravel(index,token)
        }

//        showDialogPreference()

        initData()

    }

    private fun initData() {
        binding?.apply {
            viewModel.getTravel(index, token)
            lifecycleScope.launch {
                viewModel.travelState.collect { value ->
                    when (value) {
                        is TravelState.Error -> {
                            swipeRefresh.isRefreshing = false
                            shimmerLayout.stopShimmer()
                            rvTourism.isVisible = false
                            shimmerLayout.isVisible = false
                            Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        TravelState.Loading -> {
                            shimmerLayout.startShimmer()
                            rvTourism.isVisible = false
                            shimmerLayout.isVisible = true
                        }

                        is TravelState.Success -> {
                            swipeRefresh.isRefreshing = false
                            shimmerLayout.stopShimmer()
                            rvTourism.isVisible = true
                            shimmerLayout.isVisible = false
                            val data = value.data.data
                            listTravels.addAll(data)
                            initRecycleView(listTravels)
                        }
                    }
                }
            }
        }
    }

    private fun showDialogPreference() {
        val dialogBinding = CustomPreferenceDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).create()

        dialog.setView(dialogBinding.root)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        dialog.setCancelable(false)

        dialogBinding.btnNext.setOnClickListener {
            if (dialogBinding.cgDestination.checkedChipId == -1) {
                Toast.makeText(requireContext(), "Please choose destination", Toast.LENGTH_SHORT)
                    .show()
            } else {
                when (dialogBinding.cgDestination.checkedChipId) {
                    R.id.ch_beach -> Toast.makeText(
                        requireContext(),
                        dialogBinding.chBeach.text,
                        Toast.LENGTH_SHORT
                    ).show()

                    R.id.ch_mountain -> Toast.makeText(
                        requireContext(),
                        dialogBinding.chMountain.text,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycleView(listTravel: List<TravelResponse.Data>) {
        val adapter = TravelAdapter(listTravel, this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvTourism?.layoutManager = layoutManager
        binding?.rvTourism?.adapter = adapter

        binding?.rvTourism?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visible = layoutManager.childCount
                val totalItem = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visible >= totalItem && firstVisibleItem >= 0) {
                    viewModel.getTravel(index + 1, token)
                }
            }
        })

    }




    override fun onClick() {
        startActivity(Intent(requireActivity(), DetailActivity::class.java))
    }


}