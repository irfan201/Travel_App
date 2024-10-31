package com.example.travelapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import com.bumptech.glide.Glide
import com.example.travelapp.R
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.CustomPreferenceDialogBinding
import com.example.travelapp.databinding.FragmentHomeBinding
import com.example.travelapp.domain.model.TravelState
import com.example.travelapp.presentation.detail.DetailActivity
import com.example.travelapp.presentation.detail.DetailActivity.Companion.ID_TRAVEL
import com.example.travelapp.presentation.adapter.ItemListener
import com.example.travelapp.presentation.adapter.TravelAdapter
import com.example.travelapp.presentation.all.ListAllActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), ItemListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var page = 1
    private lateinit var token: String
    private val listTravels = mutableListOf<TravelResponse.DataTravel>()
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: TravelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = "Bearer ${viewModel.getLogin()}"
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

        initProfile()
        val dataType = viewModel.getType().toString()


        lifecycleScope.launch {
            if (viewModel.getType() == null) {
                showDialogPreference()
                binding?.tvBest?.text = "Best Destinations"
            } else {
                listTravels.clear()
                viewModel.getTravelType(token, page, dataType)
                binding?.tvBest?.text = dataType.replaceFirstChar { it.uppercase() }
            }
            initData()
        }



        binding?.swipeRefresh?.setOnRefreshListener {
            lifecycleScope.launch {
                listTravels.clear()
                adapter.notifyDataSetChanged()
                page = 1
                viewModel.getTravelType(token, page, dataType)
            }
        }





        binding?.tvViewAll?.setOnClickListener {
            startActivity(Intent(requireActivity(), ListAllActivity::class.java))
        }


    }

    private fun initProfile() {
        viewModel.getProfile()
        lifecycleScope.launch {
            val dataUser = viewModel.user.value
            binding?.tvName?.text = dataUser?.firstName
            binding?.ivProfile?.let {
                Glide.with(requireContext())
                    .load(dataUser?.avatar)
                    .circleCrop()
                    .into(it)
            }
        }
    }

    private fun initData() {
        binding?.apply {
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
                    R.id.ch_museum -> {
                        listTravels.clear()
                        lifecycleScope.launch {
                            viewModel.saveType("museum")
                            viewModel.getTravelType(
                                token,
                                page,
                                dialogBinding.chMuseum.text.toString()
                            )
                        }

                    }

                    R.id.ch_market -> {
                        listTravels.clear()
                        lifecycleScope.launch {
                            viewModel.saveType("pasar")
                            viewModel.getTravelType(
                                token,
                                page,
                                dialogBinding.chMarket.text.toString()
                            )
                        }
                    }

                    R.id.ch_culinary -> {
                        listTravels.clear()
                        lifecycleScope.launch {
                            viewModel.saveType("kuliner")
                            viewModel.getTravelType(
                                token,
                                page,
                                dialogBinding.chCulinary.text.toString()
                            )
                        }
                    }

                    R.id.ch_nature -> {
                        listTravels.clear()
                        lifecycleScope.launch {
                            viewModel.saveType("alam")
                            viewModel.getTravelType(
                                token,
                                page,
                                dialogBinding.chNature.text.toString()
                            )
                        }
                    }
                }
                dialog.dismiss()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycleView(listTravel: List<TravelResponse.DataTravel>) {
        adapter = TravelAdapter(listTravel, this)
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
                    page++
                    lifecycleScope.launch {
                        viewModel.getTravelType(token, page, viewModel.getType().toString())
                    }


                }
            }
        })

    }


    override fun onClick(id: Int) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(ID_TRAVEL, id)
        startActivity(intent)
    }


}