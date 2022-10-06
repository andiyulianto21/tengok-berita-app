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
import com.daylantern.tengokberita.databinding.FragmentTechnologyBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.util.ConvertClass
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.viewmodels.TechnologyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TechnologyFragment : Fragment(R.layout.fragment_technology), Listener {

    private val viewModel: TechnologyViewModel by viewModels()
    private val binding: FragmentTechnologyBinding by viewBinding()
    private val entertainmentAdapter by lazy {RvPagingAdapterEntertainment()}

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
        binding.rvTechnology.apply {
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
        findNavController().navigate(
            TechnologyFragmentDirections.actionGlobalArticleSelectedFragment(ConvertClass.toSavedArticle(article))
        )
    }
}