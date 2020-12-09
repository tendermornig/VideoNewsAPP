package com.example.videonews.ui.videonews

import com.example.videonews.R
import com.example.videonews.ui.base.BaseFragment

class VideoFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_video

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            VideoFragment().apply {
            }
    }
}