package com.example.gmusic

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmusic.DataBindingAdapter.SetOnItemClickListener
import com.example.gmusic.databinding.ActivityMainBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author HP
 */
class MainActivity : AppCompatActivity(){
    /**
     * 获取binding对象
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * 搜索到的数据
     */
    var searchResultMusic: List<BindingAdapterItem> = ArrayList()

    /**
     * 做个数据缓存
     */
    private val cacheMusicData: MutableList<BindingAdapterItem> = ArrayList()

    /**
     * 数据源
     */
    var mData: MutableList<BindingAdapterItem> = ArrayList()
    private var adapter: DataBindingAdapter? = null

    /**
     * 记录当前正在播放的音乐的位置
     */
    var currentPlayPosition = -1

    /**
     * 记录暂停音乐时进度条的位置
     */
    var currentPausePositionInSong = 0

    /**
     * 创建MediaPlayer对象
     */
    var mediaPlayer: MediaPlayer? = null

    /**
     * 判断是不是首次启动 默认值为false
     */
    var isFirstRun = false

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //加载布局
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        //初始化mediaPlayer
        mediaPlayer = MediaPlayer()
        mData = ArrayList()
        //创建适配器对象
        adapter = DataBindingAdapter(mData)
        adapter!!.setOnItemClickListener(object : SetOnItemClickListener{
            override fun onItemClick(position: Int) {
                currentPlayPosition = position
                val localMusicBean = mData[position] as LocalMusicBean
                //设置item
                binding.setItem(localMusicBean)
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
            val lastBean = mData[currentPlayPosition] as LocalMusicBean
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
            val nextBean = mData[currentPlayPosition] as LocalMusicBean
            binding.item = nextBean
            playMusicInMusicBean(nextBean)
        }
    }

    private fun playMusicInMusicBean(musicBean: LocalMusicBean) {
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

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private fun loadLocalMusicData() {
        var cursor: Cursor? = null
        try {
            //加载本地文件到集合中
            //获取ContentResolver对象
            val resolver = contentResolver
            //获取本地存储的Uri地址
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            //开始查询地址
            cursor = resolver.query(uri, null, null, null, null)
            //遍历cursor
            var id = 0
            while (cursor!!.moveToNext()) {
                //设立条件过滤掉一部分歌曲
                if (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)) != "<unknown>" &&
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)) != "<unknown>"
                ) {
                    val song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    @SuppressLint("InlinedApi") val singer =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    @SuppressLint("InlinedApi") val album =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    id++
                    val sid = id.toString()
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val duration =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("mm:ss")
                    val time = sdf.format(Date(duration))
                    val mp = getAlbumArt(path)
                    //将一行当中的对象封装到数据当中
                    val bean = LocalMusicBean(sid, song, singer, album, time, path, mp)
                    mData.add(bean)
                }
            }
            //做数据缓存，提升效率
            cacheMusicData.addAll(mData)
            //数据源变化，提示适配器更新
            adapter!!.notifyDataSetChanged()
            cursor.close()
        } finally {
            cursor!!.close()
        }
    }

    private fun getAlbumArt(albumPath: String): Bitmap {
        //歌曲检索
        val mmr = MediaMetadataRetriever()
        //设置数据源
        mmr.setDataSource(albumPath)
        //获取图片数据
        val data = mmr.embeddedPicture
        val albumPicture: Bitmap
        albumPicture = if (data != null) {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.earphone)
        }
        return albumPicture
    }

    private fun initView() {
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String): Boolean {
                searchResultMusic = SearchSong.searchSongByName(mData, query)
                mData.clear()
                mData.addAll(searchResultMusic)
                adapter!!.notifyDataSetChanged()
                return false
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onQueryTextChange(newText: String): Boolean {
                mData.clear()
                if (cacheMusicData.size < 1) {
                    loadLocalMusicData()
                    return false
                }
                mData!!.addAll(cacheMusicData)
                adapter!!.notifyDataSetChanged()
                return false
            }
        })
    }
}