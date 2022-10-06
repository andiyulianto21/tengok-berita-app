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
import com.daylantern.tengokberita.adapters.RvPagingAdapterBusiness
import com.daylantern.tengokberita.databinding.FragmentBusinessBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.util.ConvertClass
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.viewmodels.BusinessViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BusinessFragment : Fragment(R.layout.fragment_business), Listener {

    private val binding: FragmentBusinessBinding by viewBinding()
    private val businessAdapter by lazy { RvPagingAdapterBusiness() }
    private val viewModel: BusinessViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                businessAdapter.retry()
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                },1000)
            }
        }
    }

    private fun setupRv() {
        businessAdapter.listener = this
        binding.rvBusiness.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = businessAdapter.withLoadStateFooter(HeaderFooterAdapter(businessAdapter) {
                it.retry()
            })
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.list.collect() {
                businessAdapter.submitData(it)
            }
        }
    }

    override fun onClick(article: Article) {
        findNavController().navigate(BusinessFragmentDirections.actionGlobalArticleSelectedFragment(ConvertClass.toSavedArticle(article)))
    }

}