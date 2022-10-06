package com.daylantern.tengokberita.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daylantern.tengokberita.*
import com.daylantern.tengokberita.util.Listener
import com.daylantern.tengokberita.adapters.PagerAdapter
import com.daylantern.tengokberita.databinding.FragmentHomeBinding
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.network.Article
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), Listener{

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val binding: FragmentHomeBinding by viewBinding()

    private val fragmentWithTitle =  mutableMapOf<String, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentWithTitle[getString(R.string.Headlines)] = TopHeadlinesFragment()
        fragmentWithTitle[getString(R.string.Health)] = HealthFragment()
        fragmentWithTitle[getString(R.string.Entertainment)] = EntertainmentFragment()
        fragmentWithTitle[getString(R.string.Business)] = BusinessFragment()
        fragmentWithTitle[getString(R.string.Technology)] = TechnologyFragment()
        fragmentWithTitle[getString(R.string.Science)] = ScienceFragment()

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.visibility = View.VISIBLE
//        setupRv()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val titles = fragmentWithTitle.keys.toList()
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun setupViewPager() {
        val fragments = ArrayList(fragmentWithTitle.values)
//        val viewPagerAdapter = ViewPagerAdapterHome()
        binding.viewPager.adapter = PagerAdapter(fragments, this)
    }

    private fun setupRv() {
//        homeAdapter = RvPagingAdapterHome()
//        binding.rvTopHeadlines.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = homeAdapter.withLoadStateHeaderAndFooter(
//                header = HeaderFooterAdapter(homeAdapter, this@HomeFragment),
//                footer = HeaderFooterAdapter(homeAdapter, this@HomeFragment)
//            )
//        }
//
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            homeAdapter.retry()
//            binding.swipeRefreshLayout.isRefreshing = false
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.articlesList.collect {
//                homeAdapter.submitData(it)
//            }
//        }
//        homeAdapter.listener = this
//        homeAdapter.addLoadStateListener {
//            if(it.refresh == LoadState.Loading) {
//                binding.pbLoadingHome.visibility = View.VISIBLE
//            }else {
//                binding.pbLoadingHome.visibility = View.GONE
//            }
//        }
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

//    override fun onClick(adapter: RvPagingAdapterHome) {
//        adapter.retry()
//    }
}