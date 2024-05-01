package com.AXX.newsapp.newsFrament

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.AXX.newsapp.API.model.newsResponse.Article
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter(var newsList:List<Article?>?):Adapter<NewsAdapter.NewsViewHolder>() {
    override fun getItemCount(): Int {
      return newsList?.size?:0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       holder.bind(newsList?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
      val itemBinding =ItemNewsBinding.inflate(
              LayoutInflater.from(parent.context),
              parent,
          false
              )
      return NewsViewHolder(itemBinding)
    }

    fun changeData(articles: List<Article?>?) {
        newsList =articles
        notifyDataSetChanged()
    }

    //assignment caching interceptor reteofit
    class NewsViewHolder(val itemBinding:ItemNewsBinding):ViewHolder(itemBinding.root) {
        fun bind(news: Article?) {
            itemBinding.title.text =news?.title
            itemBinding.author.text =news?.author
            itemBinding.date.text =news?.publishedAt
            Glide.with(itemView)
                .load(news?.urlToImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemBinding.image)
        }
    }
}