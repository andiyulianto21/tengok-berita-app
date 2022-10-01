package com.daylantern.tengokberita.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daylantern.tengokberita.databinding.LoadingStateBinding

class HeaderFooterAdapter(
    private val adapter: RvPagingAdapterHome,
    private val listener: Listener
): LoadStateAdapter<HeaderFooterAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: LoadingStateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(adapter: RvPagingAdapterHome) {
            binding.btnRetryConn.isVisible = loadState is LoadState.Error
            binding.btnRetryConn.setOnClickListener { listener.onClick(adapter) }
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

    interface Listener {
        fun onClick(adapter: RvPagingAdapterHome)
    }
}