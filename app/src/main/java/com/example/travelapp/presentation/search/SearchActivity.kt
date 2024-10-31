package com.example.travelapp.presentation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.ActivitySearchBinding
import com.example.travelapp.domain.model.TravelState
import com.example.travelapp.presentation.adapter.ItemListener
import com.example.travelapp.presentation.adapter.SearchTravelAdapter
import com.example.travelapp.presentation.detail.DetailActivity
import com.example.travelapp.presentation.detail.DetailActivity.Companion.ID_TRAVEL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), ItemListener {
    private lateinit var binding: ActivitySearchBinding
    private var page = 1
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var token: String
    private val listTravels = mutableListOf<TravelResponse.DataTravel>()
    private lateinit var name :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
         token = "Bearer ${viewModel.getToken()}"

        binding.etSearch.setOnEditorActionListener(object :OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                lifecycleScope.launch {
                    listTravels.clear()
                    page = 1
                    name = v?.text.toString()
                    viewModel.searchTravel(token, page, name)
                }
                return false
            }
        })

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.ivClear.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
        })

        // Clear button functionality
        binding.ivClear.setOnClickListener {
            binding.etSearch.text.clear()
            binding.ivClear.visibility = View.GONE
            lifecycleScope.launch {
                listTravels.clear()
                page = 1
                name = ""
                viewModel.searchTravel(token, page, name)
            }
        }

        lifecycleScope.launch {
            viewModel.travelState.collect { value ->
                when (value) {
                    is TravelState.Error -> {
                        Toast.makeText(this@SearchActivity, value.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    TravelState.Loading -> {
                    }

                    is TravelState.Success -> {
                        val dataTravel = value.data.data
                        listTravels.addAll(dataTravel)
                        initRecycleView(listTravels)
                    }
                }
            }
        }


    }

    private fun initRecycleView(listTravel: List<TravelResponse.DataTravel>) {
        val adapter = SearchTravelAdapter(listTravel, this)
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvSearch.layoutManager = layoutManager
        binding.rvSearch.adapter = adapter


        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visible = layoutManager.childCount
                val totalItem = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visible >= totalItem && firstVisibleItem >= 0) {
                        page++
                    lifecycleScope.launch {
                        viewModel.searchTravel(token, page, name)
                    }


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