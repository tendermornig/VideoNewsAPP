package com.example.videonews.ui.video

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videonews.R
import com.example.videonews.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(VideoViewModel::class.java)
    }

    private lateinit var adapter: FragmentStateAdapter

    override fun getLayoutId() = R.layout.fragment_video

    override fun initView() {
        adapter = object :
            FragmentStateAdapter(activity!!.supportFragmentManager, lifecycle) {
            override fun getItemCount() = viewModel.cacheData.size

            override fun createFragment(position: Int): Fragment {
                return VideoListFragment.newInstance(viewModel.cacheData[position].categoryId)
            }
        }
        vpVideo.adapter = adapter
        TabLayoutMediator(tlCategory, vpVideo, true) { tab, position ->
            tab.text = viewModel.cacheData[position].categoryName
        }.attach()
    }

    override fun initData() {
        viewModel.getUserToken()?.let {
            viewModel.setRequestValue(it)
        }?: toReLogin("用户token为空")
        setDataStatus(viewModel.dataLiveData) {
            if (it != null && it.code != 402) {
                viewModel.cacheData.clear()
                viewModel.cacheData.addAll(it.data)
                adapter.notifyDataSetChanged()
            } else {
                viewModel.clearUserToken()
                toReLogin("用户token已过期")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            VideoFragment().apply {
            }
    }
}