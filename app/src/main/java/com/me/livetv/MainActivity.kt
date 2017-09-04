package com.me.livetv

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.me.livetv.domain.LiveList
import com.me.livetv.utils.RxJavaUtils
import kotlinx.android.synthetic.main.activity_main.*
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {

    var lessons: LiveList? = null
    var url: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        list.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        list.isFocusable = false
        loadData()
        initIjk()

        list.requestFocus()
    }


    private fun loadData() {
        ApiManager.getLiveListApi().loginResponseData
                .compose(RxJavaUtils.normalSchedulers<LiveList>())
                .subscribe({ lessons ->
                    this.lessons = lessons

                    //ijk.setVideoPath("http://hd.yinyuetai.com/uploads/videos/common/1B43015E3E1C901EACC99453AE8A12C1.mp4?sc=b15ed059e02aa94d")
                    url = lessons.lessons[0].playurl
                    ijk.setVideoPath("rtsp://192.168.1.170:8554/mv")
                    ijk.start()

                    list.adapter = object : BaseQuickAdapter<LiveList.LessonsEntity>(R.layout.item_live, lessons.lessons) {
                        override fun convert(p0: BaseViewHolder?, p1: LiveList.LessonsEntity?) {
                            if (p0?.layoutPosition == currentFocusPosition){
                                p0?.itemView?.requestFocus()
                            }
                            p0?.setText(R.id.lesson_name, p1?.courseName)
                            p0?.itemView?.setOnClickListener {
                                //ijk.setVideoPath(p1?.playurl)
                                loading.visibility = View.VISIBLE
                                ijk.visibility = View.GONE
                                url = p1?.playurl
                                ijk.setVideoPath("rtsp://192.168.1.170:8554/mv")
                                ijk.start()
                            }
                            p0?.itemView?.setOnFocusChangeListener { v, hasFocus ->
                                if (hasFocus){
                                    currentFocusPosition = p0?.layoutPosition
                                    currentFocusView = v
                                }
                            }
                        }

                    }
                }, {})
    }

    private var isMenuShow: Boolean = true
    private var currentFocusPosition: Int = 0
    private var currentFocusView: View? = null
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (isMenuShow){
                    isMenuShow = false
                    startOut()
                }
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                if (!isMenuShow){
                    isMenuShow = true
                    startIn()
                }
                return true
            }
            KeyEvent.KEYCODE_BACK -> {
                mBackPressed = true
                exit()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun exit() {
        AlertDialog.Builder(this).setCancelable(false).setMessage("确定退出？").setNegativeButton("取消") { dialog, which -> dialog.dismiss()}
                .setPositiveButton("确定",{
                    dialog,which->
                    dialog.dismiss()
                    finish()
                })
                .create().show()

    }

    fun startIn() {
        val ofInt = ValueAnimator.ofFloat((-list.measuredWidth).toFloat(), 0f)
        ofInt.duration = 600
        ofInt.addUpdateListener({
            animation ->
            list.translationX = animation.animatedValue as Float
        })
        ofInt.start()
    }

    fun startOut() {
        val ofInt = ValueAnimator.ofFloat(0f, (-list.measuredWidth).toFloat())
        ofInt.duration = 600
        ofInt.addUpdateListener({
            animation ->
            list.translationX = animation.animatedValue as Float
        })
        ofInt.start()
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun initIjk() {
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")


        ijk.setOnInfoListener({ mp, what, extra ->
            when (what) {
                IMediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                    loading.visibility = View.VISIBLE
                }
                IMediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                    loading.visibility = View.GONE
                    ijk.start()
                }
                IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                    loading.visibility = View.GONE
                    ijk.visibility = View.VISIBLE
                }
            }
            true
        })
        ijk.setOnPreparedListener({
            ijk.start()
        })
        ijk.setOnErrorListener({ mp, what, extra ->
            val create = AlertDialog.Builder(this).setCancelable(false).setMessage("加载失败？").setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                    .setPositiveButton("重试", {
                        dialog, which ->
                        dialog.dismiss()
                        ijk.setVideoPath(url)
                        ijk.start()
                    })
                    .create()
            create.show()
            create.setOnDismissListener {
                currentFocusView?.requestFocus()
            }
            true
        })
        ijk.setOnCompletionListener({

        })


    }

    private var mBackPressed: Boolean = false
    override fun onDestroy() {
        super.onDestroy()
        if (mBackPressed || !ijk.isBackgroundPlayEnabled) {
            ijk.stopPlayback()
            ijk.release(true)
            ijk.stopBackgroundPlay()
        } else {
            ijk.enterBackground()
        }
        IjkMediaPlayer.native_profileEnd()
    }
}
