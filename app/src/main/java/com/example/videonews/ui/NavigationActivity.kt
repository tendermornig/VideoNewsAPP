package com.example.videonews.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videonews.R
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.ui.news.NewsFragment
import com.example.videonews.ui.news.UserFragment
import com.example.videonews.ui.video.VideoFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_navigation

    @RequiresApi(Build.VERSION_CODES.R)
    override fun initView() {
        vpNav.isUserInputEnabled = false
        val icons = listOf(R.mipmap.video_icon, R.mipmap.news_icon, R.mipmap.user_icon)
        val titles = listOf(R.string.nav_video_text, R.string.nav_news_text, R.string.nav_user_text)
        val fragments = listOf({ VideoFragment.newInstance() },
            { NewsFragment.newInstance() },
            { UserFragment.newInstance() })
        val adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {

            override fun getItemCount() = fragments.size

            override fun createFragment(position: Int) = fragments[position].invoke()
        }
        vpNav.adapter = adapter
        TabLayoutMediator(tlNav, vpNav, true, false) { tab, position ->
            tab.setIcon(icons[position])
            tab.setText(titles[position])
        }.attach()
    }

    override fun initData() {}

    companion object {

        private const val TAG = "NavigationActivity"

        fun startNavigationActivity(context: Context) {
            val intent = Intent(context, NavigationActivity::class.java)
            context.startActivity(intent)
        }
    }
}