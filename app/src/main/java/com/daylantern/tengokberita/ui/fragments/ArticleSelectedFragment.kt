package com.daylantern.tengokberita.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.daylantern.tengokberita.R
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.databinding.FragmentArticleSelectedBinding
import com.daylantern.tengokberita.viewmodels.ArticleSelectedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleSelectedFragment : Fragment(R.layout.fragment_article_selected) {

    private val binding: FragmentArticleSelectedBinding by viewBinding()

    private val args by navArgs<ArticleSelectedFragmentArgs>()
    private val viewModel: ArticleSelectedViewModel by viewModels()
    private var isSaved: Boolean = false
    private lateinit var itemBookmark: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        viewModel.setTitleToolbar(args.articleSelected.title)
        viewModel.title.observe(viewLifecycleOwner) {
            toolbar.title = it
        }
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {

        binding.webViewArticle.apply {
            loadUrl(args.articleSelected.url)
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.pbLoading.visibility = View.VISIBLE
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    binding.pbLoading.visibility = View.GONE

                }
            }
            settings.javaScriptEnabled = true
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "MenuInflater(requireContext()).inflate(R.menu.menu_article_selected, menu)",
        "android.view.MenuInflater"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        MenuInflater(requireContext()).inflate(R.menu.menu_article_selected, menu)
        itemBookmark = menu.findItem(R.id.bookmarkArticle)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        itemBookmark = menu.findItem(R.id.bookmarkArticle)
    }

    private fun setBookmarkIcon() {
        if(isSaved) {
             itemBookmark.setIcon(R.drawable.ic_round_bookmark)
        }else {
            itemBookmark.setIcon(R.drawable.ic_round_bookmark_border)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        checkIsSaved()
        return when(item.itemId) {
            R.id.bookmarkArticle -> {
                checkIsSaved()
                if(isSaved) {
                        viewModel.deleteArticle(args.articleSelected.url)
                    Toast.makeText(requireContext(), "Article is already saved from Bookmark!!!", Toast.LENGTH_SHORT).show()
                    itemBookmark.setIcon(R.drawable.ic_round_bookmark_border)
                }else {
                    checkIsSaved()
                    viewModel.saveArticle(SavedArticle(
                        url = args.articleSelected.url,
                        title = args.articleSelected.title,
                        urlToImage = args.articleSelected.urlToImage,
                        publishedAt = args.articleSelected.publishedAt,
                        savedAt = System.currentTimeMillis(),
                        isSaved = true
                    ))
                    Toast.makeText(requireContext(), "Article Saved to Bookmark", Toast.LENGTH_SHORT).show()
                    itemBookmark.setIcon(R.drawable.ic_round_bookmark)
                }
            true
            }
            R.id.shareArticle -> {
                val intent = Intent(Intent.ACTION_SEND).apply{
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, args.articleSelected.url)
                }
                startActivity(Intent.createChooser(intent, "Share Link Article"))
                true
            }
            else -> false
        }
    }

    private fun checkIsSaved() {
        viewModel.getArticle(args.articleSelected.url)
        viewModel.isSaved.observe(viewLifecycleOwner) {
            isSaved = it
        }
    }
}