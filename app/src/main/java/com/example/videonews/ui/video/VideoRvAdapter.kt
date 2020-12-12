package com.example.videonews.ui.video

import android.content.Context
import android.util.Log
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
import com.example.videonews.listener.OnItemClickListener
import com.example.videonews.logic.model.VideoModel
import com.example.videonews.ui.video.VideoRvAdapter.ViewHolder
import kotlin.properties.Delegates

class VideoRvAdapter(
    private val values: ArrayList<VideoModel>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    var lastSize by Delegates.notNull<Int>()
    private var onItemClickListener: OnItemClickListener? = null

    init {
        lastSize = values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_list_item, parent, false)
        Log.d(TAG, "onCreateViewHolder: $view")
        val holder = ViewHolder(view)
        onItemClickListener?.let { l ->
            holder.flPlayerContainer.setOnClickListener {
                l.onItemClick(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = values[position]
        with(holder) {
            Glide.with(context).load(value.headurl).apply(RequestOptions.circleCropTransform())
                .into(ivHead)
            tvTitle.text = value.vtitle
            tvAuthor.text = value.author
            Glide.with(context).load(value.coverUrl).into(ivThumb)
            tvComment.text = "${value.commentNum}"
            ivCollect.setImageResource(R.mipmap.collect)
            ivLikes.setImageResource(R.mipmap.likes)
            tvCollect.text = "${value.collectNum}"
            tvLikes.text = "${value.likeNum}"
        }
    }

    override fun getItemCount(): Int = values.size

    fun setOnItemClickListener(block: (i: Int) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                block(position)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.tag = this
        }

        val ivHead: ImageView = view.findViewById(R.id.ivHead)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val flPlayerContainer: FrameLayout = view.findViewById(R.id.flPlayerContainer)
        val pvPrepare: PrepareView = view.findViewById(R.id.pvPrepare)
        val ivThumb: ImageView = pvPrepare.findViewById(R.id.thumb)
        val tvComment: TextView = view.findViewById(R.id.tvComment)
        val ivCollect: ImageView = view.findViewById(R.id.ivCollect)
        val tvCollect: TextView = view.findViewById(R.id.tvCollect)
        val ivLikes: ImageView = view.findViewById(R.id.ivLikes)
        val tvLikes: TextView = view.findViewById(R.id.tvLikes)
    }

    companion object {
        private const val TAG = "VideoRvAdapter"
    }
}