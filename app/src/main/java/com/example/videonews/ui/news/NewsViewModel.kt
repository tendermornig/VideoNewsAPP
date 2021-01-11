package com.example.videonews.ui.news

import androidx.lifecycle.LiveData
import com.example.videonews.base.BaseResponse
import com.example.videonews.base.BaseViewModel
import com.example.videonews.logic.Repository
import com.example.videonews.logic.model.News

/**
 * @author Miracle
 * 资讯界面viewModel
 */
class NewsViewModel : BaseViewModel<BaseResponse<List<News>>, News, Map<String, Int>>() {

    /**
     * 资讯页数
     */
    var page = 0

    override fun getData(param: Map<String, Int>): LiveData<Result<BaseResponse<List<News>>>> =
        Repository.getNewsList(param)

    fun getNewsLimit() = Repository.getNewsLimit()
}