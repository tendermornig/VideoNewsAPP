package com.example.videonews.ui.welcome

import androidx.lifecycle.ViewModel
import com.example.videonews.logic.Repository

class WelcomeViewModel : ViewModel() {

    fun getUserToken() = Repository.getUserToken()
}