package com.example.videonews.ui.user

import androidx.lifecycle.LiveData
import com.example.videonews.logic.Repository
import com.example.videonews.ui.base.BaseViewModel

class LoginViewModel : BaseViewModel<String, String, Map<String, String>>() {

    override fun getData(param: Map<String, String>): LiveData<Result<String>> {
        return Repository.userLogin(param)
    }

    fun saveUserToken(token: String) = Repository.saveUserToken(token)

    fun getUserToken() = Repository.getUserToken()
}