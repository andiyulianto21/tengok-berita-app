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
import com.daylantern.tengokberita.adapters.RvPagingAdapterEntertainment
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.databinding.FragmentEntertainmentBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.viewmodels.EntertainmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntertainmentFragment : Fragment(R.layout.fragment_entertainment), Listener {

    private val binding: FragmentEntertainmentBinding by viewBinding()
    private val viewModel: EntertainmentViewModel by viewModels()
    private val entertainmentAdapter by lazy { RvPagingAdapterEntertainment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                entertainmentAdapter.retry()
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                },1000)
            }
        }
    }

    private fun setupRv() {
        entertainmentAdapter.listener = this
        binding.rvEntertainment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = entertainmentAdapter.withLoadStateFooter(HeaderFooterAdapter(entertainmentAdapter){
                it.retry()
            })
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.list.collect() {
                entertainmentAdapter.submitData(it)
            }
        }
    }

    override fun onClick(article: Article) {
        findNavController().navigate(EntertainmentFragmentDirections.actionGlobalArticleSelectedFragment(
            SavedArticle(article.url, article.title, article.urlToImage, article.publishedAt, savedAt = null)
        ))
    }

}