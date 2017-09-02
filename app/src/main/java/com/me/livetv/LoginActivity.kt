package com.me.livetv

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.me.livetv.domain.LoginResponseData
import com.me.livetv.utils.OAuthUtils
import com.me.livetv.utils.PrefUtils
import com.me.livetv.utils.RxJavaUtils
import com.me.livetv.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.*
import rx.Subscription

class LoginActivity : AppCompatActivity() {

    var subscribe: Subscription?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        textView_login.setOnClickListener {
           login()
        }
    }

    private fun login() {
        if (TextUtils.isEmpty(edittext_login_username.text.toString())) return
        if (TextUtils.isEmpty(edittext_login_password.text.toString())) return
        val authorization = OAuthUtils.getBasicAuthorizationHeader(Contants.CLIENT_ID, Contants.CLIENT_SECRET)
        subscribe = ApiManager.getLoginApi().getLoginResponseData(authorization, edittext_login_username.text.toString(), edittext_login_password.text.toString(), "password")
                .compose(RxJavaUtils.normalSchedulers<LoginResponseData>())
                .subscribe({loginResponseData->
                    PrefUtils.putString(this, "access_token", loginResponseData.refresh_token)
                    MainActivity.startActivity(this)
                }, {
                    ToastUtil.showMessage("登录失败")
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (subscribe?.isUnsubscribed?.not()?:false){
            subscribe?.unsubscribe()
        }
    }
}
