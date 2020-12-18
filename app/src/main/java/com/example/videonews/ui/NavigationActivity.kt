package com.example.videonews.ui

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.videonews.R
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.ui.news.NewsFragment
import com.example.videonews.ui.user.UserFragment
import com.example.videonews.ui.video.VideoFragment
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : BaseActivity() {

    private val menuId = listOf(R.id.tab_video, R.id.tab_news, R.id.tab_user)
    private val mFragments =
        listOf(VideoFragment.newInstance(), NewsFragment.newInstance(), UserFragment.newInstance())

    override fun getLayoutId() = R.layout.activity_navigation

    override fun initView() {
        clearNavToast()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContent, mFragments[0])
            .commitAllowingStateLoss()

    }

    override fun initData() {
        bnv.setOnNavigationItemSelectedListener {
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
        val menuItem = bnv.getChildAt(0)
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

        private const val TAG = "NavigationActivity"

        //列表播放
        const val LIST = "list"

        var mCurrentIndex = 0

        fun startNavigationActivity(context: Context) {
            val intent = Intent(context, NavigationActivity::class.java)
            context.startActivity(intent)
        }
    }
}