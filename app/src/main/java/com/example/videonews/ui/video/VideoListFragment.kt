package com.example.videonews.ui.video

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videocontroller.component.*
import com.dueeeke.videoplayer.player.AbstractPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.example.videonews.R
import com.example.videonews.ui.NavigationActivity
import com.example.videonews.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_video_list.*


class VideoListFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(VideoListViewModel::class.java)
    }
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var adapter: VideoRvAdapter
    private lateinit var mVideoView: VideoView<AbstractPlayer>
    private lateinit var mController: StandardVideoController
    private lateinit var mErrorView: ErrorView
    private lateinit var mCompleteView: CompleteView
    private lateinit var mTitleView: TitleView

    /**
     * 当前播放的位置
     */
    private var mCurPos = -1

    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    private var mLastPos = mCurPos

    override fun onResume() {
        super.onResume()
        if (mLastPos == -1) return
        if (NavigationActivity.mCurrentIndex != 0) return
        //恢复上次播放的位置
        startPlay(mLastPos)
    }

    override fun getLayoutId() = R.layout.fragment_video_list

    override fun initView() {
        initVideoView()
        mLayoutManager = LinearLayoutManager(context)
        rlVideoList.layoutManager = mLayoutManager
        adapter = VideoRvAdapter(viewModel.cacheData, context!!)
        adapter.setOnItemClickListener {
            startPlay(it)
        }
        rlVideoList.adapter = adapter
        rlVideoList.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {

            }

            override fun onChildViewDetachedFromWindow(view: View) {
                val flPlayerContainer = view.findViewById<FrameLayout>(R.id.flPlayerContainer)
                val v = flPlayerContainer.getChildAt(0)
                if (v != null && v == mVideoView && !mVideoView.isFullScreen) {
                    releaseVideoView()
                }
            }
        })
    }

    private fun initVideoView() {
        mVideoView = VideoView(context!!)
        mVideoView.setOnStateChangeListener(object : VideoView.SimpleOnStateChangeListener() {
            override fun onPlayStateChanged(playState: Int) {
                super.onPlayStateChanged(playState)
                //监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    removeViewFormParent(mVideoView)
                    mLastPos = mCurPos
                    mCurPos = -1
                }
            }
        })
        mController = StandardVideoController(activity!!)
        mErrorView = ErrorView(activity)
        mController.addControlComponent(mErrorView)
        mCompleteView = CompleteView(activity!!)
        mController.addControlComponent(mCompleteView)
        mTitleView = TitleView(activity!!)
        mController.addControlComponent(mTitleView)
        mController.addControlComponent(VodControlView(activity!!))
        mController.addControlComponent(GestureView(activity!!))
        mController.setEnableOrientation(true)
        mVideoView.setVideoController(mController)
    }

    override fun initData() {
        arguments?.let {
            val videoCategory = it.getInt(VIDEO_CATEGORY)
            viewModel.setRequestValue(videoCategory)
        }
        setDataStatus(viewModel.dataLiveData) {
            if (it != null && it.code != 402) {
                viewModel.cacheData.addAll(it.data)
                adapter.notifyItemRangeChanged(adapter.lastSize, viewModel.cacheData.size)
                adapter.lastSize = viewModel.cacheData.size
            } else {
                toReLogin("用户登录已过期")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        releaseVideoView()
    }

    /**
     * 开始播放
     * @param position 列表位置
     */
    private fun startPlay(position: Int) {
        if (mCurPos == position) return
        if (mCurPos != -1) {
            releaseVideoView()
        }
        val video = viewModel.cacheData[position]
        mVideoView.setUrl(video.playUrl)
        mTitleView.setTitle(video.vtitle)
        val itemView: View = mLayoutManager.findViewByPosition(position) ?: return
        val viewHolder: VideoRvAdapter.ViewHolder =
            itemView.tag as VideoRvAdapter.ViewHolder
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.pv, true)
        removeViewFormParent(mVideoView)
        viewHolder.flPlayerContainer.addView(mVideoView, 0)
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager()?.add(mVideoView, NavigationActivity.LIST)
        mVideoView.start()
        mCurPos = position
    }

    private fun releaseVideoView() {
        mVideoView.release()
        if (mVideoView.isFullScreen) {
            mVideoView.stopFullScreen()
        }
        if (activity!!.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        mCurPos = -1
    }

    /**
     * 将View从父控件中移除
     */
    fun removeViewFormParent(v: View?) {
        if (v == null) return
        val parent = v.parent
        if (parent is FrameLayout) {
            parent.removeView(v)
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