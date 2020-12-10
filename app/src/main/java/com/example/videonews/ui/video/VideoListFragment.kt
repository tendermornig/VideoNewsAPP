package com.example.videonews.ui.video

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.R
import com.example.videonews.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_video_list.*

class VideoListFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(VideoListViewModel::class.java)
    }
    private lateinit var adapter: VideoRvAdapter

    override fun getLayoutId() = R.layout.fragment_video_list

    override fun initView() {
        adapter = VideoRvAdapter(viewModel.cacheData, context!!)
        rlVideoList.adapter = adapter
    }

    override fun initData() {
        arguments?.let {
            val videoCategory = it.getInt(VIDEO_CATEGORY)
            viewModel.setRequestValue(videoCategory)
        }
        setDataStatus(viewModel.dataLiveData) {
            if (it != null && it.code != 402) {
                viewModel.cacheData.clear()
                viewModel.cacheData.addAll(it.data)
                adapter.notifyDataSetChanged()
            }else {
                toReLogin("用户token已过期")
            }
        }
    }

    companion object {

        private const val VIDEO_CATEGORY = "video_category"

        private const val TAG = "VideoListFragment"

        @JvmStatic
        fun newInstance(category: Int) =
            VideoListFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIDEO_CATEGORY, category)
                }
            }
    }
}