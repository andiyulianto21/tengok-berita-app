package com.daylantern.tengokberita.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daylantern.tengokberita.R
import com.daylantern.tengokberita.adapters.RvAdapterHome
import com.daylantern.tengokberita.databinding.FragmentHomeBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), RvAdapterHome.Listener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: RvAdapterHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = RvAdapterHome()
        setupRv()
        getArticles()
    }

    private fun getArticles() {
        viewModel.getTopHeadlinesByCountry("id")
        viewModel.topHeadlines.observe(viewLifecycleOwner) {
            homeAdapter.setList(it)
        }
        homeAdapter.listener = this
    }

    private fun setupRv() {
        binding.rvTopHeadlines.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
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
        Toast.makeText(requireContext(), article.title, Toast.LENGTH_SHORT).show()
    }
}