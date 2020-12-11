package com.example.videonews.ui.video

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.videonews.R
import com.example.videonews.logic.model.VideoModel
import com.example.videonews.ui.video.VideoRvAdapter.ViewHolder

class VideoRvAdapter(
    private val values: List<VideoModel>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = values[position]
        Glide.with(context).load(value.headurl).apply(RequestOptions.circleCropTransform())
            .into(holder.ivHead)
        holder.tvTitle.text = value.vtitle
        holder.tvAuthor.text = value.author
        Glide.with(context).load(value.coverUrl).into(holder.ivVideoCover)
        holder.tvComment.text = "${value.commentNum}"
        holder.ivCollect.setImageResource(R.mipmap.collect)
        holder.ivLikes.setImageResource(R.mipmap.likes)
        holder.tvCollect.text = "${value.collectNum}"
        holder.tvLikes.text = "${value.likeNum}"
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivHead: ImageView = view.findViewById(R.id.ivHead)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val ivVideoCover: ImageView = view.findViewById(R.id.ivVideoCover)
        val tvComment: TextView = view.findViewById(R.id.tvComment)
        val ivCollect: ImageView = view.findViewById(R.id.ivCollect)
        val tvCollect: TextView = view.findViewById(R.id.tvCollect)
        val ivLikes: ImageView = view.findViewById(R.id.ivLikes)
        val tvLikes: TextView = view.findViewById(R.id.tvLikes)
    }
}