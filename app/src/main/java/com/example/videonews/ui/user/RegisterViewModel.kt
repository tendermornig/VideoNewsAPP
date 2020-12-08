package com.example.videonews.ui.user

import androidx.lifecycle.LiveData
import com.example.videonews.logic.Repository
import com.example.videonews.ui.base.BaseViewModel

class RegisterViewModel : BaseViewModel<Boolean, Any, Map<String, String>>() {

    override fun getData(param: Map<String, String>): LiveData<Result<Boolean>> =
        Repository.userRegister(param)
}