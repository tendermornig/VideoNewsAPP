package com.example.videonews.ui

import com.example.videonews.R
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.ui.user.LoginActivity
import com.example.videonews.ui.user.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {
        btnToLogin.setOnClickListener {
            LoginActivity.startLoginActivity(this)
        }
        btnToRegister.setOnClickListener {
            RegisterActivity.startRegisterActivity(this)
        }
    }

    override fun initData() {}
}