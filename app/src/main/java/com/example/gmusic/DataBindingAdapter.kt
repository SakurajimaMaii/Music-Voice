package com.example.gmusic

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gmusic.DataBindingAdapter.BindingHolder

/**
 * @author HP
 */
class DataBindingAdapter(var items: List<BindingAdapterItem>) :
    RecyclerView.Adapter<BindingHolder>() {
    private var onItemClickListener: SetOnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: SetOnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * @return 返回的是adapter的view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return BindingHolder(binding)
    }

    /**
     * 数据绑定
     * @param holder BindingHolder
     * @param position position
     */
    override fun onBindViewHolder(
        holder: BindingHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bindData(items[position])
        holder.itemView.setOnClickListener { onItemClickListener!!.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    class BindingHolder(var binding: ViewDataBinding) : ViewHolder(
        binding.root
    ) {
        fun bindData(item: BindingAdapterItem?) {
            binding.setVariable(BR.item, item)
        }
    }

    /**
     * 设置点击事件回调接口
     */
    interface SetOnItemClickListener {
        /**
         * RecyclerView Item点击事件
         * @param position int
         */
        fun onItemClick(position: Int)
    }
}