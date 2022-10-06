package com.daylantern.tengokberita.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daylantern.tengokberita.R
import com.daylantern.tengokberita.adapters.RvPagingAdapterSearch
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.databinding.FragmentSearchBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search){

    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: RvPagingAdapterSearch
    private lateinit var imm: InputMethodManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.searchNews.postDelayed({
            binding.searchNews.requestFocus()
            showKeyboard()
        },400)
        setupRv()
        backButton()
        searchNews()
        getNews()
    }

    private fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getNews() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchNews()?.collectLatest {
                searchAdapter.submitData(it)
            }
        }
    }

    private fun searchNews() {
        binding.searchNews.apply{
            imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {q ->
                        viewModel.setQuery(q)
                        getNews()
                        clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun setupRv() {
        searchAdapter = RvPagingAdapterSearch()
        searchAdapter.listener = object : RvPagingAdapterSearch.Listener {
            override fun onClick(article: Article) {
                val action = SearchFragmentDirections.actionGlobalArticleSelectedFragment(
                    SavedArticle(
                        article.url, article.title, article.urlToImage, article.publishedAt, null
                    )
                )
                findNavController().navigate(action)
            }

        }
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
    }
}