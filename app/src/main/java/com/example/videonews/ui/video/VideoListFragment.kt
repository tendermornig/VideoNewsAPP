package com.example.videonews.ui.video

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videocontroller.component.*
import com.dueeeke.videoplayer.player.AbstractPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoView.SimpleOnStateChangeListener
import com.example.videonews.R
import com.example.videonews.logic.model.VideoModel
import com.example.videonews.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_video_list.*

open class VideoListFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(VideoListViewModel::class.java)
    }

    private lateinit var mVideoView: VideoView<AbstractPlayer>
    private lateinit var mController: StandardVideoController
    private lateinit var mErrorView: ErrorView
    private lateinit var mCompleteView: CompleteView
    private lateinit var mTitleView: TitleView
    private lateinit var mAdapter: VideoRvAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    /**
     * 当前播放的位置
     */
    private var mCurPos = -1

    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    private var mLastPos = mCurPos;

    override fun getLayoutId() = R.layout.fragment_video_list

    override fun initView() {
        initVideoView()
        mAdapter = VideoRvAdapter(viewModel.cacheData, context!!)
        mAdapter.setOnItemClickListener {
            startPlay(it)
        }
        mLayoutManager = LinearLayoutManager(activity)
        rlVideoList.layoutManager = mLayoutManager
        rlVideoList.adapter = mAdapter
    }

    override fun initData() {
        rlVideoList.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}

            override fun onChildViewDetachedFromWindow(view: View) {
                val player = view.findViewById<FrameLayout>(R.id.flPlayerContainer)
                Log.d(TAG, "onChildViewDetachedFromWindow: ")
                val v = player.getChildAt(0)
                if (v != null && v == mVideoView && !mVideoView.isFullScreen) {
                    Log.d(TAG, "onChildViewDetachedFromWindow: true")
                    releaseVideoView()
                }
            }
        })
        arguments?.let {
            val videoCategory = it.getInt(VIDEO_CATEGORY)
            viewModel.setRequestValue(videoCategory)
        }
        setDataStatus(viewModel.dataLiveData) {
            if (it != null && it.code != 402) {
                viewModel.cacheData.addAll(it.data)
                mAdapter.notifyItemRangeChanged(mAdapter.lastSize, viewModel.cacheData.size)
                mAdapter.lastSize = viewModel.cacheData.size
            } else {
                toReLogin("用户token已过期")
            }
        }
    }

    private fun initVideoView() {
        mVideoView = VideoView(activity!!)
        mVideoView.setOnStateChangeListener(object : SimpleOnStateChangeListener() {
            override fun onPlayStateChanged(playState: Int) {
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

    /**
     * 开始播放
     * @param position 列表位置
     */
    private fun startPlay(position: Int) {
        if (mCurPos == position) return
        if (mCurPos != -1) {
            releaseVideoView()
        }
        val video: VideoModel = viewModel.cacheData[position]
        mVideoView.setUrl(video.playUrl)
        mTitleView.setTitle(video.vtitle)
        val itemView: View = mLayoutManager.findViewByPosition(position) ?: return
        Log.d(TAG, "startPlay: $view")
        val viewHolder: VideoRvAdapter.ViewHolder =
            itemView.tag as VideoRvAdapter.ViewHolder
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.pvPrepare, true)
        removeViewFormParent(mVideoView)
//        viewHolder.flPlayerContainer.addView(mVideoView, 0)
        getVideoViewManager()?.add(mVideoView, TAG_LIST)
//        mVideoView.start()
        mCurPos = position
    }

    override fun onResume() {
        super.onResume()
        if (mLastPos == -1) return
        startPlay(mLastPos)
    }

    override fun onPause() {
        super.onPause()
        releaseVideoView()
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
    open fun removeViewFormParent(v: View?) {
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