package com.example.travelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.ItemSearchTourismBinding

class SearchTravelAdapter(private val listTravel: List<TravelResponse.DataTravel> ,private val listener: ItemListener ) : RecyclerView.Adapter<SearchTravelAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemSearchTourismBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemSearchTourismBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val travel = listTravel[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(travel.image)
                .into(ivTourism)
            tvTourism.text = travel.name
            tvLocation.text = travel.location
            rateDestination.rating = travel.popularity.toFloat()
            tvRate.text = travel.popularity
        }
        holder.binding.root.setOnClickListener {
            listener.onClick(travel.id)
        }
    }

    override fun getItemCount() = listTravel.size
}