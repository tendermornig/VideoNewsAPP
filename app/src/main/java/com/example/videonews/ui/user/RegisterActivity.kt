package com.example.videonews.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.R
import com.example.videonews.ui.base.BaseActivity
import com.example.videonews.utils.encoderByMd5
import com.example.videonews.utils.showToast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(RegisterViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.activity_register

    override fun initView() {
        btnRegister.setOnClickListener {
            val userAccount = etAccount.text.toString()
            val userPwd = etPwd.text.toString()
            val userTwoPwd = etTwoPwd.text.toString()
            if (verificationRegisterParam(userAccount, userPwd, userTwoPwd)) {
                val encryptionUserPwd = encoderByMd5(userPwd)
                val registerParam =
                    mapOf("userAccount" to userAccount, "userPassword" to encryptionUserPwd)
                viewModel.setRequestValue(registerParam)
            }
        }
    }

    override fun initData() {
        setDataStatus(viewModel.dataLiveData) {
            if (it != null && it) {
                "注册成功".showToast()
                finish()
            } else "账号已存在请修改账号".showToast()
        }
    }

    private fun verificationRegisterParam(
        userAccount: String,
        userPwd: String,
        userTwoPwd: String
    ): Boolean {
        if ("" == userAccount) {
            "账号不可为空".showToast()
            return false
        }
        if (userAccount.length != 10) {
            "账号长度应为10位".showToast()
            return false
        }
        if ("" == userPwd) {
            "密码不可为空".showToast()
            return false
        }
        if (userPwd.length < 9) {
            "密码长度应大于或等于10位".showToast()
            return false
        }
        if ("" == userTwoPwd) {
            "重复密码不可为空".showToast()
            return false
        }
        if (userTwoPwd.length < 9) {
            "重复密码长度应大于或等于10位".showToast()
            return false
        }
        if (userPwd != userTwoPwd) {
            "两次密码应该相同".showToast()
            return false
        }
        return true
    }

    companion object {

        fun startRegisterActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}