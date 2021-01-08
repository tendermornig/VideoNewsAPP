package com.example.videonews.ui.video

import androidx.lifecycle.LiveData
import com.example.videonews.logic.Repository
import com.example.videonews.base.BaseResponse
import com.example.videonews.logic.model.Video
import com.example.videonews.base.BaseViewModel

/**
 * @author Miracle
 * 视频列表viewModel
 */
class VideoListViewModel : BaseViewModel<BaseResponse<List<Video>>, Video, Int>() {

    /**
     * 当前播放的位置
     */
    var mCurPos = -1

    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    var mLastPos = mCurPos

    override fun getData(param: Int): LiveData<Result<BaseResponse<List<Video>>>> =
        Repository.getVideoList(param)
}