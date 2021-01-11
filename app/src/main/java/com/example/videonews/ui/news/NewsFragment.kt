package com.example.videonews.ui.news

import androidx.lifecycle.ViewModelProvider
import com.example.videonews.base.BaseFragment
import com.example.videonews.databinding.FragmentNewsBinding
import com.example.videonews.utils.Const

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

    private lateinit var adapter: NewsRvAdapter

    override fun initViewBinding() = FragmentNewsBinding.inflate(layoutInflater)

    override fun initView() {
        adapter = NewsRvAdapter(viewModel.cacheData, this)
        mBinding.rvNews.adapter = adapter
    }

    override fun initData() {
        setLiveDataStatus(viewModel.dataLiveData) {
            with(viewModel.cacheData) {
                clear()
                addAll(it)
            }
            adapter.notifyDataSetChanged()
        }
        viewModel.setRequestValue(
            mapOf(
                Const.PAGE to viewModel.page,
                Const.LIMIT to viewModel.getNewsLimit()
            )
        )
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
            }
    }
}