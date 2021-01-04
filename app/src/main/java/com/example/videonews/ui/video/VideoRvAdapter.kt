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
import com.example.videonews.logic.model.VideoModel
import com.example.videonews.ui.video.VideoRvAdapter.ViewHolder

class VideoRvAdapter(
    private val values: List<VideoModel>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    var lastSize = 0
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

    fun setOnItemClickListener(onItemClickListener: (i: Int) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(val itemBinding: ItemVideoBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.tag = itemBinding
        }

        val mThumb: ImageView = itemBinding.pv.findViewById(R.id.thumb)
    }
}