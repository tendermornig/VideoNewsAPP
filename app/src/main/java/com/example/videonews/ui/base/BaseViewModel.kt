package com.example.videonews.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Data, CacheData, Key> : ViewModel() {

    val dataList = ArrayList<CacheData>()

    private val requestLiveData = MutableLiveData<Key>()

    val dataLiveData = Transformations.switchMap(requestLiveData,) {
        getData(it)
    }

    abstract fun getData(param: Key): LiveData<Result<Data>>

    fun getDataList(param: Key) {
        requestLiveData.value = param
    }
}