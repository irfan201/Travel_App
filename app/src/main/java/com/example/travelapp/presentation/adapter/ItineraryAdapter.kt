package com.example.travelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.databinding.ItemTourismBinding

class ItineraryAdapter(private val listTravel: List<TravelEntity>,private val itemListener: ItemListener):RecyclerView.Adapter<ItineraryAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemTourismBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTourismBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listTravel.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataTravel = listTravel[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(dataTravel.image)
                .into(ivTourism)
            tvTourism.text = dataTravel.name
            tvLocation.text = dataTravel.location
            tvRate.text = dataTravel.popularity
            rateDestination.rating = dataTravel.popularity.toFloat()
        }
        holder.binding.root.setOnClickListener {
            itemListener.onClick(dataTravel.id)
        }
    }
}