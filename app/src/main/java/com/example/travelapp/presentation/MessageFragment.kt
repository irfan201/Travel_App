package com.example.travelapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.R
import com.example.travelapp.databinding.FragmentMessageBinding
import com.example.travelapp.presentation.adapter.MessageAdapter

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMessage()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showMessage() {
        val adapter = MessageAdapter()
        binding?.rvMessage?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvMessage?.adapter = adapter
    }


}