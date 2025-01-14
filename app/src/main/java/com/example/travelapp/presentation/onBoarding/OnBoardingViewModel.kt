package com.example.travelapp.presentation.onBoarding

import androidx.lifecycle.ViewModel
import com.example.travelapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val userUseCase: UserUseCase) :ViewModel() {

     fun getLogin():String?{
        return userUseCase.getLogin()
    }

    suspend fun saveStart(start:Boolean){
        userUseCase.saveStart(start)
    }
}