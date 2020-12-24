package com.example.videonews.ui

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.videonews.R
import com.example.videonews.databinding.ActivityNavigationBinding
import com.example.videonews.ui.base.ActivityCollector
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.ui.news.NewsFragment
import com.example.videonews.ui.user.UserFragment
import com.example.videonews.ui.video.VideoFragment

class NavigationActivity : BaseActivity<ActivityNavigationBinding>() {

    private val menuId = listOf(R.id.tab_video, R.id.tab_news, R.id.tab_user)
    private val mFragments =
        listOf(VideoFragment.newInstance(), NewsFragment.newInstance(), UserFragment.newInstance())

    override fun initViewBinding() = ActivityNavigationBinding.inflate(layoutInflater)

    override fun initView() {
        clearNavToast()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContent, mFragments[0])
            .commitAllowingStateLoss()
    }

    override fun initData() {
        mBinding.bnv.setOnNavigationItemSelectedListener {
            var index = -1
            when (it.itemId) {
                menuId[0] -> index = 0
                menuId[1] -> index = 1
                menuId[2] -> index = 2
            }
            if (mCurrentIndex != index) {
                if (mCurrentIndex == 1) {
                    getVideoViewManager().releaseByTag(LIST)
                }
                val transaction = supportFragmentManager.beginTransaction()
                val frag = mFragments[index]
                val currFrag = mFragments[mCurrentIndex]
                if (frag.isAdded) {
                    transaction.hide(currFrag).show(frag)
                } else {
                    transaction.add(R.id.flContent, frag).hide(currFrag)
                }
                transaction.commitAllowingStateLoss()
                mCurrentIndex = index
            }
            true
        }
    }

    private fun clearNavToast() {
        val menuItem = mBinding.bnv.getChildAt(0)
        for (i in menuId.indices) {
            menuItem.findViewById<View>(menuId[i]).setOnLongClickListener { true }
        }
    }

    override fun onBackPressed() {
        if (getVideoViewManager().onBackPress(LIST)) {
            return
        }
        super.onBackPressed()
    }

    companion object {

        //列表播放
        const val LIST = "list"

        var mCurrentIndex = 0

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, NavigationActivity::class.java)
            ActivityCollector.finishAll()
            context.startActivity(starter)
        }
    }
}