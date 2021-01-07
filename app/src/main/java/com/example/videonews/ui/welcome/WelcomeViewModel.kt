package com.example.videonews.ui.welcome

import androidx.lifecycle.ViewModel
import com.example.videonews.logic.Repository

/**
 * @author Miracle
 * 欢迎界面
 */
class WelcomeViewModel : ViewModel() {


    fun getUserToken() = Repository.getUserToken()
}