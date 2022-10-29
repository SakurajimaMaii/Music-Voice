package cn.govast.gmusic.ui.view


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/29
// Description: 
// Documentation:
// Reference: https://www.jb51.net/article/235251.html
// Reference: https://www.jianshu.com/p/868a7a4668ad

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import cn.govast.gmusic.R

class HorizontalProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mMaxNum = 100.0 //最大值
    private var mCurrentNum = 0.0 //当前的值
    private var mInLineColor = 0 //内线颜色
    private var mOutLineColor = 0 //外线颜色
    private var mInLineDrawable: Drawable? = null //内线图片
    private var mOutLineDrawable: Drawable? = null //外线图片
    private var mOutLineSize //外线 大小 单位sp
            : Int
    private var mWidth //宽
            = 0
    private var mHeight //高
            = 0

    //画笔
    private var mInPaint: Paint? = null
    private var mOutPaint: Paint? = null
    private val mPaint = Paint() //绘制图片

    init {


        // 获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressView)
        mCurrentNum =
            typedArray.getInteger(R.styleable.HorizontalProgressView_progress_line_progress, 0)
                .toDouble()
        mMaxNum = typedArray.getInteger(R.styleable.HorizontalProgressView_progress_line_max, 100)
            .toDouble()

        //颜色
        mInLineColor =
            typedArray.getColor(R.styleable.HorizontalProgressView_progress_line_inColor, 0)
        mOutLineColor =
            typedArray.getColor(R.styleable.HorizontalProgressView_progress_line_outColor, 0)

        //图片
        mInLineDrawable =
            typedArray.getDrawable(R.styleable.HorizontalProgressView_progress_line_inDrawable)
        mOutLineDrawable =
            typedArray.getDrawable(R.styleable.HorizontalProgressView_progress_line_outDrawable)

        //大小
        mOutLineSize = typedArray.getDimensionPixelSize(
            R.styleable.HorizontalProgressView_progress_line_outSize,
            0
        )
        typedArray.recycle()
        setInPaint()
        setOutPaint()
    }

    /**
     * 内线画笔
     */
    private fun setInPaint() {
        mInPaint = Paint()
        mInPaint!!.isAntiAlias = true
        mInPaint!!.color = mInLineColor
        mInPaint!!.strokeWidth = mHeight.toFloat() //大小
        mInPaint!!.strokeCap = Paint.Cap.ROUND // 结束位置圆角
    }

    /**
     * 外线画笔
     */
    private fun setOutPaint() {
        mOutPaint = Paint()
        mOutPaint!!.isAntiAlias = true
        mOutPaint!!.color = mOutLineColor
        mOutPaint!!.strokeWidth = mOutLineSize.toFloat() //大小
        mOutPaint!!.strokeCap = Paint.Cap.ROUND // 结束位置圆角
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //1. 获取宽
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) { //具体值
            mWidth = MeasureSpec.getSize(widthMeasureSpec)
        }
        //2.获取高
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) { //具体值
            mHeight = MeasureSpec.getSize(heightMeasureSpec)
        }
        if (mOutLineSize == 0) {
            mOutLineSize = mHeight
        }
        //2. 确定宽高
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //内层
        if (mInLineColor != 0) {
            drawInLine(canvas, 0, mWidth, mHeight, mInPaint) //画内线
        }
        if (mInLineDrawable != null) {
            val bitmap: Bitmap = (mInLineDrawable as BitmapDrawable).bitmap
            canvas.drawBitmap(bitmap, null, Rect(0, 0, mWidth, mHeight), mPaint)
        }

        //外层
        val left = (mHeight - mOutLineSize) / 2
        val width = ((mWidth - left) * (mCurrentNum / mMaxNum)).toInt()
        if (mOutLineColor != 0) {
            drawOutLine(canvas, left, width, mOutLineSize, mOutPaint) //画外线
        }
        if (mOutLineDrawable != null) {
            val bitmap: Bitmap = (mOutLineDrawable as BitmapDrawable).bitmap
            canvas.drawBitmap(
                bitmap,
                null,
                Rect(left, (mHeight - mOutLineSize) / 2, width, mOutLineSize),
                mPaint
            )
        }
    }

    private fun drawInLine(canvas: Canvas, left: Int, width: Int, height: Int, paint: Paint?) {
        val rectF = RectF(
            left.toFloat(),
            (mHeight - height).toFloat(),
            width.toFloat(),
            mHeight.toFloat()
        ) // 设置个新的长方形
        canvas.drawRoundRect(
            rectF,
            (height / 2).toFloat(),
            (height / 2).toFloat(),
            paint!!
        ) //第二个参数是x半径，第三个参数是y半径
    }

    /**
     * 进度前进方向为圆角
     */
    private fun drawOutLine(canvas: Canvas, left: Int, width: Int, height: Int, paint: Paint?) {
        val top = (mHeight - height) / 2
        if (width - left >= height) { //绘制圆角方式
            val rectF = RectF(
                left.toFloat(),
                top.toFloat(),
                width.toFloat(),
                (mHeight - top).toFloat()
            ) // 设置个新的长方形
            canvas.drawRoundRect(
                rectF,
                (height / 2).toFloat(),
                (height / 2).toFloat(),
                paint!!
            ) //第二个参数是x半径，第三个参数是y半径
        }
        //绘制前面圆
        val rectF = RectF(left.toFloat(), top.toFloat(), width.toFloat(), (mHeight - top).toFloat())
        canvas.clipRect(rectF)
        val r = height / 2
        canvas.drawCircle((left + r).toFloat(), (top + r).toFloat(), r.toFloat(), paint!!)
    }

    fun setMax(max: Double) {
        mMaxNum = max
        invalidate()
    }

    fun setCurrentNum(currentNum: Double) {
        mCurrentNum = currentNum
        if (mCurrentNum > mMaxNum) {
            mCurrentNum = mMaxNum
        }
        invalidate()
    }
}