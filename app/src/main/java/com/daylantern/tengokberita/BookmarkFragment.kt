package com.daylantern.tengokberita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daylantern.tengokberita.adapters.RvAdapterBookmark
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.databinding.FragmentBookmarkBinding
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.viewmodels.BookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment(R.layout.fragment_bookmark), RvAdapterBookmark.Listener {

    private val binding: FragmentBookmarkBinding by viewBinding()

    private val rvAdapter by lazy { RvAdapterBookmark() }
    private val viewModel: BookmarkViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
    }

    private fun setupRv() {
        rvAdapter.listener = this
        binding.rvBookmark.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }
        viewModel.getList().observe(viewLifecycleOwner) {
            rvAdapter.setList(it)
        }
    }

    override fun onClick(article: SavedArticle) {
        val action = BookmarkFragmentDirections.actionGlobalArticleSelectedFragment(article)
        findNavController().navigate(action)
    }
}