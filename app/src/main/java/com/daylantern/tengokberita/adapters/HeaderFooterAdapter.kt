package com.daylantern.tengokberita.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daylantern.tengokberita.databinding.LoadingStateBinding

class HeaderFooterAdapter<T>(
    private val adapter: T,
    private val onItemClicked: (T) -> Unit
): LoadStateAdapter<HeaderFooterAdapter<T>.ViewHolder>() {
    inner class ViewHolder(private val binding: LoadingStateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: T) {
            binding.btnRetryConn.isVisible = loadState is LoadState.Error
            binding.btnRetryConn.setOnClickListener { onItemClicked(adapter) }
            binding.pbLoadingState.visibility = View.VISIBLE
            binding.textView.text = (loadState as? LoadState.Error)?.error?.message
            binding.textView.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(LoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(adapter)
    }
}