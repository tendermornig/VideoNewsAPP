package com.example.videonews.ui.news

import androidx.lifecycle.ViewModelProvider
import com.example.videonews.base.BaseFragment
import com.example.videonews.databinding.FragmentNewsBinding

/**
 * @author Miracle
 * 资讯界面
 */
class NewsFragment : BaseFragment<FragmentNewsBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(NewsViewModel::class.java)
    }

    override fun initViewBinding() = FragmentNewsBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
        setDataStatus(viewModel.dataLiveData) {

        }
        viewModel.setRequestValue(mapOf("page" to 0, "limit" to 5))
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
            }
    }
}