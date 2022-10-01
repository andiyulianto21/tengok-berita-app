package com.daylantern.tengokberita.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.databinding.CardItemArticleBinding
import com.daylantern.tengokberita.network.Article

class RvAdapterHome: RecyclerView.Adapter<RvAdapterHome.ViewHolder>() {

    private var articles = emptyList<Article>()
    var listener: Listener? = null

    fun setList(newList: List<Article>) {
//        val diffUtil = DiffUtilCallback(newList)
//        val diffResult = DiffUtil.calculateDiff(diffUtil)

        articles = newList
        notifyDataSetChanged()
//        diffResult.dispatchUpdatesTo(this)
    }

    inner class DiffUtilCallback(private val newList: List<Article>): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return articles.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return articles[oldItemPosition].url == newList[newItemPosition].url
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return articles[oldItemPosition] == newList[newItemPosition]
        }

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
}