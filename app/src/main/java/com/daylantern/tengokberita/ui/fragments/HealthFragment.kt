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
import com.daylantern.tengokberita.adapters.HeaderFooterAdapter
import com.daylantern.tengokberita.adapters.RvPagingAdapterHealth
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.databinding.FragmentHealthBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.viewmodels.HealthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HealthFragment : Fragment(R.layout.fragment_health), Listener {

    private val binding: FragmentHealthBinding by viewBinding()
    private val healthAdapter by lazy { RvPagingAdapterHealth() }
    private val viewModel: HealthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                healthAdapter.retry()
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                },1000)
            }
        }
    }

    private fun setupRv() {
        healthAdapter.listener = this
        binding.rvHealth.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = healthAdapter
                .withLoadStateHeaderAndFooter(
                header = HeaderFooterAdapter(healthAdapter) { it.retry() },
                footer = HeaderFooterAdapter(healthAdapter) { it.retry() }
            )
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.list.collect {
                healthAdapter.submitData(it)
            }
        }
    }

    override fun onClick(article: Article) {
        findNavController().navigate(HealthFragmentDirections.actionGlobalArticleSelectedFragment(
            SavedArticle(article.url, article.title, article.urlToImage, article.publishedAt, savedAt = null)
        ))
    }
}