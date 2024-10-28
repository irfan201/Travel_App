package com.example.travelapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.R
import com.example.travelapp.databinding.ActivitySearchBinding
import com.example.travelapp.presentation.adapter.ItemListener
import com.example.travelapp.presentation.adapter.TravelAdapter

class SearchActivity : AppCompatActivity() ,ItemListener{
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
//        initRecycleView()


    }

//    private fun initRecycleView(){
//        val adapter = TravelAdapter(this)
//        binding.rvSearch.layoutManager = LinearLayoutManager(this)
//        binding.rvSearch.adapter = adapter
//    }

    override fun onClick() {
        startActivity(Intent(this,DetailActivity::class.java))
    }
}