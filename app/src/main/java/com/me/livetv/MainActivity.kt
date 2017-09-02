package com.me.livetv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.me.livetv.domain.LiveList
import com.me.livetv.utils.RxJavaUtils
import com.me.livetv.utils.ToastUtil
import com.open.androidtvwidget.bridge.RecyclerViewBridge
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV
import kotlinx.android.synthetic.main.activity_main.*
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {

    private var mRecyclerViewBridge: RecyclerViewBridge?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.layoutManager = LinearLayoutManager(this)
        list.isFocusable = false
        loadData()
        initIjk()
        mainUpView1.effectBridge = RecyclerViewBridge()
        // 注意这里，需要使用 RecyclerViewBridge 的移动边框 Bridge.
        mRecyclerViewBridge = mainUpView1.effectBridge as RecyclerViewBridge
        mRecyclerViewBridge?.setUpRectResource(R.drawable.test_rectangle)

        list.setOnItemListener(object : RecyclerViewTV.OnItemListener {
            override fun onItemPreSelected(parent: RecyclerViewTV, itemView: View, position: Int) {
                // 传入 itemView也可以, 自己保存的 oldView也可以.
                mRecyclerViewBridge?.setUnFocusView(itemView)
            }

            override fun onItemSelected(parent: RecyclerViewTV, itemView: View, position: Int) {
                mRecyclerViewBridge?.setFocusView(itemView, 1.0f)
                oldView = itemView
            }

            /**
             * 这里是调整开头和结尾的移动边框.
             */
            override fun onReviseFocusFollow(parent: RecyclerViewTV, itemView: View, position: Int) {
                mRecyclerViewBridge?.setFocusView(itemView, 1.0f)
                oldView = itemView
            }
        })
        list.setOnItemClickListener(RecyclerViewTV.OnItemClickListener { parent, itemView, position ->
            // 测试.
            mRecyclerViewBridge?.setFocusView(itemView, oldView, 1.0f)
            oldView = itemView
        })
    }

    private var oldView: View? = null


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
            list.requestFocus()
            true
        })
        ijk.setOnCompletionListener(IMediaPlayer.OnCompletionListener {  })


    }
}
