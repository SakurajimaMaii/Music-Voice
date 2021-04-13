package com.example.gmusic

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmusic.databinding.MainActivityBinding
import com.gcode.gutils.adapter.BaseBindingAdapter
import com.gcode.gutils.adapter.BaseItem
import com.gcode.gutils.utils.MsgWindowUtils
import com.permissionx.guolindev.PermissionX
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    inner class MusicBindingAdapter(items: MutableList<BaseItem>) : BaseBindingAdapter(items) {
        override fun setVariableId(): Int {
            return BR.item
        }
    }

    /**
     * 获取binding对象
     */
    private lateinit var binding: MainActivityBinding

    /**
     * 搜索到的数据
     */
    var searchResultMusic: List<BaseItem> = ArrayList()

    /**
     * 做个数据缓存
     */
    private val cacheMusicData: MutableList<BaseItem> = ArrayList()

    /**
     * 数据源
     */
    var mData: MutableList<BaseItem> = ArrayList()
    private var adapter: MusicBindingAdapter? = null

    /**
     * 记录当前正在播放的音乐的位置
     */
    var currentPlayPosition = -1

    /**
     * 记录暂停音乐时进度条的位置
     */
    private var currentPausePositionInSong = 0

    /**
     * 创建MediaPlayer对象
     */
    private var mediaPlayer: MediaPlayer? = null

    /**
     * 判断是不是首次启动 默认值为false
     */
    private var isFirstRun = false

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //申请权限
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        Log.i(this.localClassName, "All permissions are granted")
                    } else {
                        Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_SHORT).show()
                    }
                }

        //加载布局
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        initView()
        //初始化mediaPlayer
        mediaPlayer = MediaPlayer()
        mData = ArrayList()
        //创建适配器对象
        adapter = MusicBindingAdapter(mData)
        adapter!!.setOnItemClickListener(object : BaseBindingAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, pos: Int) {
                currentPlayPosition = pos
                val localMusicBean = mData[pos] as MusicBean
                //设置item
                binding.item = localMusicBean
                binding.localMusicBottomTvAlbumArt.setImageBitmap(localMusicBean.albumArt)
                playMusicInMusicBean(localMusicBean)
            }
        })
        binding.localMusicRv.adapter = adapter
        //设置布局管理器
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.localMusicRv.layoutManager = layoutManager
        //加载本地数据源
        loadLocalMusicData()
        //设置每一项的点击事件
        binding.localMusicBottomIvLast.setOnClickListener {
            if (currentPlayPosition == 0) {
                Toast.makeText(this, "已经是第一首了，没有上一曲！", Toast.LENGTH_SHORT).show()
            }
            currentPlayPosition -= 1
            val lastBean = mData[currentPlayPosition] as MusicBean
            binding.item = lastBean
            playMusicInMusicBean(lastBean)
        }
        binding.localMusicBottomIvPlay.setOnClickListener {
            if (currentPlayPosition == -1) {
                //并没有选中要播放的音乐
                Toast.makeText(this, "请选择想要播放的音乐", Toast.LENGTH_SHORT).show()
            }
            if (mediaPlayer!!.isPlaying) {
                //此时处于播放状态，需要暂停音乐
                pauseMusic()
            } else {
                //此时没有播放音乐，点击开始播放音乐
                playMusic()
            }
        }
        binding.localMusicBottomIvNext.setOnClickListener {
            if (currentPlayPosition == mData.size - 1) {
                Toast.makeText(this, "已经是最后一首了，没有下一曲！", Toast.LENGTH_SHORT).show()
            }
            currentPlayPosition += 1
            val nextBean = mData[currentPlayPosition] as MusicBean
            binding.item = nextBean
            playMusicInMusicBean(nextBean)
        }

        binding.localMusicBottomLayout.setOnClickListener {
            startForResult.launch(Intent(this, MusicItemActivity::class.java))
        }
    }

    private fun playMusicInMusicBean(musicBean: MusicBean) {
        /*根据传入对象播放音乐*/
        //设置底部显示的歌手名称和歌曲名
        stopMusic()
        //重置多媒体播放器
        mediaPlayer!!.reset()
        //设置新的播放路径
        try {
            mediaPlayer!!.setDataSource(musicBean.path)
            playMusic()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun playMusic() {
        //播放音乐的函数
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
            if (currentPausePositionInSong == 0) {
                try {
                    //判断播放器是否被占用
                    Log.d("MainActivity", "playMusic()")
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                //从暂停到播放
                mediaPlayer!!.seekTo(currentPausePositionInSong)
                mediaPlayer!!.start()
            }
            binding.localMusicBottomIvPlay.setImageResource(R.drawable.pause)
        }
    }

    private fun pauseMusic() {
        /* 暂停音乐的函数*/
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            currentPausePositionInSong = mediaPlayer!!.currentPosition
            mediaPlayer!!.pause()
            binding.localMusicBottomIvPlay.setImageResource(R.drawable.play)
        }
    }

    /**
     * 停止音乐的函数
     * 在这里加入isFirstRun的主要用意如下:
     * 在首次启动软件时,如果第一次点击音乐,执行playMusicInMusicBean()时,进行到stopMusic()函数时,在不加isFirstRun判定条件的情况下,
     * 直接执行mediaPlayer.pause();会报错,诸如 E/MediaPlayerNative: pause called in state 1, mPlayer(0x0),那是因为不符合MediaPlayer生命周期,
     * 因而在加入isFirstRun条件,在初次运行时并不执行if条件句里面的内容,之后将isFirstRun设置为true
     */
    private fun stopMusic() {
        if (mediaPlayer != null && isFirstRun) {
            Log.d("MainActivity", "stopMusic()")
            currentPausePositionInSong = 0
            mediaPlayer!!.pause()
            mediaPlayer!!.seekTo(0)
            mediaPlayer!!.stop()
            binding.localMusicBottomIvPlay.setImageResource(R.drawable.play)
        } else {
            isFirstRun = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

    private fun loadLocalMusicData() {
        //加载本地文件到集合中
        //获取ContentResolver对象
        val resolver = contentResolver
        //获取本地存储的Uri地址
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        //开始查询地址
        val cursor: Cursor? = resolver.query(uri, null, null, null, null)
        //遍历cursor
        //设立条件过滤掉一部分歌曲
        when {
            cursor == null -> {
                Log.e(this.localClassName, "query failed, handle error.")
            }
            !cursor.moveToFirst() -> {
                MsgWindowUtils.showShortMsg(this, "设备上没有歌曲")
            }
            else -> {
                var id = 0
                do {
                    if (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)) != "<unknown>" &&
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)) != "<unknown>"
                    ) {
                        val song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                        var singer: String? = null
                        var album: String? = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                            album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                        } else {
                            Log.w(this.localClassName, "当前版本的手机不支持查找singer和album,MIN VERSION_CODE R")
                        }
                        id++
                        val sid = id.toString()
                        val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                        var duration: Long? = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                        } else {
                            Log.w(this.localClassName, "当前版本的手机不支持查找singer和album,MIN VERSION_CODE Q")
                        }
                        val mp = getAlbumArt(path)
                        //将一行当中的对象封装到数据当中
                        val bean = MusicBean(sid, song, singer, album, duration, path, mp)
                        mData.add(bean)
                    }
                } while (cursor.moveToNext())
            }
        }
        //做数据缓存，提升效率
        cacheMusicData.addAll(mData)
        //数据源变化，提示适配器更新
        adapter!!.notifyDataSetChanged()
        cursor?.close()
    }

    private fun getAlbumArt(albumPath: String): Bitmap {
        //歌曲检索
        val mmr = MediaMetadataRetriever()
        //设置数据源
        mmr.setDataSource(albumPath)
        //获取图片数据
        val data = mmr.embeddedPicture
        return if (data != null) {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.earphone)
        }
    }

    private fun initView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String): Boolean {
                searchResultMusic = SearchSong.searchSongByName(mData, query)
                mData.clear()
                mData.addAll(searchResultMusic)
                adapter!!.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mData.clear()
                if (cacheMusicData.size < 1) {
                    loadLocalMusicData()
                    return false
                }
                mData.addAll(cacheMusicData)
                adapter!!.notifyDataSetChanged()
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.main_toolbar_setting->{
                MsgWindowUtils.showShortMsg(this,"Hello")
            }
            
            R.id.main_toolbar_search->{
                if (binding.searchView.visibility == View.GONE){
                    binding.searchView.visibility = View.VISIBLE
                }else{
                    binding.searchView.visibility = View.GONE
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}