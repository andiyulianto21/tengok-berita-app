package com.daylantern.tengokberita.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daylantern.tengokberita.databinding.CardItemArticleBinding
import com.daylantern.tengokberita.network.Article

class RvAdapterHome: RecyclerView.Adapter<RvAdapterHome.ViewHolder>() {

    private var articles = listOf<Article>()
    var listener: Listener? = null

    fun setList(list: List<Article>) {
        articles = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CardItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article){
            binding.apply {
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
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    interface Listener {
        fun onClick(article: Article)
    }
}