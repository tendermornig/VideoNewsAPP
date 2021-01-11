package com.example.videonews.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.videonews.logic.Repository

/**
 * @author Miracle
 * 资讯列表子项的viewModel
 */
class NewsRvViewModel(requestParam: Int) {

    /**
     * 数据请求live data
     */
    private val requestLiveData = MutableLiveData<Int>()

    init {
        requestLiveData.value = requestParam
    }

    /**
     * 数据live data 通过switch Map函数转换数据请求live data获取
     */
    val dataLiveData = Transformations.switchMap(requestLiveData) {
        //请求数据
        Repository.getNewsThumb(it)
    }
}