package com.example.videonews.ui.news

import androidx.lifecycle.LiveData
import com.example.videonews.logic.Repository
import com.example.videonews.logic.model.BaseResponse
import com.example.videonews.logic.model.News
import com.example.videonews.ui.base.BaseViewModel

/**
 * @author Miracle
 * 资讯界面viewModel
 */
class NewsViewModel : BaseViewModel<BaseResponse<List<News>>, List<News>, Map<String, Int>>() {

    override fun getData(param: Map<String, Int>): LiveData<Result<BaseResponse<List<News>>>> =
        Repository.getNewsList(param)
}