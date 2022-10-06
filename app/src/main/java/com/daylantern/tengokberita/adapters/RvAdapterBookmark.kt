package com.daylantern.tengokberita.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.databinding.CardItemArticleBinding
import com.daylantern.tengokberita.util.ConvertDateTime
import com.daylantern.tengokberita.util.ConvertDateTime.toTimeAgo

class RvAdapterBookmark: RecyclerView.Adapter<RvAdapterBookmark.ViewHolder>() {

    private var articles = emptyList<SavedArticle>()
    var listener: RvAdapterBookmark.Listener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<SavedArticle>) {
        articles = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CardItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: SavedArticle) {
            binding.apply {
                Glide.with(itemView).load(article.urlToImage).into(imgThumbnail)
                tvTitle.text = article.title
                val convert = article.publishedAt?.let { ConvertDateTime.convertToLong(it) }
                val result = convert?.toTimeAgo()
                tvPublishedAt.text = "$result • "
                itemView.setOnClickListener { listener?.onClick(article) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    interface Listener {
        fun onClick(article: SavedArticle)
    }
}