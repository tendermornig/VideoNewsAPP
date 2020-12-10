package com.example.videonews.ui.video

import androidx.lifecycle.LiveData
import com.example.videonews.logic.Repository
import com.example.videonews.logic.model.BaseResponse
import com.example.videonews.logic.model.CategoryModel
import com.example.videonews.ui.base.BaseViewModel

class VideoViewModel :
    BaseViewModel<BaseResponse<List<CategoryModel>>, CategoryModel, String>() {

    override fun getData(param: String): LiveData<Result<BaseResponse<List<CategoryModel>>>> =
        Repository.getCategory(param)

    fun getUserToken() = Repository.getUserToken()
}