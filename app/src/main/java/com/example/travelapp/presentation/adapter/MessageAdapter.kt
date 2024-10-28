package com.example.travelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.databinding.ItemMessageBinding

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }
}