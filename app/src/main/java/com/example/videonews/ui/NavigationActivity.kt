package com.example.videonews.ui

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.videonews.R
import com.example.videonews.databinding.ActivityNavigationBinding
import com.example.videonews.base.ActivityCollector
import com.example.videonews.base.BaseActivity
import com.example.videonews.ui.news.NewsFragment
import com.example.videonews.ui.user.UserFragment
import com.example.videonews.ui.video.VideoFragment

/**
 * 导航界面
 */
class NavigationActivity : BaseActivity<ActivityNavigationBinding>() {

    /**
     * 底部选项id集合
     */
    private val menuId = listOf(R.id.tab_video, R.id.tab_news, R.id.tab_user)

    /**
     * 选项对应的fragment实例集合
     */
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

    /**
     * 由于BottomNavigationView控件选项自带长按弹窗
     * 此方法用于去除长按弹窗
     */
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

        /**
         * 列表播放
         */
        const val LIST = "list"

        /**
         * 当前展示的选项位置
         */
        var mCurrentIndex = 0

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, NavigationActivity::class.java)
            ActivityCollector.finishAll()
            context.startActivity(starter)
        }
    }
}