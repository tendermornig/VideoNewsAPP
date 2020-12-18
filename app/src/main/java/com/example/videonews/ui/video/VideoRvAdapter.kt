package com.example.videonews.ui.video

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dueeeke.videocontroller.component.PrepareView
import com.example.videonews.R
import com.example.videonews.logic.model.VideoModel
import com.example.videonews.ui.video.VideoRvAdapter.ViewHolder

class VideoRvAdapter(
    private val values: List<VideoModel>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    var lastSize = 0
    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_list_item, parent, false)
        val holder = ViewHolder(itemView)
        onItemClickListener?.let { l ->
            holder.pv.setOnClickListener {
                l(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = values[position]
        Glide.with(context).load(value.headurl).apply(RequestOptions.circleCropTransform())
            .into(holder.ivHead)
        holder.tvTitle.text = value.vtitle
        holder.tvAuthor.text = value.author
        Glide.with(context).load(value.coverUrl).into(holder.mThumb)
        holder.tvComment.text = "${value.commentNum}"
        holder.ivCollect.setImageResource(R.mipmap.collect)
        holder.ivLikes.setImageResource(R.mipmap.likes)
        holder.tvCollect.text = "${value.collectNum}"
        holder.tvLikes.text = "${value.likeNum}"
    }

    override fun getItemCount(): Int = values.size

    fun setOnItemClickListener(onItemClickListener: (i: Int) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.tag = this
        }

        val ivHead: ImageView = itemView.findViewById(R.id.ivHead)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val flPlayerContainer: FrameLayout = itemView.findViewById(R.id.flPlayerContainer)
        val pv: PrepareView = itemView.findViewById(R.id.pv)
        val mThumb: ImageView = pv.findViewById(R.id.thumb)
        val tvComment: TextView = itemView.findViewById(R.id.tvComment)
        val ivCollect: ImageView = itemView.findViewById(R.id.ivCollect)
        val tvCollect: TextView = itemView.findViewById(R.id.tvCollect)
        val ivLikes: ImageView = itemView.findViewById(R.id.ivLikes)
        val tvLikes: TextView = itemView.findViewById(R.id.tvLikes)
    }
}