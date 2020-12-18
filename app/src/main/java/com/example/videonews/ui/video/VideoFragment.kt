package com.example.videonews.ui.video

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videonews.R
import com.example.videonews.ui.NavigationActivity
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

        fl.fitsSystemWindows = true
        adapter = object :
            FragmentStateAdapter(childFragmentManager, lifecycle) {
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
        viewModel.setRequestValue(viewModel.getUserToken())
        setDataStatus(viewModel.dataLiveData) {
            if (it != null && it.code != 402) {
                viewModel.cacheData.clear()
                viewModel.cacheData.addAll(it.data)
                adapter.notifyDataSetChanged()
            } else {
                viewModel.clearUserToken()
                toReLogin("用户登录已过期")
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            vpVideo.visibility = View.GONE
        } else {
            vpVideo.visibility = View.VISIBLE
        }
    }

    override fun onDetach() {
        super.onDetach()
        getVideoViewManager()?.releaseByTag(NavigationActivity.LIST)
    }

    companion object {

        private const val TAG = "VideoFragment"

        @JvmStatic
        fun newInstance() =
            VideoFragment().apply {
            }
    }
}