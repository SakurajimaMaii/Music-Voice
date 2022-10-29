package cn.govast.gmusic.ui.view


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference: https://www.jb51.net/article/235251.html

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.FloatRange
import cn.govast.gmusic.R
import cn.govast.vasttools.utils.LogUtils
import cn.govast.vasttools.utils.ResUtils
import kotlin.math.ceil

class HorizontalProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private lateinit var mPaint: Paint
    private lateinit var mPaintRoundRect: Paint
    private lateinit var mPaintText: Paint
    private var mWidth = 0
    private var mHeight = 0
    private var padding = 5
    private var strokeWidth = 8
    private var textSize = 15
    private var progress: Float = 0f
    private var round = 0
    private var process = 0f

    //初始化画笔
    private fun initPaint() {
        //圆角矩形
        mPaintRoundRect = Paint().also {
            it.color = context.getColor(R.color.colorPrimaryContainer) //圆角矩形颜色
            it.isAntiAlias = true // 抗锯齿效果
            it.style = Paint.Style.STROKE //设置画笔样式
            it.strokeWidth = strokeWidth.toFloat() //设置画笔宽度
        }
        mPaint = Paint().also {
            it.color = context.getColor(R.color.colorPrimary)
            it.style = Paint.Style.FILL_AND_STROKE
            it.isAntiAlias = true
            it.strokeWidth = strokeWidth.toFloat()
        }
        mPaintText = Paint().also {
            it.isAntiAlias = true
            it.style = Paint.Style.FILL
            it.color = context.getColor(R.color.colorPrimary)
            it.textSize = textSize.toFloat()
        }
    }

    fun setPadding(padding: Int) {
        this.padding = padding
    }

    fun setStrokeWidth(strokeWidth: Int) {
        this.strokeWidth = strokeWidth
    }

    fun setTextSize(textSize: Int) {
        this.textSize = textSize
    }

    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        this.progress = progress
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        //MeasureSpec.EXACTLY，精确尺寸
        mWidth = if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            widthSpecSize
        } else {
            0
        }
        //MeasureSpec.AT_MOST，最大尺寸，只要不超过父控件允许的最大尺寸即可，MeasureSpec.UNSPECIFIED未指定尺寸
        mHeight =
            if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
                defaultHeight()
            } else {
                heightSpecSize
            }
        //设置控件实际大小
        round = mHeight / 2 //圆角半径
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPaint()
        drawBackground(canvas) //绘制背景矩形
        drawProgress(canvas) //绘制进度
        // updateText(canvas) //处理文字
    }

    private fun drawBackground(canvas: Canvas) {
        val rectF = RectF(
            padding.toFloat(),
            padding.toFloat(),
            (mWidth - padding).toFloat(),
            (mHeight - padding).toFloat()
        ) //圆角矩形
        canvas.drawRoundRect(rectF, round.toFloat(), round.toFloat(), mPaintRoundRect)
    }

    private fun drawProgress(canvas: Canvas) {
        if (process != 0f) {
            val rectProgress = RectF(
                (padding + strokeWidth).toFloat(),
                (padding + strokeWidth).toFloat(),
                process * (mWidth - padding - strokeWidth),
                (mHeight - padding - strokeWidth).toFloat()
            ) //内部进度条
            LogUtils.d("1111111",(process * (mWidth - padding - strokeWidth)).toString())
            canvas.drawRoundRect(rectProgress, round.toFloat(), round.toFloat(), mPaint)
        }
    }

    private fun updateText(canvas: Canvas) {
        val finishedText = ResUtils.getString(R.string.finished)
        val defaultText = ResUtils.getString(R.string.defaultText)
        val percent = (process / (mWidth - padding - strokeWidth) * 100).toInt()
        val fm = mPaintText!!.fontMetrics
        val mTxtWidth = mPaintText!!.measureText(finishedText, 0, defaultText.length).toInt()
        val mTxtHeight = ceil((fm.descent - fm.ascent).toDouble()).toInt()
        val x = width / 2 - mTxtWidth / 2 //文字在画布中的x坐标
        val y = height / 2 + mTxtHeight / 4 //文字在画布中的y坐标
        if (percent < 100) {
            canvas.drawText("$percent%", x.toFloat(), y.toFloat(), mPaintText)
        } else {
            canvas.drawText(finishedText, x.toFloat(), y.toFloat(), mPaintText)
        }
    }

    //进度条默认高度，未设置高度时使用
    private fun defaultHeight(): Int {
        val scale = context.resources.displayMetrics.density
        return (20 * scale + 0.5f * if (20 >= 0) 1 else -1).toInt()
    }
}