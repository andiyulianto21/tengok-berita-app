package com.daylantern.tengokberita.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daylantern.tengokberita.databinding.CardItemArticleBinding
import com.daylantern.tengokberita.network.Article

class RvPagingAdapterSearch: PagingDataAdapter<Article, RvPagingAdapterSearch.ViewHolder>(DiffUtilCallback) {

    var listener: Listener? = null

    object DiffUtilCallback: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url

        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(private val binding: CardItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                tvTitle.text = article.title
                tvPublishedAt.text = article.publishedAt
                Glide.with(itemView).load(article.urlToImage).into(imgThumbnail)
                itemView.setOnClickListener { listener?.onClick(article) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    interface Listener {
        fun onClick(article: Article)
    }
}