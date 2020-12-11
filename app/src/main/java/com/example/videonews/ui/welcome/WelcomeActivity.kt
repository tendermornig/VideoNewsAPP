package com.example.videonews.ui.welcome

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.R
import com.example.videonews.ui.NavigationActivity
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.ui.user.LoginActivity
import com.example.videonews.ui.user.RegisterActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(WelcomeViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {}

    override fun initData() {
        if (viewModel.getUserToken() != null && "" != viewModel.getUserToken()) {
            NavigationActivity.startNavigationActivity(this)
            finish()
        }
        btnToLogin.setOnClickListener {
            LoginActivity.startLoginActivity(this)
            finish()
        }
        btnToRegister.setOnClickListener {
            RegisterActivity.startRegisterActivity(this)
            finish()
        }
    }

    companion object {
        private const val TAG = "WelcomeActivity"

        fun startWelcomeActivity(context: Context) {
            val intent = Intent(context, WelcomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}