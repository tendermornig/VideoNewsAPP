package com.example.videonews.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * @author Miracle
 * 应用中所有ViewModel的基类
 */
abstract class BaseViewModel<Data, CacheData, Key> : ViewModel() {

    /**
     * 数据缓存集合
     */
    val cacheData = ArrayList<CacheData>()

    /**
     * 数据请求live data
     */
    private val requestLiveData = MutableLiveData<Key>()

    /**
     * 数据live data 通过switch Map函数转换数据请求live data获取
     */
    val dataLiveData = Transformations.switchMap(requestLiveData) {
        //请求数据
        getData(it)
    }

    /**
     * 获取数据函数 提供给子类重写
     */
    abstract fun getData(param: Key): LiveData<Result<Data>>

    /**
     * 设置请求参数live data函数 用于触发数据请求
     */
    fun setRequestValue(param: Key) {
        requestLiveData.value = param
    }
}