package com.daylantern.tengokberita

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.daylantern.tengokberita.databinding.FragmentArticleSelectedBinding

class ArticleSelectedFragment : Fragment() {

    private var _binding: FragmentArticleSelectedBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ArticleSelectedFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleSelectedBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        setupWebView()


    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {

        binding.webViewArticle.apply {
            loadUrl(args.articleSelected.url)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "MenuInflater(requireContext()).inflate(R.menu.menu_article_selected, menu)",
        "android.view.MenuInflater"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        MenuInflater(requireContext()).inflate(R.menu.menu_article_selected, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.bookmarkArticle -> {
                Toast.makeText(requireContext(), "Bookmark ${args.articleSelected.url}", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.shareArticle -> {
                Toast.makeText(requireContext(), "Share ${args.articleSelected.url}", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }
}