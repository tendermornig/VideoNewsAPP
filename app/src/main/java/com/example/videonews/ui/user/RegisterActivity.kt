package com.example.videonews.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.videonews.databinding.ActivityRegisterBinding
import com.example.videonews.base.ActivityCollector
import com.example.videonews.base.BaseActivity
import com.example.videonews.utils.encoderByMd5
import com.example.videonews.utils.showToast

/**
 * @author Miracle
 * 注册界面
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(RegisterViewModel::class.java)
    }

    override fun initViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initView() {
        mBinding.btnRegister.setOnClickListener {
            val userAccount = mBinding.etAccount.text.toString()
            val userPwd = mBinding.etPwd.text.toString()
            val userTwoPwd = mBinding.etTwoPwd.text.toString()
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
                ActivityCollector.remove(weakReference)
            } else "账号已存在请修改账号".showToast()
        }
    }

    /**
     * 验证用户注册参数是否合法函数
     * @param userAccount 用户账户
     * @param userPwd 用户一次输入密码
     * @param userTwoPwd 用户二次输入密码
     * @return 验证结果
     */
    private fun verificationRegisterParam(
        userAccount: String,
        userPwd: String,
        userTwoPwd: String
    ): Boolean {
        if (userAccount.isEmpty()) {
            "账号不可为空".showToast()
            return false
        }
        if (userAccount.length != 10) {
            "账号长度应为10位".showToast()
            return false
        }
        if (userPwd.isEmpty()) {
            "密码不可为空".showToast()
            return false
        }
        if (userPwd.length < 9) {
            "密码长度应大于或等于10位".showToast()
            return false
        }
        if (userTwoPwd.isEmpty()) {
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

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RegisterActivity::class.java)
            context.startActivity(starter)
        }
    }
}