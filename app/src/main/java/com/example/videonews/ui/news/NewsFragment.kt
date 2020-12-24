package com.example.videonews.ui.news

import com.example.videonews.databinding.FragmentNewsBinding
import com.example.videonews.ui.base.BaseFragment

class NewsFragment : BaseFragment<FragmentNewsBinding>() {

    override fun initViewBinding() = FragmentNewsBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
            }
    }
}