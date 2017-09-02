package com.me.livetv

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.me.livetv.domain.LoginResponseData
import com.me.livetv.utils.OAuthUtils
import com.me.livetv.utils.PrefUtils
import com.me.livetv.utils.RxJavaUtils
import com.me.livetv.utils.ToastUtil
import com.open.androidtvwidget.bridge.OpenEffectBridge
import kotlinx.android.synthetic.main.activity_login.*
import rx.Subscription

class LoginActivity : AppCompatActivity() {

    var subscribe: Subscription?=null
    lateinit var  mOpenEffectBridge: OpenEffectBridge
    private var mOldFocus: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        textView_login.setOnClickListener {
           login()
        }

        mOpenEffectBridge = mainUpView1.effectBridge as OpenEffectBridge
        mainUpView1.setUpRectResource(R.drawable.test_rectangle) // 设置移动边框的图片.
        mainUpView1.setShadowResource(R.drawable.item_shadow)

        edittext_login_username.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                mainUpView1.setFocusView(v,1.1f)
            }else{
                mainUpView1.setFocusView(v,1.0f)
            }

        }
        edittext_login_password.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                mainUpView1.setFocusView(v,1.1f)
            }else{
                mainUpView1.setFocusView(v,1.0f)
            }
        }
        textView_login.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                mainUpView1.setFocusView(v,1.1f)
            }else{
                mainUpView1.setFocusView(v,1.0f)
            }
        }
        edittext_login_username.setText(PrefUtils.getString(this,"name",""))
        edittext_login_password.setText(PrefUtils.getString(this,"password",""))
    }

    private fun login() {
        if (TextUtils.isEmpty(edittext_login_username.text.toString())) return
        if (TextUtils.isEmpty(edittext_login_password.text.toString())) return
        PrefUtils.putString(this,"name",edittext_login_username.text.toString())
        PrefUtils.putString(this,"password",edittext_login_password.text.toString())
        val authorization = OAuthUtils.getBasicAuthorizationHeader(Contants.CLIENT_ID, Contants.CLIENT_SECRET)
        subscribe = ApiManager.getLoginApi().getLoginResponseData(authorization, edittext_login_username.text.toString(), edittext_login_password.text.toString(), "password")
                .compose(RxJavaUtils.normalSchedulers<LoginResponseData>())
                .subscribe({loginResponseData->
                    PrefUtils.putString(this, "access_token", loginResponseData.refresh_token)
                    MainActivity.startActivity(this)
                    finish()
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
