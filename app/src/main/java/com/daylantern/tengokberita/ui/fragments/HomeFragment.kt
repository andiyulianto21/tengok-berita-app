package com.daylantern.tengokberita.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.daylantern.tengokberita.R
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.adapters.HeaderFooterAdapter
import com.daylantern.tengokberita.databinding.FragmentHomeBinding
import com.daylantern.tengokberita.adapters.RvPagingAdapterHome
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.viewmodels.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), Listener, HeaderFooterAdapter.Listener {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val binding: FragmentHomeBinding by viewBinding()

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: RvPagingAdapterHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.visibility = View.VISIBLE

//        homeAdapter = RvAdapterHome()
        setupRv()
//        viewModel.loading.observe(viewLifecycleOwner) {
//             if(it) {
//                 binding.pbLoadingHome.visibility = View.VISIBLE
//                 Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
//             } else{
//                 binding.pbLoadingHome.visibility = View.GONE
//                 Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
//             }
//        }

//        getArticles(Constants.CHIP_TOP_HEADLINES)
//        setupChipGroup()
    }

//    private fun setupChipGroup() {
//        binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->
//
//            val chip: Chip = binding.chipGroup.findViewById(group.checkedChipId)
//                when(chip.id) {
//                    R.id.chip_topHeadlines -> {
//                        viewModel.saveChipSelected(Constants.CHIP_TOP_HEADLINES)
//                        getArticles(Constants.CHIP_TOP_HEADLINES)
//                    }
//                    R.id.chip_business -> {
//                        viewModel.saveChipSelected(Constants.CHIP_BUSINESS)
//                        getArticles(Constants.CHIP_BUSINESS)
//                    }
//                    R.id.chip_entertainment -> {
//                        viewModel.saveChipSelected(Constants.CHIP_ENTERTAINMENT)
//                        getArticles(Constants.CHIP_ENTERTAINMENT)
//                    }
//                    R.id.chip_technology -> {
//                        viewModel.saveChipSelected(Constants.CHIP_TECHNOLOGY)
//                        getArticles(Constants.CHIP_TECHNOLOGY)
//                    }
//            }
//
//            chip.let {
//                Toast.makeText(requireContext(), it.text, Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }

//    private fun getArticles(chip: String) {
//        viewModel.getTopHeadlines(chip, "id")
//        viewModel.topHeadlines.observe(viewLifecycleOwner) {
//            homeAdapter.setList(it)
//        }
//        homeAdapter.listener = this
//    }

    private fun setupRv() {
        homeAdapter = RvPagingAdapterHome()
        binding.rvTopHeadlines.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter.withLoadStateHeaderAndFooter(
                header = HeaderFooterAdapter(homeAdapter, this@HomeFragment),
                footer = HeaderFooterAdapter(homeAdapter, this@HomeFragment)
            )
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            homeAdapter.retry()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.articlesList.collect {
                homeAdapter.submitData(it)
            }
        }
        homeAdapter.listener = this
        homeAdapter.addLoadStateListener {
            if(it.refresh == LoadState.Loading) {
                binding.pbLoadingHome.visibility = View.VISIBLE
            }else {
                binding.pbLoadingHome.visibility = View.GONE
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.searchFragment) {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            true
        } else false
    }

    override fun onClick(article: Article) {
        val action = HomeFragmentDirections.actionGlobalArticleSelectedFragment(SavedArticle(
            urlToImage = article.urlToImage,
            url = article.url,
            title = article.title,
            publishedAt = article.publishedAt,
            savedAt = null
        ))
        findNavController().navigate(action)
    }

    override fun onClick(adapter: RvPagingAdapterHome) {
        adapter.retry()
    }
}