package com.example.videonews.ui.video

import androidx.lifecycle.LiveData
import com.example.videonews.logic.Repository
import com.example.videonews.logic.model.BaseResponse
import com.example.videonews.logic.model.VideoModel
import com.example.videonews.ui.base.BaseViewModel

class VideoListViewModel : BaseViewModel<BaseResponse<List<VideoModel>>, VideoModel, Int>() {

    /**
     * 当前播放的位置
     */
    var mCurPos = -1

    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    var mLastPos = mCurPos

    override fun getData(param: Int): LiveData<Result<BaseResponse<List<VideoModel>>>> =
        Repository.getVideoList(param)
}