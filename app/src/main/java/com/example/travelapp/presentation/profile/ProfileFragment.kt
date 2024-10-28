package com.example.travelapp.presentation.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.travelapp.R
import com.example.travelapp.databinding.CustomPreferenceDialogBinding
import com.example.travelapp.databinding.FragmentProfileBinding
import com.example.travelapp.databinding.LoadingDialogBinding
import com.example.travelapp.domain.model.UserState
import com.example.travelapp.presentation.login.LoginActivity
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val viewModel: ProfileViewModel by activityViewModels()
    private val loadingDialog by lazy {
        val binding = LoadingDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            cvLogout.setOnClickListener {
                showDeleteDialog()
            }
            cvPreference.setOnClickListener {
                showDialogPreference()
            }
        }
        lifecycleScope.launch {
            viewModel.getProfile()
            viewModel.userState.collect{value ->
                when(value){
                    is UserState.Error -> {
                        loadingDialog.dismiss()
                        Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT).show()
                    }
                    UserState.Loading -> {
                        loadingDialog.show()
                    }
                    is UserState.Success -> {
                        loadingDialog.dismiss()
                        binding?.tvProfileName?.text = value.userData.firstName
                        binding?.tvProfileEmail?.text = value.userData.email
                        binding?.ivProfile?.let {
                            Glide.with(requireContext())
                                .load(value.userData.avatar)
                                .circleCrop()
                                .into(it)
                        }
                    }
                }
            }
        }

    }

    private fun showDeleteDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Logout")
        dialog.setMessage("Are you sure want to logout?")
        dialog.setPositiveButton("Yes"){_,_ ->
            lifecycleScope.launch {
                viewModel.logout()
            }
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
        dialog.setNegativeButton("No"){_,_ ->
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun showDialogPreference() {
        val dialogBinding = CustomPreferenceDialogBinding.inflate(layoutInflater)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext()).create()

        dialog.setView(dialogBinding.root)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        dialog.setCancelable(false)

        dialogBinding.btnNext.setOnClickListener {
            if (dialogBinding.cgDestination.checkedChipId == -1){
                Toast.makeText(requireContext(), "Please choose destination", Toast.LENGTH_SHORT).show()
            } else{
                when(dialogBinding.cgDestination.checkedChipId){
                    R.id.ch_beach -> Toast.makeText(requireContext(), dialogBinding.chBeach.text, Toast.LENGTH_SHORT).show()
                    R.id.ch_mountain -> Toast.makeText(requireContext(), dialogBinding.chMountain.text, Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}