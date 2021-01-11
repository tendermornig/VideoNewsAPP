package com.example.videonews.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.videonews.databinding.ItemNews1Binding
import com.example.videonews.databinding.ItemNews2Binding
import com.example.videonews.databinding.ItemNews3Binding
import com.example.videonews.logic.model.News
import com.example.videonews.logic.model.Thumb

/**
 * @author Miracle
 * 资讯列表的适配器
 */
class NewsRvAdapter(
    private val values: ArrayList<News>,
    private val mFragment: NewsFragment
) : RecyclerView.Adapter<NewsRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            1 -> ViewHolder(ItemNews1Binding.inflate(LayoutInflater.from(parent.context)))
            2 -> ViewHolder(ItemNews2Binding.inflate(LayoutInflater.from(parent.context)))
            3 -> ViewHolder(ItemNews3Binding.inflate(LayoutInflater.from(parent.context)))
            else -> throw IllegalArgumentException("viewType is Unknown")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = values[position]
        val viewModel = NewsRvViewModel(value.newsId)
        var onRequestFinish: (List<Thumb>) -> Unit = {}
        with(holder.binding) {
            when(this) {
                is ItemNews2Binding -> {
                    onRequestFinish = {
                        Glide.with(mFragment).load(it[0].thumbUrl).into(imgThumb1)
                        Glide.with(mFragment).load(it[1].thumbUrl).into(imgThumb2)
                        Glide.with(mFragment).load(it[2].thumbUrl).into(imgThumb3)
                        Glide.with(mFragment).load(value.headerUrl).into(imgHead)
                        tvTitle.text = value.newsTitle
                        tvAuthor.text = value.authorName
                        tvReleaseDate.text = value.releaseDate
                    }
                }
                is ItemNews1Binding -> {
                    onRequestFinish = {
                        Glide.with(mFragment).load(it[0].thumbUrl).into(imgThumb)
                        Glide.with(mFragment).load(value.headerUrl).into(imgHead)
                        tvTitle.text = value.newsTitle
                        tvAuthor.text = value.authorName
                        tvReleaseDate.text = value.releaseDate
                    }
                }
                is ItemNews3Binding -> {
                    onRequestFinish = {
                        Glide.with(mFragment).load(it[0].thumbUrl).into(imgThumb)
                        Glide.with(mFragment).load(value.headerUrl).into(imgHead)
                        tvTitle.text = value.newsTitle
                        tvAuthor.text = value.authorName
                        tvReleaseDate.text = value.releaseDate
                    }
                }
            }
            mFragment.setLiveDataStatus(viewModel.dataLiveData, onRequestFinish)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return values[position].type
    }

    override fun getItemCount() = values.size

    class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
}