package com.example.videonews.ui.video

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.videonews.R
import com.example.videonews.databinding.ItemVideoBinding
import com.example.videonews.logic.model.Video
import com.example.videonews.ui.video.VideoRvAdapter.ViewHolder

/**
 * @author Miracle
 * 视频列表适配器
 */
class VideoRvAdapter(
    private val values: List<Video>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    /**
     * 最后一次刷新时的列表大小
     */
    var lastSize = 0

    /**
     * 点击事件方法对象 用于给子项设置点击事件
     */
    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(itemBinding)
        onItemClickListener?.let { l ->
            itemBinding.pv.setOnClickListener {
                l(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = values[position]
        with(holder.itemBinding) {
            with(value) {
                Glide.with(context).load(headurl).apply(RequestOptions.circleCropTransform())
                    .into(ivHead)
                tvTitle.text = vtitle
                tvAuthor.text = author
                Glide.with(context).load(coverUrl).into(holder.mThumb)
                tvComment.text = "$commentNum"
                ivCollect.setImageResource(R.mipmap.collect)
                ivLikes.setImageResource(R.mipmap.likes)
                tvCollect.text = "$collectNum"
                tvLikes.text = "$likeNum"
            }
        }
    }

    override fun getItemCount(): Int = values.size

    /**
     * 设置子项点击事件
     * @param onItemClickListener 方法对象
     */
    fun setOnItemClickListener(onItemClickListener: (i: Int) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(val itemBinding: ItemVideoBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.tag = itemBinding
        }

        /**
         * 略缩图控件对象
         */
        val mThumb: ImageView = itemBinding.pv.findViewById(R.id.thumb)
    }
}