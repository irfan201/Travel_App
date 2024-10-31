package com.example.travelapp.presentation.all

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.ActivityListAllBinding
import com.example.travelapp.domain.model.TravelState
import com.example.travelapp.presentation.adapter.ItemListener
import com.example.travelapp.presentation.adapter.TravelAdapter
import com.example.travelapp.presentation.detail.DetailActivity
import com.example.travelapp.presentation.detail.DetailActivity.Companion.ID_TRAVEL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListAllActivity : AppCompatActivity(),ItemListener {
    private lateinit var binding: ActivityListAllBinding
    private lateinit var token :String
    private var page = 1
    private val listTravels = mutableListOf<TravelResponse.DataTravel>()
    private val viewModel :ListAllViewModel by viewModels()
    private lateinit var adapter: TravelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = "Bearer ${viewModel.getLogin()}"

        binding.swipeRefresh.setOnRefreshListener {
            listTravels.clear()
            adapter.notifyDataSetChanged()
            page = 1
            viewModel.getTravel(page,token)
        }
        listTravels.clear()
        viewModel.getTravel(page,token)

        binding.ivBack.setOnClickListener {
            finish()
        }
        initData()
    }

    private fun initData() {
        binding.apply {
            lifecycleScope.launch {
                viewModel.travelState.collect { value ->
                    when (value) {
                        is TravelState.Error -> {
                            swipeRefresh.isRefreshing = false
                            shimmerLayout.stopShimmer()
                            rvAllDestination.isVisible = false
                            shimmerLayout.isVisible = false
                            Toast.makeText(this@ListAllActivity, value.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        TravelState.Loading -> {
                            shimmerLayout.startShimmer()
                            rvAllDestination.isVisible = false
                            shimmerLayout.isVisible = true
                        }

                        is TravelState.Success -> {
                            swipeRefresh.isRefreshing = false
                            shimmerLayout.stopShimmer()
                            rvAllDestination.isVisible = true
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

    private fun initRecycleView(listTravel: List<TravelResponse.DataTravel>) {
        adapter = TravelAdapter(listTravel, this)
        val layoutManager = LinearLayoutManager(this)
        binding.rvAllDestination.layoutManager = layoutManager
        binding.rvAllDestination.adapter = adapter

        binding.rvAllDestination.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visible = layoutManager.childCount
                val totalItem = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visible >= totalItem && firstVisibleItem >= 0) {
                    page++
                    viewModel.getTravel(page,token)


                }
            }
        })

    }

    override fun onClick(id: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(ID_TRAVEL, id)
        startActivity(intent)

    }
}