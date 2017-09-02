package com.me.livetv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.me.livetv.domain.LiveList
import com.me.livetv.utils.RxJavaUtils
import com.me.livetv.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.layoutManager = LinearLayoutManager(this)
        loadData()
        initIjk()
    }

    private fun loadData() {
        ApiManager.getLiveListApi().loginResponseData
                .compose(RxJavaUtils.normalSchedulers<LiveList>())
                .subscribe({lessons->
                    list.adapter = object : BaseQuickAdapter<LiveList.LessonsEntity>(R.layout.item_live,lessons.lessons){
                        override fun convert(p0: BaseViewHolder?, p1: LiveList.LessonsEntity?) {
                            p0?.setText(R.id.lesson_name,p1?.courseName)
                            p0?.itemView?.setOnClickListener {
                                ijk.setVideoPath(p1?.playurl)
                                ijk.start()
                            }
                        }

                    }
                },{})
    }


    companion object{
        fun startActivity(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun initIjk() {
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")


        ijk.setOnInfoListener(IMediaPlayer.OnInfoListener { mp, what, extra ->
            when (what) {
                IMediaPlayer.MEDIA_INFO_BUFFERING_START ->{}
                IMediaPlayer.MEDIA_INFO_BUFFERING_END -> {

                }
                IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                }
            }
            true
        })
        ijk.setOnPreparedListener(IMediaPlayer.OnPreparedListener {

        })
        ijk.setOnErrorListener({ mp, what, extra ->
            ToastUtil.showMessage("视频加载失败")
            true
        })
        ijk.setOnCompletionListener(IMediaPlayer.OnCompletionListener {  })


    }
}
