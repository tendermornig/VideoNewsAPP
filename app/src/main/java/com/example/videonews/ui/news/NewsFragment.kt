package com.example.videonews.ui.news

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.databinding.FragmentNewsBinding
import com.example.videonews.base.BaseFragment
import com.google.gson.Gson

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
        setDataStatus(viewModel.dataLiveData) {
            Log.d(TAG, "initView: ${Gson().toJson(it)}")
        }
        viewModel.setRequestValue(mapOf("page" to 0, "limit" to 5))
    }

    override fun initData() {
    }

    companion object {

        private const val TAG = "NewsFragment"

        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
            }
    }
}