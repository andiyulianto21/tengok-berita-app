package com.daylantern.tengokberita.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daylantern.tengokberita.R
import com.daylantern.tengokberita.TopHeadlinesFragmentDirections
import com.daylantern.tengokberita.adapters.HeaderFooterAdapter
import com.daylantern.tengokberita.adapters.RvPagingAdapterTopHeadlines
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.databinding.FragmentTopHeadlinesBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.viewmodels.TopHeadlinesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopHeadlinesFragment : Fragment(R.layout.fragment_top_headlines), Listener {

    private val binding: FragmentTopHeadlinesBinding by viewBinding()
    private val topHeadlinesAdapter by lazy { RvPagingAdapterTopHeadlines() }
    private val viewModel: TopHeadlinesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                topHeadlinesAdapter.retry()
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                },1000)
            }
        }
    }

    private fun setupRv() {
        topHeadlinesAdapter.listener = this
        binding.rvTopHeadlines.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topHeadlinesAdapter.withLoadStateHeaderAndFooter(
                header = HeaderFooterAdapter(topHeadlinesAdapter) { it.retry() },
                footer = HeaderFooterAdapter(topHeadlinesAdapter) { it.retry() }
            )
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.articlesList.collect {
                topHeadlinesAdapter.submitData(it)
            }
        }
    }

    override fun onClick(article: Article) {
        findNavController().navigate(
            TopHeadlinesFragmentDirections.actionGlobalArticleSelectedFragment(
            SavedArticle(article.url, article.title, article.urlToImage, article.publishedAt, savedAt = null)
        ))
    }
}