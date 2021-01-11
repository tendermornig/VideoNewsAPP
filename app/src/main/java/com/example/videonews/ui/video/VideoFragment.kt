package com.example.videonews.ui.video

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videonews.base.BaseFragment
import com.example.videonews.databinding.FragmentVideoBinding
import com.example.videonews.utils.Const
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author Miracle
 * 视频导航界面
 */
class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(VideoViewModel::class.java)
    }

    /**
     * 视频列表viewPager适配器
     */
    private lateinit var adapter: FragmentStateAdapter

    override fun initViewBinding() = FragmentVideoBinding.inflate(layoutInflater)

    override fun initView() {
        adapter = object :
            FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount() = viewModel.cacheData.size

            override fun createFragment(position: Int): Fragment {
                return VideoListFragment.newInstance(viewModel.cacheData[position].categoryId)
            }
        }
        mBinding.vpVideo.adapter = adapter
        TabLayoutMediator(mBinding.tlCategory, mBinding.vpVideo, true) { tab, position ->
            tab.text = viewModel.cacheData[position].categoryName
        }.attach()
    }

    override fun initData() {
        setLiveDataStatus(viewModel.dataLiveData) {
            viewModel.cacheData.clear()
            viewModel.cacheData.addAll(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.setRequestValue(viewModel.getUserToken())
    }

    override fun onDetach() {
        super.onDetach()
        getVideoViewManager()?.releaseByTag(Const.LIST)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            VideoFragment().apply {
            }
    }
}