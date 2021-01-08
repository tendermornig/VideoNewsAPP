package com.example.videonews.ui.video

import androidx.lifecycle.LiveData
import com.example.videonews.logic.Repository
import com.example.videonews.base.BaseResponse
import com.example.videonews.logic.model.Category
import com.example.videonews.base.BaseViewModel

/**
 * @author Miracle
 * 视频界面viewModel
 */
class VideoViewModel :
    BaseViewModel<BaseResponse<List<Category>>, Category, String>() {

    override fun getData(param: String): LiveData<Result<BaseResponse<List<Category>>>> =
        Repository.getVideoCategory(param)

    fun getUserToken() = Repository.getUserToken()

    fun clearUserToken() = Repository.clearUserToken()
}