package com.example.travelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.databinding.ItemTourismBinding

class TravelAdapter(private var listTravel: List<TravelResponse.Data>, private val listener: ItemListener): RecyclerView.Adapter<TravelAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemTourismBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTourismBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listTravel.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val travel = listTravel[position]
        holder.binding.apply {
            tvTourism.text = travel.name
            tvLocation.text = travel.location
            tvRate.text = travel.popularity
            rateDestination.rating = travel.popularity.toFloat()
            Glide.with(holder.itemView.context)
                .load(travel.image)
                .into(ivTourism)
        }
        holder.binding.root.setOnClickListener {
            listener.onClick()
        }
    }

    fun updateTravel(newTravel : List<TravelResponse.Data>){
        listTravel = newTravel
        notifyDataSetChanged()
    }
}