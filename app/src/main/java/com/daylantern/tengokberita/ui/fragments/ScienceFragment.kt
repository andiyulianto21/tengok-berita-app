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
import com.daylantern.tengokberita.adapters.RvPagingAdapterScience
import com.daylantern.tengokberita.databinding.FragmentScienceBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.util.ConvertClass
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.viewmodels.ScienceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScienceFragment : Fragment(R.layout.fragment_science), Listener {

    private val binding: FragmentScienceBinding by viewBinding()
    private val scienceAdapter by lazy { RvPagingAdapterScience() }
    private val viewModel: ScienceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                scienceAdapter.retry()
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                },1000)
            }
        }
    }

    private fun setupRv() {
        scienceAdapter.listener = this
        binding.rvScience.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scienceAdapter.withLoadStateFooter(HeaderFooterAdapter(scienceAdapter){
                it.retry()
            })
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.list.collect() {
                scienceAdapter.submitData(it)
            }
        }
    }

    override fun onClick(article: Article) {
        findNavController().navigate(
            TechnologyFragmentDirections.actionGlobalArticleSelectedFragment(ConvertClass.toSavedArticle(article))
        )
    }
}